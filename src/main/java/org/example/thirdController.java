package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class thirdController {
    @FXML
    private Button logbutton;

    @FXML
    private void closesesion()throws IOException {
        App.setRoot("home");
    }
}
