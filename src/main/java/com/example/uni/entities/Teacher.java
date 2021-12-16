package com.example.uni.entities;

import com.example.uni.exceptions.InvalidDataException;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {
    private int teacherID;
    private List<Course> courses;

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, int teacherID) {
        super(firstName, lastName);
        try {
            this.teacherID = teacherID;
            this.courses = new ArrayList<>();
            validate();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return teacherID == teacher.teacherID;
    }

    /**
     * data validator
     * checks if the integers are negative
     * @throws InvalidDataException if data is not valid
     */
    private void validate() throws InvalidDataException {
        if(teacherID <= 0) {
            throw new InvalidDataException("Invalid ID");
        }
    }

    @Override
    public String toString() {
        List<String> courseNames = new ArrayList<>();
        for (Course course : courses) {
            courseNames.add(course.getName());
        }
        return "Teacher{" +
                "teacherID=" + teacherID +
                ", firstName=" + getFirstName() +
                ", lastName=" + getLastName() +
                ", courses=" + courseNames +
                '}';
    }

    /**
     * adds a new course to the list of courses
     * @param course the course to be added
     */
    public void addCourseToCourses(Course course) {
        this.courses.add(course);
    }

    /**
     * removes a course from the list
     * @param course the course to be removed
     */
    public void deleteCourseFromCourses(Course course) {
        this.courses.remove(course);
    }
}
