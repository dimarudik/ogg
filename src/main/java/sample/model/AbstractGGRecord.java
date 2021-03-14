package sample.model;

import java.util.List;

public abstract class AbstractGGRecord {

    public abstract String getTimestampAsString();
    public abstract String getSqlType();
    public abstract String getTableName();
    public abstract String getXid();
    public abstract String getCsn();
    public abstract Integer getOperationSeqNo();
    public abstract Boolean getIsLast();
    public abstract Boolean getHasLobValue();
    public abstract List<AbstractGGColumn> getGGColumns();

}
