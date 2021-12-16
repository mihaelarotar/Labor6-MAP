package com.example.uni.repository;

import com.example.uni.entities.Teacher;

import java.sql.SQLException;
import java.util.List;

public class TeacherJdbcRepository extends JdbcRepository<Teacher> {



    /**
     * removes the entity with the specified id
     * @param entity entity must not be null
     * @return the removed entity or null if there is no such entity
     */
    @Override
    public Teacher delete(Teacher entity) {
        String sql = String.format("delete from Teacher where teacherId=%d",  entity.getTeacherID());
        try {
            updateTable(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return entity;
    }

    /**
     * deletes the teacher with the given ID from the list
     * @param teacherID int, representing the ID of the teacher to be removed
     */
    public void deleteByID(int teacherID) {
        String sql = String.format("delete from Teacher where teacherId= %d", teacherID);
        try {
            updateTable(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * returns the teacher with the given ID
     * @param teacherID int, representing the ID of the teacher to be returned
     * @return the teacher with the given ID
     */
    public Teacher findByID(int teacherID) throws SQLException {
        String sql = String.format("select * from Teacher where teacherId= %d", teacherID);
        return getEntity(sql, new TeacherRowMapper());
    }

    /**
     * @return all entities
     */
    @Override
    public List<Teacher> getAll() throws SQLException {
        String sql = "select * from Teacher";
        return getRows(sql, new TeacherRowMapper());
    }

    /**
     * saves given entity
     * @param entity entity must be not null
     * @return the entity - if the given entity was created successfully, otherwise returns null (if the entity already exists)
     */
    @Override
    public Teacher save(Teacher entity) {
        String sql = String.format("insert into Teacher values(%d, '%s', '%s')", entity.getTeacherID(), entity.getFirstName(), entity.getLastName());
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
     * updates given entity
     *
     * @param entity entity must not be null
     * @return null - if the entity is updated, otherwise returns the entity (if this entity does not exist)
     */
    @Override
    public Teacher update(Teacher entity) {
        String sql = String.format("update Teacher " +
                "set firstName= '%s', lastName= '%s'" +
                "where teacherId= %d", entity.getFirstName(), entity.getLastName(), entity.getTeacherID());
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
