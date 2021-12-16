package com.example.uni.controller;

import com.example.uni.entities.Student;
import com.example.uni.repository.StudentJdbcRepository;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class StudentController extends Controller<Student> {
    public StudentController(StudentJdbcRepository repository) {
        super(repository);
    }

    /**
     * deletes the student with the given ID from the list
     * @param studentID long, representing the ID of the student to be removed
     */
    public void deleteByID(long studentID) {
        StudentJdbcRepository studentRepository = (StudentJdbcRepository) repository;
        studentRepository.deleteByID(studentID);
    }

    /**
     * returns the student with the given ID
     * @param studentID long int, representing the ID of the student to be returned
     * @return the student with the given ID
     */
    public Student findByID(long studentID) throws SQLException {
        StudentJdbcRepository studentRepository = (StudentJdbcRepository) repository;
        return studentRepository.findByID(studentID);
    }

    /**
     * filters students by the number of total credits
     * @param totalCredits the number of total credits of the students to be shown
     * @return the students with the number of total credits equal to the parameter
     */
    public List<Student> filterByTotalCredits(int totalCredits) throws SQLException {
        return filter( student -> student.getTotalCredits() == totalCredits);
    }

    /**
     * sorts students alphabetically by name
     */
    public void sortByName() throws SQLException {
        Comparator<Student> compareByName = Comparator.comparing(Student::getLastName).thenComparing(Student::getFirstName);
        sort(compareByName);
    }

    /**
     * sorts students descending by the number of credits
     */
    public void sortByCreditsDescending() throws SQLException {
        Comparator<Student> compareByCredits = Comparator.comparingInt(Student::getTotalCredits);
        sort(compareByCredits.reversed());
    }
}
