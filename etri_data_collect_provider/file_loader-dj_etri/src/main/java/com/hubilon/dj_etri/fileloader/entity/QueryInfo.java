package com.hubilon.dj_etri.fileloader.entity;

import lombok.Data;

@Data
public class QueryInfo {
    private String selectTargetDirectory;
    private String selectLoadSeq;
    private String selectTableExists;
    private String selectAlreadyLoadInfo;
    private String createBackupTable;
    private String insertCsvLoadInfo;
    private String updateCsvLoadInfo;
    private String updateCsvLoadInfo4FileHash;
    private String updateCsvLoadInfo4Rollback;
    private String truncateTable;
    private String insertAsSelect;
    private String dropTable;
    private String insertTable;
    private String selectColumnInfo;
}
