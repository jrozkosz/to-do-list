package com.example.todolist;

import com.example.todolist.models.ToDoAndDoneModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddingToDoController {

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
    private Stage stage;
    @FXML
    public void onAddButtonClick(ActionEvent event){
        if(!textInput.getText().equals("") && !(chosenDeadline.getValue() == null)) {
            System.out.println(textInput.getText());
            dataModel.addToDoTask(textInput.getText(), chosenDeadline.getValue());
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
