package com.example.uni.repository;

import com.example.uni.entities.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CourseJdbcRepository extends JdbcRepository<Course> {


    /**
     * returns the course with the given name
     * @param courseName string, representing the title of the course to be returned
     * @return the course with the given name
     */
    public Course findByName(String courseName) throws SQLException {
        String sql = String.format("select * from Course where name='%s' ", courseName);
        return getEntity(sql, new CourseRowMapper());
    }

    /**
     * @return all entities
     */
    @Override
    public List<Course> getAll() throws SQLException {
        String sql = "select * from Course";
        return getRows(sql, new CourseRowMapper());
    }

    /**
     * saves given entity
     *
     * @param entity entity must be not null
     * @return the entity - if the given entity was created successfully, otherwise returns null (if the entity already exists)
     */
    @Override
    public Course save(Course entity) {
        String sql = String.format("insert into Course values('%s', %d, %d, %d)", entity.getName(), entity.getTeacherID(), entity.getMaxEnrollment(), entity.getCredits());
        try {
            updateTable(sql);
        }
        catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
        return entity;
    }

    /**
     * removes the entity with the specified id
     * @param entity entity must not be null
     * @return the removed entity or null if there is no such entity
     */
    @Override
    public Course delete(Course entity)  {
        try {
            deleteFromEnrolled(entity.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = String.format("delete from Course where name='%s'",  entity.getName());
        try {
            updateTable(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return entity;
    }

    public void deleteFromEnrolled(String courseName) throws SQLException {
        String sql = String.format("delete from Enrolled where courseName='%s'", courseName);
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        connection.close();
    }

    /**
     * deletes the course with the given name from the list, as well from the teacher's list of courses
     * and the students' lists of enrolled courses
     * @param name string, representing the name of the course to be deleted
     */
    public void deleteByName(String name) {
        String sql = String.format("delete from Course where name='%s'", name);
        try {
            updateTable(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * updates given entity
     * @param entity entity must not be null
     * @return null - if the entity is updated, otherwise returns the entity (if this entity does not exist)
     */
    @Override
    public Course update(Course entity) {
        String sql = String.format("update Course " +
                "set teacherId=%d, maxEnrollment=%d, credits=%d" +
                "where name='%s'", entity.getTeacherID(), entity.getMaxEnrollment(), entity.getCredits(), entity.getName());
        try {
            updateTable(sql);
        }
        catch (SQLException exception) {
            exception.printStackTrace();
            return entity;
        }
        return null;
    }
}
