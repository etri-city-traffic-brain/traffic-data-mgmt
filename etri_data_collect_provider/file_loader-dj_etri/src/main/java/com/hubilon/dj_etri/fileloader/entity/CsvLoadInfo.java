package com.hubilon.dj_etri.fileloader.entity;

import com.hubilon.dj_etri.entity.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CsvLoadInfo {
    private Long loadId;
    private Long loadSeq;
    private String targetDate;
    private String targetSchema;
    private String targetTableName;
    private String backupTableName;
    private String targetFile;
    private String targetFileHash;
    private Long targetFileSize;
    private LoadStatus loadStatus;
    private Long loadLineNum;
    private LocalDateTime loadDate;

    // not entity
    private Table table;
    private File csvFile;
}
