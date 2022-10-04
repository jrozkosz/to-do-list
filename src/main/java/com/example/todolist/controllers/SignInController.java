package com.example.todolist;

import com.example.todolist.ToDoMenuController;
import com.example.todolist.models.ToDoAndDoneModel;
import com.example.todolist.models.User;
import com.example.todolist.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SignInController {
    @FXML
    private Button signInButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorText;


    @FXML
    public void onSignInButtonClick(ActionEvent event) throws IOException, SQLException {
        UserModel userModel = new UserModel();

        User user = userModel.getUser(usernameTextField.getText(), passwordField.getText());
        if(usernameTextField.getText().equals("") || passwordField.getText() .equals("")){
            errorText.setText("No field can be empty!");
        } else if (user == null) {
            errorText.setText("There is no user like that in the system!");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
            Parent root = loader.load();
            ToDoMenuController controller = loader.getController();
            controller.displayAllTasks(new ToDoAndDoneModel(user));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
