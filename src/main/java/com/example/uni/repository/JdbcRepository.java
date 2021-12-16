package com.example.uni.repository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcRepository<E> implements ICrudRepository<E> {

    /**
     * opens a connection to a certain database
     * @return the new connection
     * @throws SQLException for database access errors
     */
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/university";
        String user = "user1";
        String pass = "pass";

        return DriverManager.getConnection(url, user, pass);
    }

    /**
     * returns a list of objects, representing the tuples in the table
     * @param sql the sql query
     * @param mapper maps each row of data in ResultSet
     * @return a list of objects, representing the tuples in the table
     * @throws SQLException for database access errors
     */
    public List<E> getRows(String sql, RowMapper<E> mapper) throws SQLException {
        List<E> rows = new ArrayList<>();
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();


        while (resultSet.next()) {
            rows.add(mapper.mapRow(resultSet));
        }
        connection.close();
        return rows;
    }

    /**
     * returns an object, representing the tuple in the current row of the table
     * @param sql the sql query
     * @param mapper maps each row of data in ResultSet
     * @return an object, representing the tuple in the current row of the table
     * @throws SQLException for database access errors
     */
    public E getEntity(String sql, RowMapper<E> mapper) throws SQLException {
        E row = null;
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()) {
            row = mapper.mapRow(resultSet);
        }
        connection.close();
        return row;
    }


    /**
     * updates the table in the databases after being modified
     * @param sql the sql query
     * @throws SQLException for database access errors
     */
    public void updateTable(String sql) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        connection.close();
    }
}
