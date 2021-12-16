package com.example.uni.repository;

import com.example.uni.entities.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentJdbcRepository extends JdbcRepository<Student> {


    /**
     * @return all entities
     */
    @Override
    public List<Student> getAll() throws SQLException {
        String sql = "select * from Student";
        return getRows(sql, new StudentRowMapper());
    }

    /**
     * returns the student with the given ID
     * @param studentID long int, representing the ID of the student to be returned
     * @return the student with the given ID
     */
    public Student findByID (long studentID) throws SQLException {
        String sql = String.format("select * from Student where studentId='%s'", studentID);
        return getEntity(sql, new StudentRowMapper());
    }

    /**
     * saves given entity
     *
     * @param entity entity must be not null
     * @return the entity - if the given entity was created successfully, otherwise returns null (if the entity already exists)
     */
    @Override
    public Student save(Student entity) {
        String sql = String.format("insert into Student values(%d, '%s', '%s', %d)", entity.getStudentID(), entity.getFirstName(), entity.getLastName(), entity.getTotalCredits());
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
     *
     * @param entity entity must not be null
     * @return the removed entity or null if there is no such entity
     */
    @Override
    public Student delete(Student entity) {
        String sql = String.format("delete from Student where studentId=%d",  entity.getStudentID());
        try {
            updateTable(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return entity;
    }

    /**
     * deletes the student with the given ID from the list
     * @param studentID long, representing the ID of the student to be removed
     */
    public void deleteByID(long studentID) {
        String sql = String.format("delete from Student where studentId=%d", studentID);
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
    public Student update(Student entity) {
        String sql = String.format("update Student " +
                "set firstName='%s', lastName='%s', totalCredits=%d " +
                "where studentId=%d", entity.getFirstName(), entity.getLastName(), entity.getTotalCredits(), entity.getStudentID());
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
