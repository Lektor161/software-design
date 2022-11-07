package ru.akirakozov.sd.refactoring.database;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface QueryFunction<R> {
    R apply(ResultSet rs) throws SQLException;
}
