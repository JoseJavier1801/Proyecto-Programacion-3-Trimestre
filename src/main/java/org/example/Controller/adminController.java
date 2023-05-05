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
    private void viewproducts() {
        try {
            // Obtener los productos del administrador actualmente logueado
            ProductsDAO PDAO = ProductsDAO.getInstance();
            List<Products> products = PDAO.findByAdminId(ALoginController.adminId);

            // Asignar los datos a las columnas
            colid.setCellValueFactory(new PropertyValueFactory<>("id_product"));
            colN.setCellValueFactory(new PropertyValueFactory<>("name"));
            colD.setCellValueFactory(new PropertyValueFactory<>("description"));
            colP.setCellValueFactory(new PropertyValueFactory<>("price"));
            coliS.setCellValueFactory(new PropertyValueFactory<>("stock"));
            colIDA.setCellValueFactory(new PropertyValueFactory<>("admin.id_admin"));

            // Cargar los productos en la tabla
            ObservableList<Products> list = FXCollections.observableArrayList(products);
            tableProducts.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
