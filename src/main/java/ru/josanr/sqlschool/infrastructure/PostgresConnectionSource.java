package ru.josanr.sqlschool.infrastructure;

import ru.josanr.sqlschool.infrastructure.exceptions.ConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresConnectionSource implements ConnectionSource {

    private Connection connection = null;

    @Override
    public Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        String url = System.getenv("DB_HOST") + "/" + System.getenv("DB_NAME");
        Properties props = new Properties();
        props.setProperty("user", System.getenv("DB_USER"));
        props.setProperty("password", System.getenv("DB_PASSWORD"));
        String driverName = System.getenv("DB_DRIVER");
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, props);
            return connection;
        } catch (SQLException e) {
            throw ConnectionException.becauseCouldNotConnect(url, e);
        } catch (ClassNotFoundException e) {
            throw ConnectionException.becauseCouldNotLoadDriver(driverName);
        }
    }
}
