package sample.service;

import sample.model.BrokenTX;

import java.sql.SQLException;
import java.util.Properties;

public interface IPushService {

    boolean init() throws SQLException;
    void closeHikariPool();
    void insertInto(String s) throws SQLException;
    void writeToTraceBrokenTX(Properties properties, String s);
    BrokenTX readBrokenTXFromTrace(Properties properties);

}
