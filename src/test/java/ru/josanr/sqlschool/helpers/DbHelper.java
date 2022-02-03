package ru.josanr.sqlschool.helpers;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbHelper {

    private final String jdbcUrl;
    private final String username;
    private final String password;
    private final String schema;
    private PGSimpleDataSource dataSource;

    public DbHelper() {
        jdbcUrl = "jdbc:postgresql://localhost:5432/school";
        schema = "test";
        username = "school_admin";
        password = "school_admin_pass";
    }

    public void init() {
        DataSource dataSource = getDataSource();
        Flyway.configure()
            .schemas(schema)
            .dataSource(dataSource).load().migrate();
    }

    public DataSource getDataSource() {
        if(dataSource != null) {
            return dataSource;
        }

        dataSource = new PGSimpleDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setCurrentSchema(schema);
        return dataSource;
    }

    public void resetSequence() {

            try (
                Connection conn = getDataSource().getConnection();
                Statement seqStmt = conn.createStatement();
                Statement updStmt = conn.createStatement()
                ) {

                String sql = "SELECT c.relname FROM pg_class c WHERE c.relkind = 'S';";
                try (
                    ResultSet rs = seqStmt.executeQuery(sql)
                ) {
                    while (rs.next()) {
                        String sequence = rs.getString("relname");
                        String table = sequence.substring(0, sequence.length() - 7);
                        String updateValSql = "SELECT SETVAL('" + sequence + "', (SELECT MAX(id)+1 FROM " + table + "), true);";
                        updStmt.executeQuery(updateValSql);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
