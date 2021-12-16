package com.example.uni.controller;

import com.example.uni.entities.Course;
import com.example.uni.entities.Student;
import com.example.uni.entities.Teacher;
import com.example.uni.exceptions.ExceededValueException;
import com.example.uni.exceptions.NonExistingDataException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationSystem {

    private final StudentController studentController;
    private final CourseController courseController;
    private final TeacherController teacherController;

    public RegistrationSystem(StudentController studentController, CourseController courseController, TeacherController teacherController) {
        this.studentController = studentController;
        this.courseController = courseController;
        this.teacherController = teacherController;
    }

    public StudentController getStudentController() {
        return studentController;
    }

    public CourseController getCourseController() {
        return courseController;
    }

    public TeacherController getTeacherController() {
        return teacherController;
    }

    /**
     * registers a student to a given course
     * @param courseName the course to be enrolled in
     * @param studentID the student to be enrolled
     * @return true if the registration was completed successfully
     * or false if the student is already registered to this course
     * or if there are no more available places for this course
     * @throws NonExistingDataException if the given course or student are not in the list
     */
    public boolean register(String courseName, long studentID) throws SQLException, NonExistingDataException{
        Course course = courseController.findByName(courseName);
        Student student = studentController.findByID(studentID);

        if (course == null) {
            throw new NonExistingDataException("There is no such course in the list");
        }
        if (student == null) {
            throw new NonExistingDataException("There is no such student in the list");
        }

        if (course.getStudentsEnrolled().size() >= course.getMaxEnrollment()) {
            System.out.println("There are no more available places for this course");
            return false;
        }

        if (course.getStudentsEnrolled().contains(student)) {
            System.out.println("Student already registered");
            return false;
        }


        try {
            int credits = student.getTotalCredits()+course.getCredits();
            student.setTotalCredits(credits);
            studentController.update(student);
            student.addCourseToEnrolledCourses(course);
            course.addStudentToStudentsEnrolled(student);
        } catch (ExceededValueException exception) {
            System.out.println(exception.getMessage());
            return false;
        }

        insertIntoEnrolled(studentID, courseName);
        return true;
    }

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

    public void insertIntoEnrolled(long studentId, String courseName) throws SQLException {
        String sql = String.format("insert into Enrolled values(%d, '%s')", studentId, courseName);
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        connection.close();
    }

    /**
     * retrieves the courses with available places
     * @return a new list containing all the courses with available places
     */
    public List<Course> retrieveCoursesWithFreePlaces() throws SQLException {
        List<Course> coursesWithFreePlaces = new ArrayList<>();
        for(Course course : courseController.getAll()) {
            if (course.getMaxEnrollment() > course.getStudentsEnrolled().size()) {
                coursesWithFreePlaces.add(course);
            }
        }
        return coursesWithFreePlaces;
    }

    /**
     * retrieves all students enrolled to a given course
     * @param course the given course
     * @return a new list containing all the enrolled students
     */
    public List<Student> retrieveStudentsEnrolledForACourse(String course) throws SQLException {
        return courseController.findByName(course).getStudentsEnrolled();
    }

    /**
     * retrieves all courses in the list
     * @return the list with all courses
     */
    public List<Course> getAllCourses() throws SQLException {
        return courseController.getAll();
    }

    /**
     * deletes the course with the given name from the list, as well from the teacher's list of courses
     * and the students' lists of enrolled courses
     * @param courseName string, representing the name of the course to be deleted
     */
    public void deleteCourse(String courseName) throws SQLException {
        Course courseToBeDeleted = courseController.findByName(courseName);

        Teacher teacherToBeUpdated = teacherController.getAll().stream()
                .filter(teacher -> teacher.getTeacherID() == courseToBeDeleted.getTeacherID())
                .findFirst()
                .orElseThrow();

        teacherToBeUpdated.deleteCourseFromCourses(courseToBeDeleted);
        courseController.deleteByName(courseName);
    }

    /**
     * adds a new course to the list of courses, as well to the teacher's list of courses
     * @param course entity must be not null
     */
    public void addCourse(Course course) throws SQLException {
        Teacher teacherToBeUpdated = teacherController.getAll().stream()
                .filter(teacher -> teacher.getTeacherID() == course.getTeacherID())
                .findFirst()
                .orElseThrow();
        if(!teacherToBeUpdated.getCourses().contains(course)) {
            teacherToBeUpdated.addCourseToCourses(course);
        }

        courseController.add(course);
    }
}
