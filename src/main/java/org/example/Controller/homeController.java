package org.example.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.App;
import org.example.DAO.*;

import java.io.IOException;

public class homeController {

    @FXML
    private Button btn_admin;

    @FXML
    private Button btn_user;

    @FXML
    private Button btn_reg;


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