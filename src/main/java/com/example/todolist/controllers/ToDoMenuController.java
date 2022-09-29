package com.example.todolist;

import com.example.todolist.models.ToDoModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ToDoMenuController implements Initializable {
    @FXML
    private ListView<String> toDoListView;
    @FXML
    private ListView<String> doneListView;
    private ToDoModel dataModel;
//    private Scene scene;
//    private Stage stage;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataModel = new ToDoModel();
        toDoListView.setItems(dataModel.getToDoList());
        doneListView.setItems(dataModel.getDoneList());
    }
    @FXML
    public void onPlusButtonClick(ActionEvent event) throws IOException {
        // """ opens addingToDo Scene """ //
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addingToDo.fxml"));
        root = loader.load();
        com.example.todolist.AddingToDoController addingController = loader.getController();
        System.out.println(addingController);
        addingController.setModel(dataModel);
        Stage newStage = new Stage();
        newStage.setTitle("Adding to-do task");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    @FXML
    public void updateToDoList(ToDoModel model) throws IOException {
        this.dataModel = model;
        System.out.println("update wykonany");
        toDoListView.setItems(model.getToDoList());
    }
}