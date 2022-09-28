package com.example.todolist;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ToDoMenuController {
    @FXML
    private VBox toDoList;

    @FXML
    protected void onPlusButtonClick() {
        Label newToDoTask = new Label("Dodane zadanie");
        toDoList.getChildren().add(newToDoTask);
    }
}