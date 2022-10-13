package com.example.todolist.models;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class ToDoAndDoneModel {
    private final MyObservableList toDoList;
    private final MyObservableList doneList;
    private User user;
    private Connection connection;
    private boolean afterLoadingData = false;

    public ToDoAndDoneModel(User user) throws SQLException {
        this.user = user;
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/to_do_app", "root", "!Root2022");
        toDoList = new MyObservableList(connection,  true);
        doneList = new MyObservableList(connection, false);

        // pobrac z bazy dane i wstawic do list //
        try {
            PreparedStatement prepStatToDo = connection.prepareStatement(
                    "select description, deadline from tasks_to_do where user_id = ?");
            prepStatToDo.setInt(1, user.getUserId());
            ResultSet rsToDo = prepStatToDo.executeQuery();
            while (rsToDo.next()) {
                toDoList.getMyList().add(new Task(
                        rsToDo.getString("description"),
                        rsToDo.getDate("deadline").toLocalDate()));
            }
            PreparedStatement prepStatDone = connection.prepareStatement(
                    "select description, deadline from tasks_done where user_id = ?");
            prepStatDone.setInt(1, user.getUserId());
            ResultSet rsDone = prepStatDone.executeQuery();
            while (rsDone.next()) {
                doneList.getMyList().add(new Task(
                        rsDone.getString("description"),
                        rsDone.getDate("deadline").toLocalDate()));
            }
            FXCollections.sort(toDoList.getMyList());
            FXCollections.sort(doneList.getMyList());
            afterLoadingData = true;
        } catch (Exception e) {
            System.out.println("Importing tasks from database failed");
            e.printStackTrace();
        }
    }

    public ObservableList<Task> getToDoList() {
        return toDoList.getMyList();
    }

    public ObservableList<Task> getDoneList() {
        return doneList.getMyList();
    }

    public void addToDoTask(String description, LocalDate deadline){
        toDoList.getMyList().add(new Task(description, deadline));
    }


    public class MyObservableList {
        private final ObservableList<Task> myList = FXCollections.observableArrayList();
        private boolean toDoTable;

        public MyObservableList(Connection connection, boolean toDoTable) {
            this.toDoTable = toDoTable;
            myList.addListener(new ListChangeListener<Task>() {
                @Override
                public void onChanged(Change<? extends Task> change) {
                    while (change.next()) {
                        if (afterLoadingData) {
                            if (change.wasRemoved()) {
                                try {
                                    Task removedTask = change.getRemoved().get(0);
                                    PreparedStatement prepStatToDo;
                                    if(toDoTable) {
                                        prepStatToDo = connection.prepareStatement(
                                                "delete from tasks_to_do where user_id = ? and description = ? and deadline = ?");
                                    } else{
                                        prepStatToDo = connection.prepareStatement(
                                                "delete from tasks_done where user_id = ? and description = ? and deadline = ?");
                                    }
                                    prepStatToDo.setInt(1, user.getUserId());
                                    prepStatToDo.setString(2, removedTask.getDescription());
                                    prepStatToDo.setDate(3, Date.valueOf(removedTask.getDeadline()));
                                    prepStatToDo.execute();
                                } catch (SQLException ex) {
                                    System.out.println("Removing task from db after change failed");
                                    throw new RuntimeException(ex);
                                }
                            }
                            if (change.wasAdded()) {
                                try {
                                    Task addedTask = change.getAddedSubList().get(0);
                                    PreparedStatement prepStatDone;
                                    if(toDoTable) {
                                        prepStatDone = connection.prepareStatement(
                                                "insert into tasks_to_do values (NULL, ?, ?, ?);");
                                    } else {
                                        prepStatDone = connection.prepareStatement(
                                                "insert into tasks_done values (NULL, ?, ?, ?);");
                                    }
                                    prepStatDone.setString(1, addedTask.getDescription());
                                    prepStatDone.setDate(2, Date.valueOf(addedTask.getDeadline()));
                                    prepStatDone.setInt(3, user.getUserId());
                                    prepStatDone.execute();
                                    FXCollections.sort(getMyList());
                                } catch (SQLException ex) {
                                    System.out.println("Adding task to db after change failed");
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                    }
                }
            });
        }

        public ObservableList<Task> getMyList() {
            return myList;
        }
    }
}
