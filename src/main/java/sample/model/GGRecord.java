package sample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import oracle.goldengate.datasource.DsOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GGRecord extends AbstractGGRecord {

    private final String timestampAsString;
    private final String sqlType;
    private final String tableName;
    private final String xid;
    private final String csn;
    private final Integer operationSeqNo;
    private final Boolean isLast;
    private final Boolean hasLobValue;
    private final List<AbstractGGColumn> ggColumns;

    public GGRecord(DsOperation operation) {
        timestampAsString = operation.getTimestampAsString();
        sqlType = operation.getSqlType();
        tableName = operation.getTableMetaData().getTableName().toString();
        xid = operation.getXidStr();
        csn = operation.getCsnStr();
        operationSeqNo = operation.getOperationSeqno();
        isLast = operation.isLast();
        hasLobValue = operation.hasLobValue();
        ggColumns = getGGColumns(operation);
    }

    public String getTimestampAsString() {
        return timestampAsString;
    }

    public String getSqlType() {
        return sqlType;
    }

    public String getTableName() {
        return tableName;
    }

    public String getXid() {
        return xid;
    }

    public String getCsn() {
        return csn;
    }

    public Integer getOperationSeqNo() {
        return operationSeqNo;
    }

    public Boolean getIsLast() {
        return isLast;
    }

    public Boolean getHasLobValue() {
        return hasLobValue;
    }

    public List<AbstractGGColumn> getGGColumns() {
        return ggColumns;
    }

    private List<AbstractGGColumn> getGGColumns(DsOperation op) {
        List<AbstractGGColumn> columns = new ArrayList<>();
        op.getColumns().forEach(withCounter((i, column) -> {
            AbstractGGColumn ggColumn = new GGColumn(op, column, i);
            if (ggColumn.getIsKeyCol() || ggColumn.getIsChanged()) {
                switch (op.getSqlType()) {
                    case "INSERT" :
                        if (!ggColumn.getAfterValue().toString().equals("NULL")) columns.add(ggColumn);
                        break;
                    case "UPDATE" :
                        columns.add(ggColumn);
                        break;
                    case "DELETE" :
                        if (ggColumn.getIsChanged() && ggColumn.getIsKeyCol()) columns.add(ggColumn);
                        break;
                    default:
                        break;
                }
            }
        }));
        return columns;
    }

    // https://www.baeldung.com/java-foreach-counter
    private static <T> Consumer<T> withCounter(BiConsumer<Integer, T> consumer) {
        AtomicInteger counter = new AtomicInteger(0);
        return item -> consumer.accept(counter.getAndIncrement(), item);
    }

}
