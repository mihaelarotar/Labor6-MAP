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

    /**
     * opens a new window for student login
     * @throws IOException if an I/O exception has occurred
     */
    public void loginStudent() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("student-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage1 = new Stage();
        stage1.setTitle("Hello Student!");
        stage1.setScene(scene);
        stage1.show();

    }

    /**
     * changes scene on student login window
     * @param fxml the name of the fxml file to be used as the new scene
     * @throws IOException if an I/O exception has occurred
     */
    public void changeSceneStudent(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(HelloApplication.class.getResource(fxml));
        stage1.getScene().setRoot(pane);
    }

    /**
     * changes scene on teacher login window
     * @param fxml the name of the fxml file to be used as the new scene
     * @throws IOException if an I/O exception has occurred
     */
    public void changeSceneTeacher(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(HelloApplication.class.getResource(fxml));
        stage2.getScene().setRoot(pane);
    }

    /**
     * opens a new window for teacher login
     * @throws IOException if an I/O exception has occurred
     */
    public void loginTeacher() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("teacher-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage2 = new Stage();
        stage2.setTitle("Hello Teacher!");
        stage2.setScene(scene);
        stage2.show();
    }
}