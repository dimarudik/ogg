package sample;

import oracle.goldengate.datasource.*;
import oracle.goldengate.datasource.meta.DsMetaData;
import sample.model.BrokenTX;
import sample.service.PushService;

import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class SampleHandler extends AbstractHandler {

    private final Logger logger = Logger.getLogger(SampleHandler.class.getName());
    private final PushService pushService = new PushService();
    private Properties properties = null;

    @Override
    public void init(DsConfiguration conf, DsMetaData metaData) {
        super.init(conf, metaData);
        DsConfiguration dsConfiguration = getConfig();
        properties = dsConfiguration.getProperties();
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

        BrokenTX brokenTX = pushService.readBrokenTXFromTrace(properties);

        final int[] j = {0};
        StringBuffer s = new StringBuffer();
            s.append("{");
            s.append(op.getTimestampAsString());
            s.append("}");
            s.append(",");
            s.append("{");
            s.append(op.getSqlType());
            s.append("}");
            s.append(",");
            s.append("{");
            s.append(op.getTableMetaData().getTableName());
            s.append("}");
            s.append(",");
            s.append("{");
            s.append(op.getXidStr());
            s.append(",");
            s.append(op.getCsnStr());
            s.append(",");
            s.append(op.getOperationSeqno());
            s.append("}");
            s.append(",");
            op.getColumns().forEach(c -> {
                s.append("{");
                s.append(op.getTableMetaData().getColumnName(j[0]++));
                s.append(":");
                s.append(c.getBeforeValue());
                s.append(",");
                s.append(c.getAfterValue());
                s.append("}");
            });

            try {
                if (!brokenTX.isValid()) {
                    pushService.insertInto(String.valueOf(s));
                } else if (brokenTX.getCsn().equals(op.getCsnStr()) && brokenTX.getOperationSeq() <= op.getOperationSeqno()) {
                    pushService.writeToTraceBrokenTX(properties, "");
                    pushService.insertInto(String.valueOf(s));
                }
            } catch (SQLException ex) {
                logger.warning(String.valueOf(s));
                pushService.writeToTraceBrokenTX(properties, op.getCsnStr() + ";" + op.getOperationSeqno());
                destroy();
                ex.printStackTrace();
            }

        return GGDataSource.Status.OK;
    }

    @Override
    public GGDataSource.Status transactionBegin(DsEvent e, DsTransaction tx) {
        //logger.info(e.toString() + " " + " ---------------------------------------------- BEGIN " + tx.getCsnStr());
        return super.transactionBegin(e, tx);
    }

    @Override
    public GGDataSource.Status transactionCommit(DsEvent e, DsTransaction tx) {
        //logger.info(e.toString() + " " + " ---------------------------------------------- END " + tx.getCsnStr());
        return super.transactionCommit(e,tx);
    }

    @Override
    public GGDataSource.Status metaDataChanged(DsEvent e, DsMetaData meta) {
        //Logger.getLogger(SampleHandler.class.getName()).info("DsEvent " + e.toString());
        return super.metaDataChanged(e, meta);
    }

    @Override
    public void destroy() {
        pushService.closeHikariPool();
        super.destroy();
    }

    @Override
    public String reportStatus() {
        return "";
    }

/*
    public static void main(String[] args) throws SQLException {

        try (Connection connection = DataSource.getConnection()) {
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
*/

}
