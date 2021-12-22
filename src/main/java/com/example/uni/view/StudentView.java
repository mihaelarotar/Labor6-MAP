package com.example.uni.view;

import com.example.uni.controller.CourseController;
import com.example.uni.controller.RegistrationSystem;
import com.example.uni.controller.StudentController;
import com.example.uni.controller.TeacherController;
import com.example.uni.entities.Student;
import com.example.uni.exceptions.NonExistingDataException;
import com.example.uni.repository.CourseJdbcRepository;
import com.example.uni.repository.StudentJdbcRepository;
import com.example.uni.repository.TeacherJdbcRepository;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.sql.SQLException;

public class StudentView {
    private static Student student;

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
    Button totalCredits;

    @FXML
    Label message;

    @FXML
    Label message2;

    @FXML
    Label notFoundText;

    @FXML
    Label credits;

    @FXML
    Button studentLogIn;

    @FXML
    TextField idStudent;

    @FXML
    Label course;

    @FXML
    TextField courseName;

    @FXML
    Button register;

    public void loginStudent() throws IOException {
        int id = Integer.parseInt(idStudent.getText());
        RegistrationSystem registrationSystem = initializeRegistrationSystem();
        try {
            student = registrationSystem.getStudentController().findByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (student == null) {
            notFoundText.setText("Student not found");
            return;
        }

        HelloController main = new HelloController();
        /*idStudent.setVisible(false);
        studentLogIn.setVisible(false);
        message.setText("What would you like to do?");
        totalCredits.setVisible(true);*/
        main.changeSceneStudent("student-after-login.fxml");
    }

    public void showTotalCredits() {
        credits.setText(String.format("You have %s credits", String.valueOf(student.getTotalCredits())));
    }

    public void register() {
        String courseTitle = courseName.getText();
        RegistrationSystem registrationSystem = initializeRegistrationSystem();
        boolean registered = false;
        try {
            registered = registrationSystem.register(courseTitle, student.getStudentID());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NonExistingDataException e) {
            e.printStackTrace();
        }
        if (registered) {
            message2.setText("Student registered");
        }
        else
            message2.setText("Registration failed!");

    }
}
