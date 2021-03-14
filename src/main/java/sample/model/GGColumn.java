package sample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import oracle.goldengate.datasource.DsColumn;
import oracle.goldengate.datasource.DsOperation;

import java.sql.JDBCType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GGColumn extends AbstractGGColumn {

    private final String columnName;
    private final String jdbcColumnType;
    private final Long columnLength;
    private final Boolean isChanged;
    private final Boolean isKeyCol;
    private final StringBuffer beforeValue = new StringBuffer();
    private final StringBuffer afterValue = new StringBuffer();

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

    public StringBuffer getBeforeValue() {
        return beforeValue;
    }

    public StringBuffer getAfterValue() {
        return afterValue;
    }

    public Long getColumnLength() {
        return columnLength;
    }

    public String getJdbcColumnType() {
        return jdbcColumnType;
    }

    public Boolean getIsChanged() {
        return isChanged;
    }

    public Boolean getIsKeyCol() {
        return isKeyCol;
    }

}
