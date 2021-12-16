module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.uni to javafx.fxml;
    exports com.example.uni;
    exports com.example.uni.view;
    opens com.example.uni.view to javafx.fxml;
}