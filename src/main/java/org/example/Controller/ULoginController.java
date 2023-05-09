package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.App;
import org.example.DAO.*;
import org.example.DOMAIN.User;

import java.io.IOException;
import java.sql.SQLException;

public class ULoginController {

    @FXML
    private TextField usertxt;


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
                //  guardar los demas datos del usuario logueado para su futuro uso en otros metodos
                UDAO.userId = u.getId();
                UDAO.userDNI=u.getDNI();
                UDAO.userMail=u.getEmail();
                // Usuario autenticado, navegar a la vista de admin
                App.setRoot("users");
            } else {
                // Usuario o contraseña incorrectos, mostrar alerta de error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Log-in Error");
                alert.setHeaderText(null);
                alert.setContentText("Incorrect User or Password.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Error al buscar en la base de datos, mostrar alerta de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DataBase Error");
            alert.setHeaderText(null);
            alert.setContentText("Couldn't find user in the database.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void btnDelete() throws IOException{

    }

    @FXML
    private void goBack() throws IOException {
        App.setRoot("home");
    }
}
