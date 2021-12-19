package ru.josanr.sqlschool.infrastructure;

import java.sql.Connection;

public interface ConnectionSource {

    Connection getConnection();
}
