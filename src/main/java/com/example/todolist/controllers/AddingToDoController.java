package com.example.todolist.controllers;

import com.example.todolist.models.ToDoAndDoneModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddingToDoController implements Initializable {

    @FXML
    private Button addButton;
    @FXML
    private Label textNotEmpty;
    @FXML
    private Label pickDateFirst;
    @FXML
    private TextArea textInput;
    @FXML
    private DatePicker chosenDeadline;
    private ToDoAndDoneModel dataModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButton.setCursor(Cursor.HAND);
    }
    @FXML
    public void onAddButtonClick(ActionEvent event){
        if(!textInput.getText().equals("") && !(chosenDeadline.getValue() == null)) {
            dataModel.addToDoTask(textInput.getText(), chosenDeadline.getValue());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.hide();
        } else if (textInput.getText().equals("")) {
            textNotEmpty.setText("Text field cannot be empty!");
        } else if (chosenDeadline.getValue() == null) {
            pickDateFirst.setText("Yo have to pick a date first!");
        }
    }
    public void setModel(ToDoAndDoneModel model) {
        this.dataModel = model;
    }
}
