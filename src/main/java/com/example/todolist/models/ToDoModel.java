package com.example.todolist.models;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ToDoModel {
    private final ObservableList<String> toDoList;
    private final ObservableList<String> doneList;

    public ToDoModel() {
        toDoList = FXCollections.observableArrayList();
        doneList = FXCollections.observableArrayList();
        // pobrac z bazy dane i wstawic do list //
    }

    public ObservableList<String> getToDoList() {
        return toDoList;
    }

    public ObservableList<String> getDoneList() {
        return doneList;
    }

}
