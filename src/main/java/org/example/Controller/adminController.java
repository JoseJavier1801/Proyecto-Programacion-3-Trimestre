package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.App;
import org.example.DAO.*;
import org.example.DOMAIN.Admin;
import org.example.DOMAIN.Products;

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
    private TableView<Products> tableProducts;

    @FXML
    private TableColumn<Products, Integer> colid;
    @FXML
    private TableColumn<Products, String> colN;
    @FXML
    private TableColumn<Products, String> colD;
    @FXML
    private TableColumn<Products, Double> colP;
    @FXML
    private TableColumn<Products, Integer> coliS;
    @FXML
    private TableColumn<Products, Integer> colIDA;

    private Admin admin;

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

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


}
