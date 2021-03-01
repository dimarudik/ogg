package sample.service;

import sample.config.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PushService implements IPushService {

    private Connection connection;

    @Override
    public boolean init() throws SQLException {
        return DataSource.init();
    }

    @Override
    public void closeHikariPool() {
        DataSource.closeHikariPool();
    }

    @Override
    public void insertInto(String s) throws SQLException {

        PreparedStatement preparedStatement = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("insert into hr.identities (name) values (?)");
            //preparedStatement = connection.prepareStatement("insert into hr.identities (id, name) values (1, ?)");
            preparedStatement.setString(1, s);
            preparedStatement.execute();
            connection.commit();
        } finally {
            assert preparedStatement != null;
            preparedStatement.close();
            connection.close();
        }
    }

}
