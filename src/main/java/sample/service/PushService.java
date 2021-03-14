package sample.service;

import sample.config.HCPDataSource;
import sample.model.BrokenTX;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class PushService implements IPushService {

    private Connection connection;
    private final Logger logger = Logger.getLogger(PushService.class.getName());

    @Override
    public boolean init() throws SQLException {
        return HCPDataSource.init();
    }

    @Override
    public void closeHikariPool() {
        HCPDataSource.closeHikariPool();
    }

    @Override
    public void insertInto(String s) throws SQLException {

        PreparedStatement preparedStatement = null;
        try {
            connection = HCPDataSource.getConnection();
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

    @Override
    public void writeToTraceBrokenTX(Properties properties, String s) {
        if (properties.containsKey("gg.recoverypath")) {
            File recoverypath = new File(properties.getProperty("gg.recoverypath", "./")
                    + File.separator
                    + properties.getProperty("gg.handlerlist")
                    + ".rcv");
            try {
                BufferedOutputStream stream = null;
                stream = new BufferedOutputStream(new FileOutputStream(recoverypath));
                byte[] strToByte = s.getBytes(StandardCharsets.UTF_8);
                stream.write(strToByte);
                stream.close();
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

    }

    @Override
    public BrokenTX readBrokenTXFromTrace(Properties properties) {
        BrokenTX brokenTX = new BrokenTX();
        if (properties.containsKey("gg.recoverypath")) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(
                        properties.getProperty("gg.recoverypath", "./")
                                + File.separator
                                + properties.getProperty("gg.handlerlist")
                                + ".rcv"
                ));
                String currentLine = reader.readLine();
                if (currentLine != null) {
                    String[] strings = currentLine.split(";");
                    brokenTX.setOperationSeq(Integer.valueOf(strings[1]));
                    brokenTX.setCsn(strings[0]);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return brokenTX;
    }

}
