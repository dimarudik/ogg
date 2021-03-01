package sample.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

//https://www.baeldung.com/hikaricp

public class DataSource {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl( "jdbc:oracle:thin:@tcp://10.217.46.21:1522/BOB" );
        config.setUsername( "hr" );
        config.setPassword( "hr" );
        config.setMaximumPoolSize(2);
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    private DataSource(){}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static boolean init() throws SQLException {
        return ds.isRunning();
    }

    public static void closeHikariPool() {
        ds.close();
    }
}
