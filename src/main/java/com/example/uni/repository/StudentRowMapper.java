package com.example.uni.repository;

import com.example.uni.entities.Student;
import com.example.uni.exceptions.ExceededValueException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentRowMapper implements RowMapper<Student> {
    /**
     * maps each row of data in ResultSet
     *
     * @param resultSet ResultSet, the ResultSet to be mapped
     * @return the result object for the current row
     * @throws SQLException for database access errors
     */
    @Override
    public Student mapRow(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setStudentID(resultSet.getLong("studentId"));
        student.setFirstName(resultSet.getString("firstName"));
        student.setLastName(resultSet.getString("lastName"));
        try {
            student.setTotalCredits(resultSet.getInt("totalCredits"));
        } catch (ExceededValueException e) {
            e.printStackTrace();
        }

        student.setEnrolledCourses(new ArrayList<>());
        return student;
    }
}
