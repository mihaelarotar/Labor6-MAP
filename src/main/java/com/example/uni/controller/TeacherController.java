package com.example.uni.controller;

import com.example.uni.entities.Teacher;
import com.example.uni.repository.TeacherJdbcRepository;

import java.sql.SQLException;

public class TeacherController extends Controller<Teacher> {
    public TeacherController(TeacherJdbcRepository repository) {
        super(repository);
    }

    /**
     * deletes the teacher with the given ID from the list
     * @param teacherID int, representing the ID of the teacher to be removed
     */
    public void deleteByID(int teacherID) {
        TeacherJdbcRepository teacherRepository = (TeacherJdbcRepository) repository;
        teacherRepository.deleteByID(teacherID);
    }

    /**
     * returns the teacher with the given ID
     * @param teacherID int, representing the ID of the teacher to be returned
     * @return the teacher with the given ID
     */
    public Teacher findByID(int teacherID) throws SQLException {
        TeacherJdbcRepository teacherRepository = (TeacherJdbcRepository) repository;
        return teacherRepository.findByID(teacherID);
    }
}
