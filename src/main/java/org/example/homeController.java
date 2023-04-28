package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class homeController {

    @FXML
    private Button btn_admin;

    @FXML
    private Button btn_user;


    @FXML
    private void AdminLogin() throws IOException {
            App.setRoot("adminLogin");
    }
    @FXML
    private void UserLogin() throws IOException {
        App.setRoot("UsersLogin");
    }
}