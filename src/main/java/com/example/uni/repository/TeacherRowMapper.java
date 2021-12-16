package com.example.uni.repository;

import com.example.uni.entities.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherRowMapper implements RowMapper<Teacher> {
    /**
     * maps each row of data in ResultSet
     *
     * @param resultSet ResultSet, the ResultSet to be mapped
     * @return the result object for the current row
     * @throws SQLException for database access errors
     */
    @Override
    public Teacher mapRow(ResultSet resultSet) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setTeacherID(resultSet.getInt("teacherId"));
        teacher.setFirstName(resultSet.getString("firstName"));
        teacher.setLastName(resultSet.getString("lastName"));
        String sql = String.format("select * from Course where teacherId= '%s'", teacher.getTeacherID());
        teacher.setCourses(new CourseJdbcRepository().getRows(sql, new CourseRowMapper()));
        return teacher;
    }
}
