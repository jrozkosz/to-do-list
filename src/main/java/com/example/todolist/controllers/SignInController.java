package com.example.todolist.controllers;

import com.example.todolist.Main;
import com.example.todolist.models.ToDoAndDoneModel;
import com.example.todolist.models.User;
import com.example.todolist.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Hyperlink signUpLink;


    @FXML
    public void onSignInButtonClick(ActionEvent event) throws IOException, SQLException {
        UserModel userModel = new UserModel();

        User user = userModel.getUser(usernameTextField.getText(), passwordField.getText());
        if(usernameTextField.getText().equals("") || passwordField.getText().equals("")){
            errorText.setText("No field can be empty!");
        } else if (user == null) {
            errorText.setText("There is no user like that in the system!");
        } else {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("menu.fxml"));
            loader.setClassLoader(getClass().getClassLoader());
            Parent root = loader.load();
            ToDoMenuController controller = loader.getController();
            controller.displayAllTasks(new ToDoAndDoneModel(user));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    public void onSignUpLinkClick(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("signUp.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign Up");
            stage.show();
        } catch (Exception e) {
            System.out.println("Register try failed");
            e.printStackTrace();
        }
    }
}
