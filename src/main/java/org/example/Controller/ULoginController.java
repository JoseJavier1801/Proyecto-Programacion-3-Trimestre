package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.App;
import org.example.DAO.*;
import org.example.DOMAIN.Admin;
import org.example.DOMAIN.User;

import java.io.IOException;
import java.sql.SQLException;

public class ULoginController {
    @FXML
    private Button btn_login;

    @FXML
    private Button btn_main;

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
        String username = usertxt.getText();
        String password = psswdtxt.getText();

        UserDAO UDAO = UserDAO.getInstance();
        try {
            User u = UDAO.findByUsernameAndPassword(username, password);
            if (u != null) {
                // Usuario autenticado, navegar a la vista de admin
                App.setRoot("users");
            } else {
                // Usuario o contraseña incorrectos, mostrar alerta de error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de inicio de sesión");
                alert.setHeaderText(null);
                alert.setContentText("Usuario o contraseña incorrectos.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Error al buscar en la base de datos, mostrar alerta de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de base de datos");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo buscar el usuario en la base de datos.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
    @FXML
    private void goBack() throws IOException {
        App.setRoot("home");
    }
}
