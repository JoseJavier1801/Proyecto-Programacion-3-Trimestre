package org.example.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.DAO.*;
import org.example.DOMAIN.Admin;
import org.example.DOMAIN.Products;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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

    @FXML
    private void initialize() throws SQLException {
        // Obtener los productos del DAO correspondiente
        ProductsDAO PDAO=ProductsDAO.getInstance();
        List<Products> productList = PDAO.findByAdminId(admin.getId_admin());

        // Asignar los productos a la TableView
        ObservableList<Products> productsObservableList = FXCollections.observableArrayList(productList);
        tableProducts.setItems(productsObservableList);

        // Asignar los valores de cada propiedad de Producto a las columnas correspondientes
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colN.setCellValueFactory(new PropertyValueFactory<>("name"));
        colD.setCellValueFactory(new PropertyValueFactory<>("description"));
        colP.setCellValueFactory(new PropertyValueFactory<>("price"));
        coliS.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        colIDA.setCellValueFactory(new PropertyValueFactory<>("adminId"));
    }


}
