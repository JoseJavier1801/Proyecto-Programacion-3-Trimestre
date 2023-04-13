package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class secondController {

    @FXML
    private Button logbutton;

    @FXML
    private void closesesion() throws IOException{
        System.out.println("CLOSE");
        App.setRoot("home");
    }

}
