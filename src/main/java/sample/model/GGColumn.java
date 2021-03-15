package sample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import oracle.goldengate.datasource.DsColumn;
import oracle.goldengate.datasource.DsOperation;

import java.sql.JDBCType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GGColumn {

    private String columnName;
    private String jdbcColumnType;
    private Long columnLength;
    private Boolean isChanged;
    private Boolean isKeyCol;
    private StringBuffer beforeValue = new StringBuffer();
    private StringBuffer afterValue = new StringBuffer();

    public GGColumn(){}

    public GGColumn(DsOperation operation, DsColumn column, Integer columnIndex){
        columnName = operation.getTableMetaData().getColumnName(columnIndex);
        jdbcColumnType = JDBCType.valueOf(operation.getTableMetaData().getColumnMetaData(columnIndex).getDataType().getJDBCType()).getName();
        columnLength = operation.getTableMetaData().getColumnMetaData(columnIndex).getColumnLength();
        beforeValue.append(column.getBeforeValue());
        afterValue.append(column.getAfterValue());
        isChanged = column.isChanged();
        isKeyCol = operation.getTableMetaData().getColumnMetaData(columnIndex).isKeyCol();
    }

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
