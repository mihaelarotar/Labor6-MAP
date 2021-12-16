package com.example.uni.entities;

import com.example.uni.exceptions.ExceededValueException;
import com.example.uni.exceptions.InvalidDataException;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person{
    private long studentID;
    private int totalCredits;
    private List<Course> enrolledCourses;

    public Student()  {

    }

    public Student(String firstName, String lastName, long studentID) throws InvalidDataException {
        super(firstName, lastName);
        if (studentID <= 0) {
            throw new InvalidDataException("Invalid ID");
        }
        this.studentID = studentID;
        this.totalCredits = 0;
        this.enrolledCourses = new ArrayList<>();
    }


    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) throws ExceededValueException {
        if (totalCredits > 30) {
            throw new ExceededValueException("Number of total credits cannot be larger than 30");
        }
        this.totalCredits = totalCredits;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentID == student.studentID;
    }

    @Override
    public String toString() {
        List<String> courses = new ArrayList<>();
        for (Course course : enrolledCourses) {
            courses.add(course.getName());
        }
        return "Student{" +
                "studentID=" + studentID +
                ", firstName=" + getFirstName() +
                ", lastName=" + getLastName() +
                ", totalCredits=" + totalCredits +
               // ", enrolledCourses=" + courses +
                '}';
    }

    /**
     * adds a course to the list of enrolled courses and updates the total number of credits
     * @param course the course to be added
     * @throws ExceededValueException if the maximum number of credits is exceeded
     */
    public void addCourseToEnrolledCourses(Course course) throws ExceededValueException {
        if (this.totalCredits + course.getCredits() >= 30) {
            throw new ExceededValueException("The maximum number of 30 credits was exceeded");
        }
        this.enrolledCourses.add(course);
        this.totalCredits += course.getCredits();
    }

    /**
     * removes a course from the list of enrolled courses and updates the total number of credits
     * @param course the course to be removed
     */
    public void removeCourseFromEnrolledCourses(Course course) {
        this.enrolledCourses.remove(course);
        this.totalCredits -= course.getCredits();
    }
}
