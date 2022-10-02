package com.example.todolist;

import com.example.todolist.controllers.Task;
import com.example.todolist.models.ToDoAndDoneModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AddingToDoController {

    @FXML
    private Button addButton;
    @FXML
    private TextArea textInput;
    @FXML
    private DatePicker chosenDeadline;
    private ToDoAndDoneModel dataModel;
    private ListView<Task> listView;
    private Stage stage;
    @FXML
    public void onAddButtonClick(ActionEvent event) throws IOException, SQLException {
        System.out.println(this);
        dataModel.addToDoTask(textInput.getText(), chosenDeadline.getValue());
//        listView.setItems(dataModel.getToDoList());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.hide();
    }
    public void setModel(ToDoAndDoneModel model) {
        this.dataModel = model;
    }

    public void setListView(ListView<Task> listView) {
        this.listView = listView;
    }
}
