package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.App;
import org.example.DAO.*;

import java.io.IOException;

public class adminController {
    @FXML
    private Button logbutton;
    @FXML
    private Button addproducts;
    @FXML
    private Button searchP;
    @FXML
    private Button modP;
    @FXML
    private Button delP;
    @FXML
    private Button showP;
    @FXML
    private Button ManageU;

    @FXML
    private void closesesion()throws IOException {
        App.setRoot("adminLogin");
    }
   @FXML
    private void add()throws IOException {
        App.setRoot("addproducts");
    }
    @FXML
    private void search()throws IOException {

    }
    @FXML
    private void modify()throws IOException {

    }
    @FXML
    private void delete()throws IOException {

    }
    @FXML
    private void showAll()throws IOException {

    }
    @FXML
    private void ManageUsers()throws IOException {

    }
}
