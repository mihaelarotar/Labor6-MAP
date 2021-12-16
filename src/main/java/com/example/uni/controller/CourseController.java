package com.example.uni.controller;

import com.example.uni.entities.Course;
import com.example.uni.repository.CourseJdbcRepository;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;


public class CourseController extends Controller<Course> {

    public CourseController(CourseJdbcRepository courseRepository) {
        super(courseRepository);
    }

    /**
     * deletes the course with the given name from the list, as well from the teacher's list of courses
     * and the students' lists of enrolled courses
     * @param name string, representing the name of the course to be deleted
     */
    public void deleteByName(String name) {
        CourseJdbcRepository courseRepository = (CourseJdbcRepository) repository;
        courseRepository.deleteByName(name);
    }

    /**
     * returns the course with the given name
     * @param name string, representing the title of the course to be returned
     * @return the course with the given name
     */
    public Course findByName(String name) throws SQLException {
        CourseJdbcRepository courseRepository = (CourseJdbcRepository) repository;
        return courseRepository.findByName(name);
    }

    /**
     * filters courses by the number of credits
     * @param credits the number of credits of the courses to be shown
     * @return the courses with the number of credits equal to the parameter
     */
    public List<Course> filterByCredits(int credits) throws SQLException {
        return filter( course -> course.getCredits() == credits);
    }

    /**
     * sorts courses alphabetically by name
     */
    public void sortByName() throws SQLException {
        Comparator<Course> compareByName = Comparator.comparing(Course::getName);
        sort(compareByName);
    }

    /**
     * sorts courses ascending by the number of credits
     */
    public void sortByCredits() throws SQLException {
        Comparator<Course> compareByCredits = Comparator.comparingInt(Course::getCredits);
        sort(compareByCredits);
    }
}
