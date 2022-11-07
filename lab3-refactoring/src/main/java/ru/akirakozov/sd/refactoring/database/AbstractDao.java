package ru.akirakozov.sd.refactoring.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDao {
    private final String url;

    protected AbstractDao(String url) {
        this.url = url;
    }

    protected void update(String sql) {
        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected  <T> T execute(String sql, QueryFunction<T> function) {
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            return function.apply(statement.executeQuery(sql));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
