package com.example.todolist.controllers;
;
import com.example.todolist.Main;
import com.example.todolist.models.User;
import com.example.todolist.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpController {
    @FXML
    private Button signUpButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorText;
    @FXML
    private ImageView goBackToSignInButton;

    @FXML
    public void onSignUpButtonClick(ActionEvent event) throws IOException, SQLException {
        UserModel userModel = new UserModel();

        User user = userModel.getUser(usernameTextField.getText(), passwordField.getText());
        if(usernameTextField.getText().equals("") || passwordField.getText().equals("")){
            errorText.setText("No field can be empty!");
        } else if (userModel.isUsernameTaken(usernameTextField.getText())) {
            errorText.setText("The username is already taken");
        } else if(userModel.isPasswordTaken(passwordField.getText())){
            errorText.setText("The password is already taken");
        } else {
            userModel.insertUser(usernameTextField.getText(), passwordField.getText());
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("signIn.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign In");
            stage.show();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Your account has been created successfully!");
            alert.show();
        }
    }

    @FXML
    public void onGoBackButtonClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("signIn.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Sign In");
        stage.show();
    }
}
