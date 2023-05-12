package org.example.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.DAO.CartDAO;
import org.example.DOMAIN.cart;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class ShowCartController {

    @FXML
    private TableView<cart> tableCart;

    @FXML
    private TableColumn<cart, Integer> id_userColumn;
    @FXML
    private TableColumn<cart, Integer> id_productColumn;
    @FXML
    private TableColumn<cart, Date> DateColumn;
    @FXML
    private TableColumn<cart, String> NameColumn;
    @FXML
    private TableColumn<cart, Integer> QuantityColumn;
    @FXML
    private TableColumn<cart, Double> PriceColumn;

    private ObservableList<cart> CartList = FXCollections.observableArrayList();

    public void initialize() {
        try {
            // Inicializar las columnas
            id_userColumn.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            id_productColumn.setCellValueFactory(new PropertyValueFactory<>("id_product"));
            DateColumn.setCellValueFactory(new PropertyValueFactory<>("buyDate"));
            NameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
            QuantityColumn.setCellValueFactory(new PropertyValueFactory<>("cant"));
            PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            // Obtiene los productos de la base de datos y los agrega a la lista
            CartDAO CDAO= CartDAO.getInstance();
            CartList.addAll(CDAO.findAll());

            // Establece la lista de productos
            tableCart.setItems(CartList);

        } catch (SQLException e) {
            // Manejo de excepciones
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database error");
            alert.setHeaderText(null);
            alert.setContentText("There was an error accessing the database");
            alert.showAndWait();
        }
    }

    @FXML
    private void BUY(){

    }

    @FXML
    private void emptyCart(){

    }
    @FXML
    private void goBack() throws IOException {
        App.setRoot("users");
    }






}
