package com.hubilon.dj_etri.fileloader.service;

import com.hubilon.dj_etri.common.CsvUtils;
import com.hubilon.dj_etri.common.DjmcException;
import com.hubilon.dj_etri.csv.CsvFileInfo;
import com.hubilon.dj_etri.csv.CsvQuote;
import com.hubilon.dj_etri.entity.Table;
import com.hubilon.dj_etri.fileloader.entity.CsvLoadInfo;
import com.hubilon.dj_etri.rdb.RDBDataConnector;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.CSVReaderHeaderAwareBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CsvLoaderService {
    private CSVParser csvParser;
    @Autowired
    private RDBDataConnector rdbDataConnector;
    @Autowired
    private CsvLoaderHelper csvLoaderHelper;

    @PostConstruct
    public void initializeCsvParser() {

        CSVParserBuilder csvParserBuilder = new CSVParserBuilder().withSeparator(this.csvLoaderHelper.getReadCsvSeparator().getSeparatorChar());
        if (!this.csvLoaderHelper.getCsvQuote().equals(CsvQuote.NO_QUOTE)) {
            csvParserBuilder.withQuoteChar(this.csvLoaderHelper.getCsvQuote().getQuoteCharacter());
        }
        this.csvParser = csvParserBuilder.build();
    }

    private CsvFileInfo loadCsv(Table table, File csvFile) throws IOException, CsvValidationException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("table_schema", table.getSchema().name().toLowerCase());
        paramMap.put("table_name", table.name().toLowerCase());
        final List<Map<String, Object>> tableColumnInfo = this.rdbDataConnector.selectListMap(this.csvLoaderHelper.getQuery().getSelectColumnInfo(), paramMap);
        CsvFileInfo csvFileInfo;
        try (BufferedReader bufferedReader = Files.newBufferedReader(csvFile.toPath(), this.csvLoaderHelper.getCsvFileEncode().getEncode());
             CSVReaderHeaderAware csvReader = (CSVReaderHeaderAware) new CSVReaderHeaderAwareBuilder(bufferedReader).withCSVParser(csvParser).build()) {

            List<Map<String, String>> csvLines = new ArrayList<>();
            AtomicReference<String> query = new AtomicReference<>();
            log.info("try load csv file. csvFile[{}]", csvFile.toPath());
            AtomicReference<BigInteger> currentRowNum = new AtomicReference<>(BigInteger.ZERO);
            csvFileInfo = CsvUtils.readCsv(csvFile.getName(), csvReader, table.isReplaceCsvFileTarget(),
                    headers -> {
                        query.set(String.format(this.csvLoaderHelper.getQuery().getInsertTable(),
                                table.getSchema().name(),
                                table.name(),
                                headers.stream().map(h -> h.equalsIgnoreCase("offset") ? "\"OFFSET\"" : h).collect(Collectors.joining(",")),
                                headers.stream().map(h -> String.format("cast(nullif(:%s, '') as %s) ", h,
                                        tableColumnInfo.stream()
                                                .filter(t -> h.equalsIgnoreCase(t.get("column_name").toString()))
                                                .map(t -> t.get("column_type").toString())
                                                .findFirst()
                                                .orElseThrow(() -> new DjmcException("Not found column info. table[{}], csvHeader[{}]", table.name(), h))))
                                        .collect(Collectors.joining(","))));
                    },
                    line -> {
                        csvLines.add(line);
                        if (csvLines.size() >= this.csvLoaderHelper.getBatchSize()) {
                            currentRowNum.set(currentRowNum.get().add(BigInteger.valueOf(this.csvLoaderHelper.getBatchSize())));
                            log.info("try insert [{}] lines. schema[{}], table[{}]", csvLines.size(), table.getSchema().name(), table.name());
                            insertFromCsv(query.get(), csvLines);
                            csvLines.clear();
                        }
                    });
            currentRowNum.set(currentRowNum.get().add(BigInteger.valueOf(csvLines.size())));
            log.info("try insert [{}] lines. schema[{}], table[{}]", csvLines.size(), table.getSchema().name(), table.name());
            insertFromCsv(query.get(), csvLines);
            // csvFileInfo
            long fileLineNum = Files.lines(csvFile.toPath(), this.csvLoaderHelper.getCsvFileEncode().getEncode()).count();
            csvFileInfo.setFileLineNum(fileLineNum);
        }
        return csvFileInfo;
    }

    private void insertFromCsv(final String query, final List<Map<String, String>> csvValues) {
        log.debug("query[{}], csvLines.size() [{}]", query, csvValues.size());
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(csvValues);
        int[] ints = this.rdbDataConnector.batchUpdate(query, batch);
        log.debug("results [{}]", ints);
        if (Arrays.stream(ints).boxed().anyMatch(i -> i != 1)) {
            int failNum = 0;
            for (int i = 0; i < ints.length; i++) {
                if (ints[i] != 1) {
                    failNum++;
                    log.warn("Failed to insert csv values. csvValues[{}]", csvValues.get(i));
                }
            }
            throw new DjmcException("Failed to insert csv values. failNum[{}]", failNum);
        }
    }

    public void rollbackBackupTable(CsvLoadInfo csvLoadInfo) throws SQLException {
        // 1. truncate
        truncateTargetTable(csvLoadInfo);
        Table table = csvLoadInfo.getTable();
        // 2. rollback from backup table
        String query = String.format(this.csvLoaderHelper.getQuery().getInsertAsSelect(), table.getSchema().name(), table.name(), table.getSchema().name(), csvLoadInfo.getBackupTableName());
        log.info("Try insert as select from backup table. query[{}]", query);
        this.rdbDataConnector.running(query);
        log.info("Successfully insert as select from backup table. schemaName[{}], backupTableName[{}], targetTableName[{}], query[{}]", table.getSchema().name(), csvLoadInfo.getBackupTableName(), csvLoadInfo.getTargetTableName(), query);
    }

    public void dropBackupTable(CsvLoadInfo csvLoadInfo) throws SQLException {
        Table table = csvLoadInfo.getTable();
        // drop backup table
        String query = String.format(this.csvLoaderHelper.getQuery().getDropTable(), table.getSchema().name(), csvLoadInfo.getBackupTableName());
        log.info("Try drop backup table. query[{}]", query);
        this.rdbDataConnector.running(query);
        log.info("Successfully dropped backup table. schemaName[{}], backupTableName[{}], targetTableName[{}], query[{}]", table.getSchema().name(), csvLoadInfo.getBackupTableName(), csvLoadInfo.getTargetTableName(), query);
    }

    public CsvFileInfo loadCsvFile(CsvLoadInfo csvLoadInfo) throws IOException, CsvValidationException {
        Table table = csvLoadInfo.getTable();
        File csvFile = csvLoadInfo.getCsvFile();
        return loadCsv(table, csvFile);
    }

    public void createBackupTable(CsvLoadInfo csvLoadInfo) throws SQLException {
        Table table = csvLoadInfo.getTable();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("schemaName", table.getSchema().name().toLowerCase());
        paramMap.put("tableName", csvLoadInfo.getBackupTableName());
        Map<String, Object> tableExists = this.rdbDataConnector.selectMap(this.csvLoaderHelper.getQuery().getSelectTableExists(), paramMap);
        if (tableExists != null && !tableExists.isEmpty() && tableExists.containsKey("table_exists") && Boolean.TRUE.equals(tableExists.get("table_exists"))) {
            // truncate already exists table.
            log.info("Already exists backup table. schemaName[{}], tableName[{}]", table.getSchema().name(), table.name());
            String query = String.format(this.csvLoaderHelper.getQuery().getTruncateTable(), csvLoadInfo.getTable().getSchema().name(), csvLoadInfo.getTable().name());
            this.rdbDataConnector.running(query);

            log.info("Insert as select from target table. targetSchemaName[{}], targetTableName[{}], backupTableName[{}]", table.getSchema().name(), table.name(), csvLoadInfo.getBackupTableName());
            query = String.format(this.csvLoaderHelper.getQuery().getInsertAsSelect(),
                    table.getSchema().name(), csvLoadInfo.getBackupTableName(), table.getSchema().name(), table.name());
            this.rdbDataConnector.running(query);
            log.info("Successfully insert as select from target table. targetSchemaName[{}], targetTableName[{}], backupTableName[{}]", table.getSchema().name(), table.name(), csvLoadInfo.getBackupTableName());
        } else {
            String query = String.format(this.csvLoaderHelper.getQuery().getCreateBackupTable(),
                    table.getSchema().name(), csvLoadInfo.getBackupTableName(),
                    table.getSchema().name(), csvLoadInfo.getTargetTableName());
            log.info("Try create backup table. query[{}]", query);
            this.rdbDataConnector.running(query);
            log.info("Successfully create backup table. table[{}]", csvLoadInfo.getTable().name());
        }
    }

    public void truncateTargetTable(CsvLoadInfo csvLoadInfo) throws SQLException {
        String query = String.format(this.csvLoaderHelper.getQuery().getTruncateTable(), csvLoadInfo.getTable().getSchema().name(), csvLoadInfo.getTable().name());
        log.info("Try truncate table. query[{}]", query);
        this.rdbDataConnector.running(query);
        log.info("Successfully truncate table. table[{}]", csvLoadInfo.getTable().name());
    }

    public boolean alreadyLoaded(CsvLoadInfo csvLoadInfo) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("loadId", csvLoadInfo.getLoadId());
        Map<String, Object> alreadyLoadedMap = this.rdbDataConnector.selectMap(this.csvLoaderHelper.getQuery().getSelectAlreadyLoadInfo(), paramMap);
        return alreadyLoadedMap != null && alreadyLoadedMap.containsKey("already_loaded") && Boolean.TRUE.equals(alreadyLoadedMap.get("already_loaded"));
    }
}
