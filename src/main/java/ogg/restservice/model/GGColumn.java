package ogg.restservice.model;

import ogg.record.AbstractGGColumn;

public class GGColumn {

    private String columnName;
    private String jdbcColumnType;
    private Long columnLength;
    private Boolean isChanged;
    private Boolean isKeyCol;
    private final StringBuffer beforeValue = new StringBuffer();
    private final StringBuffer afterValue = new StringBuffer();

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getJdbcColumnType() {
        return jdbcColumnType;
    }

    public void setJdbcColumnType(String jdbcColumnType) {
        this.jdbcColumnType = jdbcColumnType;
    }

    public Long getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(Long columnLength) {
        this.columnLength = columnLength;
    }

    public Boolean getIsChanged() {
        return isChanged;
    }

    public void setIsChanged(Boolean changed) {
        isChanged = changed;
    }

    public Boolean getIsKeyCol() {
        return isKeyCol;
    }

    public void setIsKeyCol(Boolean keyCol) {
        isKeyCol = keyCol;
    }

    public StringBuffer getBeforeValue() {
        return beforeValue;
    }

    public StringBuffer getAfterValue() {
        return afterValue;
    }

}
