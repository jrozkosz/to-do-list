package com.example.todolist.models;

import com.example.todolist.controllers.Task;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class ToDoAndDoneModel {
    //    private final ObservableList<Task> toDoList;
//    private final ObservableList<Task> doneList;
    private final MyObservableList toDoList;
    private final MyObservableList doneList;
    private Connection connection;
    private boolean afterLoadingData = false;

    public ToDoAndDoneModel() throws SQLException {
//        toDoList = FXCollections.observableArrayList();
//        doneList = FXCollections.observableArrayList();
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/to_do_app", "root", "mysqlrozki01");
        toDoList = new MyObservableList(connection,  true);
        doneList = new MyObservableList(connection, false);

        // pobrac z bazy dane i wstawic do list //
        try {
            Statement statement = connection.createStatement();
            ResultSet rsToDo = statement.executeQuery("select description, deadline from tasks_to_do");
            while (rsToDo.next()) {
                toDoList.getMyList().add(new Task(
                        rsToDo.getString("description"),
                        rsToDo.getDate("deadline").toLocalDate()));
            }
            ResultSet rsDone = statement.executeQuery("select description, deadline from tasks_done");
            while (rsDone.next()) {
                doneList.getMyList().add(new Task(
                        rsDone.getString("description"),
                        rsDone.getDate("deadline").toLocalDate()));
            }
            afterLoadingData = true;
            FXCollections.sort(toDoList.getMyList());
            FXCollections.sort(doneList.getMyList());
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
                                    prepStatToDo.setInt(1, 2);
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
                                    FXCollections.sort(getMyList());
                                    Task addedTask = change.getAddedSubList().get(0);
                                    System.out.println(change.getAddedSubList());
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
                                    prepStatDone.setInt(3, 2);
                                    prepStatDone.execute();
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
