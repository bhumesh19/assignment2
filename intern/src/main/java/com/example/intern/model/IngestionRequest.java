package com.example.intern.model;

import java.util.List;

public class IngestionRequest {
    private String sourceType;
    private ClickHouseConfig clickHouseConfig;
    private String flatFileName;
    private String table;
    private List<String> columns;
    private String targetType;

    // Getter and Setter for sourceType
    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    // Getter and Setter for clickHouseConfig
    public ClickHouseConfig getClickHouseConfig() {
        return clickHouseConfig;
    }

    public void setClickHouseConfig(ClickHouseConfig clickHouseConfig) {
        this.clickHouseConfig = clickHouseConfig;
    }

    // Getter and Setter for flatFileName
    public String getFlatFileName() {
        return flatFileName;
    }

    public void setFlatFileName(String flatFileName) {
        this.flatFileName = flatFileName;
    }

    // Getter and Setter for table
    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    // Getter and Setter for columns
    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    // Getter and Setter for targetType
    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
}
