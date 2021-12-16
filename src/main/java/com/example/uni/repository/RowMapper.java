package com.example.uni.repository;


import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<E> {
    /**
     * maps each row of data in ResultSet
     * @param resultSet ResultSet, the ResultSet to be mapped
     * @return the result object for the current row
     * @throws SQLException for database access errors
     */
    E mapRow(ResultSet resultSet) throws SQLException;
}
