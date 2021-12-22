package com.example.uni.view;

import com.example.uni.HelloApplication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    private static Stage stage1;
    private static Stage stage2;

    @FXML
    Button teacherLogIn;


    @FXML
    Button studentLogIn;

    public void loginStudent() throws IOException {
//        int id = Integer.parseInt(idStudent.getText());
//        RegistrationSystem registrationSystem = initializeRegistrationSystem();
//        Student student = null;
//        try {
//            student = registrationSystem.getStudentController().findByID(id);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        if (student == null) {
//            notFoundText.setText("Student not found");
//            return;
//        }
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("student-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage1 = new Stage();
        stage1.setTitle("Hello Student!");
        stage1.setScene(scene);
        stage1.show();

    }

    public void changeSceneStudent(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(HelloApplication.class.getResource(fxml));
        stage1.getScene().setRoot(pane);
    }

    public void changeSceneTeacher(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(HelloApplication.class.getResource(fxml));
        stage2.getScene().setRoot(pane);
    }

    public void loginTeacher() throws IOException {
//        int id = Integer.parseInt(idTeacher.getText());
//        RegistrationSystem registrationSystem = initializeRegistrationSystem();
//        Teacher teacher = null;
//        try {
//            teacher = registrationSystem.getTeacherController().findByID(id);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        if (teacher == null) {
//            notFoundText.setText("Teacher not found");
//            return;
//        }
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("teacher-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage2 = new Stage();
        stage2.setTitle("Hello Teacher!");
        stage2.setScene(scene);
        stage2.show();




    }
}