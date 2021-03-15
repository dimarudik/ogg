package sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import oracle.goldengate.datasource.*;
import oracle.goldengate.datasource.meta.DsMetaData;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sample.config.AppContext;
import sample.model.GGRecord;
import sample.service.PushService;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class SampleHandler extends AbstractHandler {

    private final Logger logger = Logger.getLogger(SampleHandler.class.getName());
    private PushService pushService;
    private Properties properties = null;
    //private final RestTemplate restTemplate = new RestTemplate();
    private final ClassPathXmlApplicationContext context =
            new ClassPathXmlApplicationContext("./sample/appcontext.xml");

    @Override
    public void init(DsConfiguration conf, DsMetaData metaData) {

        super.init(conf, metaData);
        DsConfiguration dsConfiguration = getConfig();
        properties = dsConfiguration.getProperties();

        AppContext appContext = context.getBean(AppContext.class);
        //AsyncConfiguration asyncConfiguration = context.getBean(AsyncConfiguration.class);
        //Executor executor = context.getBean("taskExecutor", Executor.class);
        pushService = context.getBean(PushService.class);
        Logger.getLogger(SampleHandler.class.getName()).info(appContext.printMe());
        appContext.publishedValues().forEach(i -> Logger.getLogger(SampleHandler.class.getName()).info(i.toString()));

    }

    @Override
    public GGDataSource.Status operationAdded(DsEvent e, DsTransaction tx, DsOperation op) {
        super.operationAdded(e, tx, op);
        GGRecord ggRecord = new GGRecord(op);
        ObjectMapper mapper = new ObjectMapper();

        try {
            String jsonString = mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(ggRecord);
            //logger.info(jsonString);
            //GGRecord record = restTemplate.postForObject("http://localhost:8080/api/ggrecord", ggRecord, GGRecord.class);
            CompletableFuture<GGRecord> record = pushService.sendToRest(ggRecord);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }

        return GGDataSource.Status.OK;
    }

    @Override
    public GGDataSource.Status transactionBegin(DsEvent e, DsTransaction tx) {
        return super.transactionBegin(e, tx);
    }

    @Override
    public GGDataSource.Status transactionCommit(DsEvent e, DsTransaction tx) {
        return super.transactionCommit(e,tx);
    }

    @Override
    public GGDataSource.Status metaDataChanged(DsEvent e, DsMetaData meta) {
        return super.metaDataChanged(e, meta);
    }

    @Override
    public void destroy() {
        //pushService.closeHikariPool();
        context.close();
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
