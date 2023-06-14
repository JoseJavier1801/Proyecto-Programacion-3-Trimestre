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
import org.example.DAO.PurchasesDAO;
import org.example.DAO.UserDAO;
import org.example.DOMAIN.Products;
import org.example.DOMAIN.cart;
import org.example.DOMAIN.purchases;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class PurchasesController {
    @FXML
    private TableView<purchases> tablePurchases;

    @FXML
    private TableColumn<purchases, Integer> id_userColumn;
    @FXML
    private TableColumn<purchases, Integer> id_productColumn;
    @FXML
    private TableColumn<purchases, Date> DateColumn;
    @FXML
    private TableColumn<purchases, Integer> QuantityColumn;
    @FXML
    private TableColumn<purchases, Double> PriceColumn;

    private ObservableList<purchases> PurchasesList = FXCollections.observableArrayList();

    public void initialize() {
        try {
            id_userColumn.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            id_productColumn.setCellValueFactory(new PropertyValueFactory<>("id_product"));
            DateColumn.setCellValueFactory(new PropertyValueFactory<>("buyDate"));
            QuantityColumn.setCellValueFactory(new PropertyValueFactory<>("cant"));
            PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            PurchasesDAO PUDAO = PurchasesDAO.getInstance();
            int userId = UserDAO.getUserId(); // Get the logged-in user's ID
            PurchasesList.addAll(PUDAO.findByUserId(userId)); // Fetch purchases for the logged-in user

            tablePurchases.setItems(PurchasesList);

            tablePurchases.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    Products selectedProduct = newValue.getId_product();
                    System.out.println("Selected Product: " + selectedProduct.getName());
                }
            });
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database error");
            alert.setHeaderText(null);
            alert.setContentText("There was an error accessing the database");
            alert.showAndWait();
        }
    }

    @FXML
    private void goBack() throws IOException {
        App.setRoot("users");
    }
}
