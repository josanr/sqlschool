package ru.josanr.sqlschool.infrastructure.exceptions;

public class ConnectionException extends RuntimeException {

    public ConnectionException(String message, Throwable prev) {
        super(message, prev);
    }

    public ConnectionException(String message) {
        super(message);
    }

    public static ConnectionException becauseCouldNotConnect(String url, Throwable prev) {
        return new ConnectionException("Could not connect to " + url, prev);
    }

    public static ConnectionException becauseCouldNotLoadDriver(String driverName) {
        return new ConnectionException("Could Not Load driver: " + driverName);
    }
}
