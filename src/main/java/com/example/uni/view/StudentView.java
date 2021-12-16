package com.example.uni.view;

import com.example.uni.controller.CourseController;
import com.example.uni.controller.RegistrationSystem;
import com.example.uni.controller.StudentController;
import com.example.uni.controller.TeacherController;
import com.example.uni.entities.Student;
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
    Student student;

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
    Label notFoundText;

    @FXML
    Label credits;

    @FXML
    Button studentLogIn;

    @FXML
    TextField idStudent;

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

        idStudent.setVisible(false);
        studentLogIn.setVisible(false);
        message.setText("What would you lie to do?");
        totalCredits.setVisible(true);
    }

    public void showTotalCredits() {
        credits.setText(String.valueOf(student.getTotalCredits()));
    }
}
