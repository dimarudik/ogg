package ogg.restservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GGRecord {

    private String timestampAsString;
    private String sqlType;
    private String tableName;
    private String xid;
    private String csn;
    private Integer operationSeqNo;
    private Boolean isLast;
    private Boolean hasLobValue;
    private List<GGColumn> ggColumns;

    public String getTimestampAsString() {
        return timestampAsString;
    }

    public void setTimestampAsString(String timestampAsString) {
        this.timestampAsString = timestampAsString;
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getCsn() {
        return csn;
    }

    public void setCsn(String csn) {
        this.csn = csn;
    }

    public Integer getOperationSeqNo() {
        return operationSeqNo;
    }

    public void setOperationSeqNo(Integer operationSeqNo) {
        this.operationSeqNo = operationSeqNo;
    }

    public Boolean getIsLast() {
        return isLast;
    }

    public void setIsLast(Boolean last) {
        isLast = last;
    }

    public Boolean getHasLobValue() {
        return hasLobValue;
    }

    public void setHasLobValue(Boolean hasLobValue) {
        this.hasLobValue = hasLobValue;
    }

    public List<GGColumn> getGGColumns() {
        return ggColumns;
    }

    public void setGGColumns(List<GGColumn> ggColumns) {
        this.ggColumns = ggColumns;
    }

}
