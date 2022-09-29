package com.example.todolist;

import com.example.todolist.models.ToDoModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddingToDoController {

    @FXML
    private Button addButton;
    @FXML
    private TextArea textInput;
    @FXML
    private DatePicker chosenDeadline;
    private ToDoModel dataModel;
    private Stage stage;
    @FXML
    public void onAddButtonClick(ActionEvent event) throws IOException {
        System.out.println(this);
        dataModel.getToDoList().add(textInput.getText());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.hide();
    }
    public void setModel(ToDoModel model) {
        this.dataModel = model;
    }
}
