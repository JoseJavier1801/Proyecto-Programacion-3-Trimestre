package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.DAO.*;

import java.io.IOException;

public class RegisterController {

    @FXML
    private Button btn_main;

    @FXML
    private void goBack() throws IOException {
        App.setRoot("home");
    }

}
