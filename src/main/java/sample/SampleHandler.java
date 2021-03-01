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

        logger.info("Thread name : " + Thread.currentThread().getName());

        logger.info("DsEvent " + e.toString());
        logger.info("DsEvent " + e.getMetaData().toString());
        logger.info("DsEvent " + e.getEventSource().toString());

        dsOperations.forEach
                (i -> {
                    logger
                        .info(
                            i.getRecord().toString() + "\n" +
                            i.getOperationType().toString() + " " + i.getPosition() + "\n" +
                            i.toString() + "\n" +
                            i.getSqlType() + "\n" +
                            i.getColumns().toString() + "\n" +
                            i.getTableMetaData().toString() + " " + i.getTableName()
                        );
                            try {
                                pushService.insertInto(i.getRecord().toString());
                            } catch (SQLException ex) {
                                destroy();
                                ex.printStackTrace();
                            }
                        }
                );

        dsOperations.forEach(i ->
            i.getColumns().forEach(c ->
                    logger.info(
                            "Before : " + c.getBeforeValue() + "        After : " + c.getAfterValue())
                    )
        );


        return GGDataSource.Status.OK;
    }

    @Override
    public GGDataSource.Status transactionCommit(DsEvent e, DsTransaction tx) {
        logger.info("DsEvent " + e.toString());
        return super.transactionCommit(e, tx);
    }

    @Override
    public GGDataSource.Status metaDataChanged(DsEvent e, DsMetaData meta) {
        Logger.getLogger(SampleHandler.class.getName()).info("DsEvent " + e.toString());
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
