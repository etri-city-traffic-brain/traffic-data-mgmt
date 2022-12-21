package com.hubilon.dj_etri.fileloader.config;

import com.opencsv.CSVWriter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class CsvCallbackHandler implements RowCallbackHandler {
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    private final CSVWriter csvWriter;
    private final BigDecimal totalSize;
    private final Integer flushSize;
    private List<String> columnNames;
    @Getter
    private Long currentRowNum = 0L;

    public void processRow(ResultSet resultSet) throws SQLException {
        if (resultSet.isFirst()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            this.columnNames = new ArrayList<>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                this.columnNames.add(metaData.getColumnName(i).toUpperCase());
            }
            csvWriter.writeNext(columnNames.stream().collect(Collectors.toList()).toArray(new String[0]));
        }
        List<String> rowValueList = new ArrayList<>();
        for (String column : columnNames) {
            rowValueList.add(resultSet.getString(column));
        }
        csvWriter.writeNext(rowValueList.toArray(new String[0]));
        if (++currentRowNum % flushSize == 0) {
            try {
                csvWriter.flush();
            } catch (IOException e) {
                log.error("Error occurred while flush csv file.", e);
            }
            log.info("Total [{}], current [{}] - {}%",
                    this.totalSize,
                    this.currentRowNum,
                    BigDecimal.valueOf(currentRowNum).multiply(ONE_HUNDRED).divide(this.totalSize, 1, BigDecimal.ROUND_DOWN));
        }
    }
}
