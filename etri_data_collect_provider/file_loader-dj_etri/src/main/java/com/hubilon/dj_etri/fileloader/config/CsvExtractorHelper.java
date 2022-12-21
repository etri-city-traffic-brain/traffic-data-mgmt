package com.hubilon.dj_etri.fileloader.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CsvExtractorHelper {
    private String parentDirectoryPosixFilePermission;
    private char separator;
    private char quoteCharacter;
    private char escapeCharacter;
    private String lineEnd;
    private Integer fetchSize;
    private Integer[] extractServiceId;
}
