package org.example.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.DAO.CartDAO;
import org.example.DAO.ProductsDAO;
import org.example.DAO.PurchasesDAO;
import org.example.DAO.UserDAO;
import org.example.DOMAIN.Products;
import org.example.DOMAIN.cart;
import org.example.DOMAIN.purchases;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase showCartController que se encargar de mostrar los productos que va a comprar el usuario y puede
 * realizar la comprar o vaciar el carrito
 */
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
    private TableColumn<cart, Integer> QuantityColumn;
    @FXML
    private TableColumn<cart, Double> PriceColumn;

    private ObservableList<cart> CartList = FXCollections.observableArrayList();


    public void initialize() {
        try {
            id_userColumn.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            id_productColumn.setCellValueFactory(new PropertyValueFactory<>("id_product")); // Corregir esta línea
            DateColumn.setCellValueFactory(new PropertyValueFactory<>("buyDate"));
            QuantityColumn.setCellValueFactory(new PropertyValueFactory<>("cant"));
            PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            CartDAO CDAO = CartDAO.getInstance();
            CartList.addAll(CDAO.findAll());

            tableCart.setItems(CartList);

            tableCart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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

    /**
     * metodo buy que realiza la compra de productos
     * suma el total de los precios y el stock
     */
    @FXML
    private void BUY() {
        double totalPrice = 0;
        List<cart> cartItems = new ArrayList<>(CartList); // Copiar los elementos del carrito
        for (cart c : cartItems) {
            int quantity = c.getCant();
            double unitPrice = c.getPrice();
            totalPrice += (quantity * unitPrice);
        }

        // Mostrar un mensaje de confirmación al usuario
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm purchase");
        alert.setHeaderText("Do you want to confirm the purchase?");
        alert.setContentText("Total price: $" + totalPrice);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                PurchasesDAO purchasesDAO = PurchasesDAO.getInstance();

                // Guardar los productos del carrito en la tabla "purchases"
                for (cart cartItem : cartItems) {
                    purchases purchase = new purchases();
                    purchase.setId_user(UserDAO.getInstance().findById(String.valueOf(UserDAO.getUserId())));
                    purchase.setId_product(cartItem.getId_product());
                    purchase.setBuyDate(cartItem.getBuyDate());
                    purchase.setCant(cartItem.getCant());
                    purchase.setPrice(cartItem.getPrice());

                    purchasesDAO.save(purchase);
                }

                CartDAO CDAO = CartDAO.getInstance();
                CDAO.deleteAll();
                CartList.clear();

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Purchase confirmed");
                alert2.setHeaderText(null);
                alert2.setContentText("Thank you for purchasing on The Tech Alley.");
                alert2.showAndWait();
            } catch (SQLException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Database error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("There was an error accessing the database");
                errorAlert.showAndWait();
            }
        }
    }
    /**
     * metodo tookout que elimina el producto seleccionado y regresa la cantidad añadida a la talba productos
     */

    @FXML
    private void tookout() {

        cart selectedCart = tableCart.getSelectionModel().getSelectedItem();
        if (selectedCart != null) {
            try {
                Products selectedProduct = selectedCart.getId_product();

                int currentStock = selectedProduct.getStock();
                int quantityToRemove = selectedCart.getCant();
                selectedProduct.setStock(currentStock + quantityToRemove);
                ProductsDAO PDAO =ProductsDAO.getInstance();
                PDAO.UpdateStock(selectedProduct);

                CartDAO CDAO = CartDAO.getInstance();
                CDAO.delete(selectedCart);
                CartList.remove(selectedCart);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Product removed from cart successfully.");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Database error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("There was an error accessing the database.");
                errorAlert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product from the cart.");
            alert.showAndWait();
        }
    }
    @FXML
    private void goBack() throws IOException {
        App.setRoot("users");
    }
}
