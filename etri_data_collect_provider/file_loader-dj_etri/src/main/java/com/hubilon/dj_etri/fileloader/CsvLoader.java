package com.hubilon.dj_etri.fileloader;

import com.hubilon.dj_etri.fileloader.entity.LoadStatus;
import com.hubilon.dj_etri.fileloader.service.CsvLoaderHelper;
import com.hubilon.dj_etri.fileloader.service.CsvLoaderService;
import com.hubilon.dj_etri.common.DjmcException;
import com.hubilon.dj_etri.common.DjmcUtils;
import com.hubilon.dj_etri.csv.CsvFileInfo;
import com.hubilon.dj_etri.entity.Table;
import com.hubilon.dj_etri.fileloader.entity.CsvLoadInfo;
import com.hubilon.dj_etri.rdb.RDBDataConnector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class CsvLoader implements ApplicationRunner {
    private static final Predicate<Path> CSV_EXTENSION_PREDICATE = file -> "csv".equalsIgnoreCase(FilenameUtils.getExtension(file.getFileName().toString()));
    private final RDBDataConnector rdbDataConnector;
    private final CsvLoaderHelper csvLoaderHelper;
    private final CsvLoaderService csvLoaderService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 1. find csv files
        List<CsvLoadInfo> csvLoadInfoList = foundTargetCsvFiles();
        csvLoadInfoList.stream().forEach(loadInfo -> {
            log.info("Founded Load info. schema[{}], table[{}], csvFile[{}], md5Hex[{}]", loadInfo.getTable().getSchema().name(), loadInfo.getTable().name(), loadInfo.getTargetFile(), loadInfo.getTargetFileHash());
        });

        // filter table by csv files
        Map<Table, List<CsvLoadInfo>> tableMappedCsvLoadInfoMap = csvLoadInfoList.stream().collect(Collectors.groupingBy(csvLoadInfo -> csvLoadInfo.getTable()));

        Long totalFileNum = csvLoadInfoList.stream().filter(csvLoadInfo -> !csvLoadInfo.getTable().equals(Table.UNKNOWN)).count();
        if (totalFileNum <= 0) {
            throw new DjmcException("Not found mapped csv files. csvFiles[{}]", totalFileNum);
        }
        AtomicInteger currentFileNum = new AtomicInteger(0);
        for (Table table : tableMappedCsvLoadInfoMap.keySet()) {

            List<CsvLoadInfo> tableMappedCsvLoadInfoList = tableMappedCsvLoadInfoMap.get(table);
            CsvLoadInfo firstCsvLoadInfo = tableMappedCsvLoadInfoList.get(0);
            if (table.equals(Table.UNKNOWN)) {
                log.info("Unknown table was ignored. csvFiles[{}]", tableMappedCsvLoadInfoList.stream().map(csvLoadInfo -> csvLoadInfo.getCsvFile().getAbsolutePath()).collect(Collectors.joining(", ")));
                continue;
            }
            // 2. create backup table
            this.csvLoaderService.createBackupTable(firstCsvLoadInfo);
            // 3. truncate target table
            this.csvLoaderService.truncateTargetTable(firstCsvLoadInfo);

            // 4. upload csv file to backup table
            String currentCsvFilePath = null;
            try {
                for (CsvLoadInfo csvLoadInfo : tableMappedCsvLoadInfoList) {
                    if (table.isReplaceCsvFileTarget()) {
                        log.info("Target file[{}] contains [null] character. Try remove it. fileHash[{}]", csvLoadInfo.getCsvFile().getAbsolutePath(), csvLoadInfo.getTargetFileHash());
                        DjmcUtils.executeScript(String.format("perl -pi -e 's/\\x00//g' %s", csvLoadInfo.getCsvFile().getAbsolutePath()));
                        csvLoadInfo.setTargetFileHash(DjmcUtils.md5Hex(csvLoadInfo.getCsvFile().getAbsoluteFile()));
                        log.info("Successfully remove [null] character. fileHash[{}]", csvLoadInfo.getTargetFileHash());
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("loadId", csvLoadInfo.getLoadId());
                        paramMap.put("targetFileHash", csvLoadInfo.getTargetFileHash());
                        this.rdbDataConnector.update(this.csvLoaderHelper.getQuery().getUpdateCsvLoadInfo4FileHash(), paramMap);
                    }
                    currentFileNum.incrementAndGet();
                    Map<String, Object> paramMap = new HashMap<>();
                    if (this.csvLoaderService.alreadyLoaded(csvLoadInfo)) {
                        log.info("[{}/{}] Already loaded file. csv file is ignored. loadSeq[{}], loadId[{}], fileName [{}], fileSize[{}], lineNumber[{}], loadDate[{}]",
                                String.format("%3d", currentFileNum.get()),
                                String.format("%3d", totalFileNum),
                                csvLoadInfo.getLoadSeq(),
                                csvLoadInfo.getLoadId(),
                                csvLoadInfo.getTargetFile(),
                                csvLoadInfo.getTargetFileSize(),
                                csvLoadInfo.getLoadLineNum(),
                                csvLoadInfo.getLoadDate()
                        );

                        paramMap.put("loadId", csvLoadInfo.getLoadId());
                        paramMap.put("loadLineNum", csvLoadInfo.getLoadLineNum());
                        paramMap.put("loadStatus", LoadStatus.IGNORED.name());
                        this.rdbDataConnector.update(this.csvLoaderHelper.getQuery().getUpdateCsvLoadInfo(), paramMap);
                        continue;
                    }
                    currentCsvFilePath = csvLoadInfo.getCsvFile().getAbsolutePath();
                    CsvFileInfo csvFileInfo = this.csvLoaderService.loadCsvFile(csvLoadInfo);
                    String fileSizeString = FileUtils.byteCountToDisplaySize(csvLoadInfo.getTargetFileSize());
                    csvFileInfo.setIndex(currentFileNum.get());
                    csvFileInfo.setFileSize(csvLoadInfo.getTargetFileSize());
                    // update loadLineNum
                    csvLoadInfo.setLoadLineNum(csvFileInfo.getTotalRow());
                    log.info("[{}/{}] loadSeq[{}], loadId[{}], fileName [{}], fileSize[{}], lineNumber[{}], columnNum[{}]",
                            String.format("%3d", currentFileNum.get()),
                            String.format("%3d", totalFileNum),
                            csvLoadInfo.getLoadSeq(),
                            csvLoadInfo.getLoadId(),
                            csvFileInfo.getFileName(),
                            fileSizeString,
                            csvFileInfo.getTotalRow(),
                            csvFileInfo.getColumnNum());

                    paramMap.put("loadId", csvLoadInfo.getLoadId());
                    paramMap.put("loadLineNum", csvLoadInfo.getLoadLineNum());
                    paramMap.put("loadStatus", LoadStatus.LOADED.name());
                    this.rdbDataConnector.update(this.csvLoaderHelper.getQuery().getUpdateCsvLoadInfo(), paramMap);
                }
            } catch (Throwable e) {
                log.error("[{}/{}] Exception occurred while load csv. loadSeq[{}], fileName[{}]",
                        String.format("%3d", currentFileNum.get()),
                        String.format("%3d", totalFileNum),
                        firstCsvLoadInfo.getLoadSeq(),
                        currentCsvFilePath,
                        e);
                this.csvLoaderService.rollbackBackupTable(firstCsvLoadInfo);
                // rollback csv file info to null
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("loadSeq", firstCsvLoadInfo.getLoadSeq());
                paramMap.put("targetSchema", table.getSchema().name());
                paramMap.put("targetTableName", table.name());
                this.rdbDataConnector.update(this.csvLoaderHelper.getQuery().getUpdateCsvLoadInfo4Rollback(), paramMap);
            } finally {
                this.csvLoaderService.dropBackupTable(firstCsvLoadInfo);
            }
        }
    }

    private List<CsvLoadInfo> foundTargetCsvFiles() throws IOException {
        Map<String, Object> resultMap = this.rdbDataConnector.selectMap(this.csvLoaderHelper.getQuery().getSelectTargetDirectory(), null);
        String targetDirectory = resultMap.get("targetDirectory").toString();
        String targetDate = resultMap.get("targetDate").toString();
        log.info("Try found target csv files. targetDate[{}], targetDirectory[{}]", targetDate, targetDirectory);
        resultMap = this.rdbDataConnector.selectMap(this.csvLoaderHelper.getQuery().getSelectLoadSeq(), null);
        Long loadSeq = (Long) resultMap.get("load_seq");
        Path rootPath = Paths.get(this.csvLoaderHelper.getRootDirectory());
        if (!rootPath.toFile().exists()) {
            throw new DjmcException("Not found root directory. rootDirectory[{}]", rootPath);
        }
        Path targetPath = Paths.get(rootPath.toString(), targetDirectory);
        if (!targetPath.toFile().exists()) {
            throw new DjmcException("Not found target directory. targetDirectory[{}]", targetPath);
        }
        List<File> csvFiles = DjmcUtils.findFiles(targetPath.toFile(), this.csvLoaderHelper.getMaxDepth4FindCsv(), CSV_EXTENSION_PREDICATE);
        if (csvFiles == null || csvFiles.isEmpty()) {
            throw new DjmcException("Not found csv files.");
        }
        final List<CsvLoadInfo> csvLoadInfoList = new ArrayList<>();
        csvFiles.stream().forEach(csv -> {
            String tableName = csv.getName().replaceAll("\\..+", "").toUpperCase();
            Table table;
            if (!EnumUtils.isValidEnum(Table.class, tableName)) {
                if (this.csvLoaderHelper.getExceptionIfUnknownCsvFile()) {
                    throw new DjmcException("Unknown table name for load csv. csvFile[{}], tableName[{}]", csv.getName(), tableName);
                }
                table = Table.UNKNOWN;
            } else {
                table = Table.valueOf(tableName);
            }
            if (table.equals(Table.UNKNOWN)) {
                log.warn("Found unknown csv file. csvFile[{}]", csv.getAbsolutePath());
            }
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("loadSeq", loadSeq);
            paramMap.put("targetDate", targetDate);
            paramMap.put("targetSchema", table.getSchema().name());
            paramMap.put("targetTableName", table.name());
            paramMap.put("backupTableName", table.equals(Table.UNKNOWN) ? null : String.format("%s_backup_%s", table.name(), targetDate));
            paramMap.put("targetFile", csv.getAbsolutePath());
            paramMap.put("targetFileHash", DjmcUtils.md5Hex(csv));
            paramMap.put("targetFileSize", csv.length());

            CsvLoadInfo csvLoadInfo = this.rdbDataConnector.selectOne(this.csvLoaderHelper.getQuery().getInsertCsvLoadInfo(), paramMap, CsvLoadInfo.class);
            csvLoadInfo.setLoadSeq(loadSeq);
            csvLoadInfo.setTable(table);
            csvLoadInfo.setCsvFile(csv);
            csvLoadInfoList.add(csvLoadInfo);
        });
        return csvLoadInfoList;
    }
}
