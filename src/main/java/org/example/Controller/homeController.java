package org.example.Controller;

import javafx.fxml.FXML;
import org.example.App;

import java.io.IOException;

/**
 * Home controller que incluye los metodos para mostrar las ventanas de inicio de sesion de administrador/usuario
 * o permite registrar un nievo usuario/administrador
 */
public class homeController {

    @FXML
    private void AdminLogin() throws IOException {
            App.setRoot("adminLogin");
    }
    @FXML
    private void UserLogin() throws IOException {
        App.setRoot("UsersLogin");
    }

    @FXML
    private void reg() throws IOException {
        App.setRoot("Register");
    }

}