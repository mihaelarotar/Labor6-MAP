package com.example.uni.entities;

import com.example.uni.exceptions.InvalidDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {
    private String name;
    private int teacherID;
    private int maxEnrollment;
    private List<Student> studentsEnrolled;
    private int credits;

    public Course() {
    }

    public Course(String name, int teacherID, int maxEnrollment, int credits) {
        if (name.isEmpty()) {
            throw new InvalidDataException("Name cannot be null");
        }
        if (maxEnrollment < 0 || credits < 0) {
            throw new InvalidDataException("Value cannot be negative");
        }
        this.name = name;
        this.teacherID = teacherID;
        this.maxEnrollment = maxEnrollment;
        this.studentsEnrolled = new ArrayList<>();
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    public List<Student> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public void setStudentsEnrolled(List<Student> studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, teacherID, maxEnrollment, studentsEnrolled, credits);
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", teacherID=" + teacherID +
                ", maxEnrollment=" + maxEnrollment +
                ", studentsEnrolled=" + studentsEnrolled +
                ", credits=" + credits +
                '}';
    }

    /**
     * adds a new student to the list of enrolled students
     * @param student the student to be added
     */
    public void addStudentToStudentsEnrolled(Student student) {
        this.studentsEnrolled.add(student);
    }
}
