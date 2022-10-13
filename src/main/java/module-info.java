module com.example.todolist {
    requires javafx.controls;
    requires javafx.fxml;
//    requires javafx.web;
    requires java.sql;

    opens com.example.todolist.controllers to javafx.fxml;
//    opens com.example.todolist to javafx.fxml;
    exports com.example.todolist;
    exports com.example.todolist.controllers;
}