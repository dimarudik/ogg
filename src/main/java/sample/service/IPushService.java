package sample.service;

import java.sql.SQLException;

public interface IPushService {

    boolean init() throws SQLException;
    void closeHikariPool();
    void insertInto(String s) throws SQLException;
}
