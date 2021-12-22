package com.example.uni.view;

import com.example.uni.controller.CourseController;
import com.example.uni.controller.RegistrationSystem;
import com.example.uni.controller.StudentController;
import com.example.uni.controller.TeacherController;
import com.example.uni.entities.Course;
import com.example.uni.entities.Student;
import com.example.uni.entities.Teacher;
import com.example.uni.repository.CourseJdbcRepository;
import com.example.uni.repository.StudentJdbcRepository;
import com.example.uni.repository.TeacherJdbcRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TeacherView {
    private static Teacher teacher;

    public RegistrationSystem initializeRegistrationSystem() {
        CourseJdbcRepository courseRepository = new CourseJdbcRepository();
        CourseController courseController = new CourseController(courseRepository);

        StudentJdbcRepository studentRepository = new StudentJdbcRepository();
        StudentController studentController = new StudentController(studentRepository);

        TeacherJdbcRepository teacherRepository = new TeacherJdbcRepository();
        TeacherController teacherController = new TeacherController(teacherRepository);

        return new RegistrationSystem(studentController, courseController, teacherController);
    }


    @FXML
    Label message;

    @FXML
    Label message2;

    @FXML
    Label notFoundText;

    @FXML
    Button teacherLogIn;

    @FXML
    TextField idTeacher;

    @FXML
    Button refresh;

    @FXML
    Button showStudents;

    @FXML
    Label course;

    @FXML
    ListView<Student> listView;

    @FXML
    TextField courseName;

    public void loginTeacher() throws IOException {
        int id = Integer.parseInt(idTeacher.getText());
        RegistrationSystem registrationSystem = initializeRegistrationSystem();
        try {
            teacher = registrationSystem.getTeacherController().findByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (teacher == null) {
            notFoundText.setText("Teacher not found");
            return;
        }

        HelloController main = new HelloController();
        main.changeSceneTeacher("teacher-after-login.fxml");
    }

    public void showEnrolledStudents() {
        String courseTitle = courseName.getText();
        RegistrationSystem registrationSystem = initializeRegistrationSystem();
        Course foundCourse = teacher.getCourses().stream()
                .filter(myCourse -> myCourse.getName().equals(courseTitle))
                .findFirst()
                .orElse(null);

        if (foundCourse == null) {
            message2.setText("This is not your course!");
            return;
        }

        try {
            List<Student> students = registrationSystem.retrieveStudentsEnrolledForACourse(courseTitle);
            ObservableList<Student> observableList = FXCollections.observableList(students);
            listView.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshPage() {

    }
}
