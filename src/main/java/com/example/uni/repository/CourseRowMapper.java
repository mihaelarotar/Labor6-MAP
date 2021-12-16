package com.example.uni.repository;

import com.example.uni.entities.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper<Course> {
    /**
     * maps each row of data in ResultSet
     * @param resultSet ResultSet, the ResultSet to be mapped
     * @return the result object for the current row
     * @throws SQLException for database access errors
     */
    @Override
    public Course mapRow(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setName(resultSet.getString("name"));
        course.setTeacherID(resultSet.getInt("teacherId"));
        course.setMaxEnrollment(resultSet.getInt("maxEnrollment"));
        course.setCredits(resultSet.getInt("credits"));
        String sql = String.format("select * " +
                "from Student S " +
                "inner join Enrolled E " +
                "on S.studentId = E.studentId " +
                "where E.courseName = '%s'", course.getName());
        course.setStudentsEnrolled(new StudentJdbcRepository().getRows(sql, new StudentRowMapper()));
        return course;
    }
}
