package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.App;
import org.example.DAO.*;
import org.example.DOMAIN.Admin;
import java.io.IOException;
import java.sql.SQLException;

public class ALoginController {

    @FXML
    private TextField usertxt;

    @FXML
    private PasswordField psswdtxt;

    @FXML
    private void btnLogin() throws IOException {
        String username = usertxt.getText();
        String password = psswdtxt.getText();
        
        AdminDAO adminDAO = AdminDAO.getInstance();
        try {
            Admin a = adminDAO.findByUsernameAndPassword(username, password);
            if (a != null) {
                //  guardar los demas datos del administrador logueado para su futuro uso en otros metodos
                adminDAO.adminId = a.getId();
                adminDAO.adminDNI=a.getDNI();
                adminDAO.adminMail=a.getEmail();
                // Admin autenticado, ir a la pagina admin
                App.setRoot("admin");
            } else {
                // Usuario o contraseña incorrectos, mostrar alerta de error
                showError("Incorrect Admin-Name or password.");
            }
        } catch (SQLException e) {
            // Error al buscar en la base de datos, mostrar alerta de error
            showError("Admin didn't exist on the database");
            e.printStackTrace();
        }
    }

    @FXML
    private void btnDelete() throws IOException{

    }


    private void showError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Log-in Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void goBack() throws IOException {
        App.setRoot("home");
    }

}

