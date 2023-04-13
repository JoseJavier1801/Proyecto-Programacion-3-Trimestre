package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class homeController {

    @FXML
    private Button btn_login;

    @FXML
    private TextField usertxt;

    @FXML
    private Label errortxt;

    @FXML
    private PasswordField psswdtxt;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void btnLogin() throws IOException {
        if (usertxt.getText().equals("user") && psswdtxt.getText().equals("user")) {
            App.setRoot("second");
        }else{
            if(usertxt.getText().equals("admin") && psswdtxt.getText().equals("admin")){
                App.setRoot("third");
            }else{
                errortxt.setText("LOG-IN ERROR");
            }
        }
    }
}