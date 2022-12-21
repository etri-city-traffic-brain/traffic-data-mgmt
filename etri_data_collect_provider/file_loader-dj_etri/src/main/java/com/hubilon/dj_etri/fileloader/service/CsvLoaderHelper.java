package com.hubilon.dj_etri.fileloader.service;

import com.hubilon.dj_etri.csv.CsvFileEncode;
import com.hubilon.dj_etri.csv.CsvQuote;
import com.hubilon.dj_etri.csv.CsvSeparator;
import com.hubilon.dj_etri.fileloader.entity.QueryInfo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "djmc.fileloader")
public class CsvLoaderHelper {
    private String rootDirectory;
    private Integer maxDepth4FindCsv;
    private QueryInfo query;
    private CsvFileEncode csvFileEncode;
    private CsvSeparator readCsvSeparator;
    private CsvQuote csvQuote;
    private Integer batchSize;
    private Boolean exceptionIfUnknownCsvFile;
}
