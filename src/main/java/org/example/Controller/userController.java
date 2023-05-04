package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.App;
import org.example.DAO.*;

import java.io.IOException;

public class userController {

    @FXML
    private Button logbutton;

    @FXML
    private void closesesion() throws IOException{
        App.setRoot("UsersLogin");
    }

}
