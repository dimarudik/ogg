package sample.model;

public abstract class AbstractGGColumn {

    public abstract String getColumnName();
    public abstract String getJdbcColumnType();
    public abstract Long   getColumnLength();
    public abstract Boolean getIsChanged();
    public abstract Boolean getIsKeyCol();
    public abstract StringBuffer getBeforeValue();
    public abstract StringBuffer getAfterValue();

}
