package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.DAO.*;

import java.io.IOException;

public class ALoginController {
    @FXML
    private Button btn_login;

    @FXML
    private Button btn_main;

    @FXML
    private Button btn_reg;

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
            if(usertxt.getText().equals("admin") && psswdtxt.getText().equals("admin")){
                App.setRoot("admin");
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de inicio de sesión");
                alert.setHeaderText(null);
                alert.setContentText("Usuario o contraseña incorrectos.");
                alert.showAndWait();
            }
        }
    @FXML
    private void btnReg() throws IOException {
        App.setRoot("Register");
    }
    @FXML
    private void goBack() throws IOException {
        App.setRoot("home");
    }

}

