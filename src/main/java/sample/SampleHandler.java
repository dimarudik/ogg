package sample;

import oracle.goldengate.datasource.*;
import oracle.goldengate.datasource.meta.DsMetaData;
import sample.config.DataSource;
import sample.service.PushService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class SampleHandler extends AbstractHandler {

    private final Logger logger = Logger.getLogger(SampleHandler.class.getName());
    private final PushService pushService = new PushService();

    @Override
    public void init(DsConfiguration conf, DsMetaData metaData) {
        super.init(conf, metaData);
        try {
            pushService.init();
        } catch (SQLException exception) {
            destroy();
            exception.printStackTrace();
        }
    }

    @Override
    public GGDataSource.Status operationAdded(DsEvent e, DsTransaction tx, DsOperation op) {
        super.operationAdded(e, tx, op);

        List<DsOperation> dsOperations = tx.getOperations();

/*
        logger.info("Thread name : " + Thread.currentThread().getName());
*/

        //logger.info("\n=============================================================================");
        //logger.info("DsEvent " + e.toString());
        //logger.info("DsEvent " + e.getMetaData().toString());
        //logger.info("DsEvent " + e.getEventSource().toString());
        //logger.info("-----------------------------------------------------------------------------");
/*
        logger.info("getPosition() " + op.getPosition() +
                " getTranID() " + op.getTranID() +
                " getPositionSeqno() " + op.getPositionSeqno() +
                " getOperationSeqno() " + op.getOperationSeqno() +
                " getPositionRba() " + op.getPositionRba());
        dsOperations.forEach
                (i -> {
                    logger
                        .info(
                            "toString() " + i.toString() + "\n" +
                            //"getRecord() " + i.getRecord().toString() + "\n" +
                            //"getOperationType() " + i.getOperationType().toString() + "\n" +
                            "getPosition() " + i.getPosition() + "\n" +
                            //"getSqlType() " + i.getSqlType() + "\n" +
                            //"getColumns() " + i.getColumns().toString() + "\n" +
                            "getTableName() " + i.getTableName() + "\n" +
                            //"getTableMetaData() " + i.getTableMetaData().toString() + "\n" +
                            "getCsnStr() " + i.getCsnStr() + "\n" +
                            //"getReadTimeAsString() " + i.getReadTimeAsString() + "\n" +
                            //"getTimestampAsString() " + i.getTimestampAsString() + "\n" +
                            //"getXidStr() " + i.getXidStr() + "\n" +
                            //"getOperationSeqno() " + i.getOperationSeqno() + "\n" +
                            "getPositionRba() " + i.getPositionRba() + "\n" +
                            //"getPositionSeqno() " + i.getPositionSeqno() + "\n" +
                            "getTranID() " + i.getTranID()
                        );
                            try {
                                pushService.insertInto(i.getRecord().toString());
                            } catch (SQLException ex) {
                                destroy();
                                ex.printStackTrace();
                            }
                        }
                );
*/

        final int[] j = {0};
        StringBuilder s = new StringBuilder();
        dsOperations.forEach(i -> {
            s.append("{");
            s.append(i.getTimestampAsString());
            s.append("}");
            s.append(",");
            s.append("{");
            s.append(i.getSqlType());
            s.append("}");
            s.append(",");
            s.append("{");
            s.append(i.getTableMetaData().getTableName());
            s.append("}");
            s.append(",");
            s.append("{");
            s.append(i.getXidStr());
            s.append(",");
            s.append(i.getCsnStr());
            s.append(",");
            s.append(i.getOperationSeqno());
            s.append("}");
            s.append(",");
            i.getColumns().forEach(c -> {
                s.append("{");
                s.append(i.getTableMetaData().getColumnName(j[0]++));
                s.append(":");
                s.append(c.getBeforeValue());
                s.append(",");
                s.append(c.getAfterValue());
                s.append("}");
            });
            logger.info(String.valueOf(s));
            }
        );

        return GGDataSource.Status.OK;
    }

    @Override
    public GGDataSource.Status transactionCommit(DsEvent e, DsTransaction tx) {
        logger.info(e.toString());
        return super.transactionCommit(e, tx);
    }

    @Override
    public GGDataSource.Status metaDataChanged(DsEvent e, DsMetaData meta) {
        //Logger.getLogger(SampleHandler.class.getName()).info("DsEvent " + e.toString());
        return super.metaDataChanged(e, meta);
    }

    @Override
    public void destroy() {
        super.destroy();
        pushService.closeHikariPool();
    }

    @Override
    public String reportStatus() {
        return "";
    }

    public static void main(String[] args) throws SQLException {

        try (Connection connection = DataSource.getConnection()) {
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

}
