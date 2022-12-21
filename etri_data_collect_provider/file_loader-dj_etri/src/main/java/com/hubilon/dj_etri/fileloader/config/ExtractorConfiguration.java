package com.hubilon.dj_etri.fileloader.config;

import com.hubilon.dj_etri.fileloader.CsvLoader;
import com.hubilon.dj_etri.fileloader.service.CsvLoaderHelper;
import com.hubilon.dj_etri.fileloader.service.CsvLoaderService;
import com.hubilon.dj_etri.rdb.RDBDataConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ExtractorConfiguration {

    @Bean
    public CsvLoader csvLoader(RDBDataConnector rdbDataConnector, CsvLoaderHelper csvLoaderHelper, CsvLoaderService csvLoaderService) {
        return new CsvLoader(rdbDataConnector, csvLoaderHelper, csvLoaderService);
    }
}
