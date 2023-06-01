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
import org.example.DOMAIN.cart;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
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
            // Inicializar las columnas
            id_userColumn.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            id_productColumn.setCellValueFactory(new PropertyValueFactory<>("id_product"));
            DateColumn.setCellValueFactory(new PropertyValueFactory<>("buyDate"));
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

    /**
     * metodo buy que realiza la compra de productos
     * suma el total de los precios y el stock
     */
    @FXML
    private void BUY() {
        double totalPrice = 0;
        for (cart c : CartList) {
            int quantity = c.getCant();
            double unitPrice = c.getPrice();
            totalPrice += (quantity * unitPrice);
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm purchase");
        alert.setHeaderText("Do you want to confirm the purchase?");
        alert.setContentText("Total price: $" + totalPrice);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                // Vaciar la tabla de carrito
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
     * metodo emptyCart que vacia el carrito
     */

    @FXML
    private void emptyCart(){
        // Crea una ventana de confirmación para verificar si el usuario quiere vaciar el carrito
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Empty Cart");
        alert.setHeaderText("Are you sure you want to empty the cart?");

        // Muestra la ventana y espera la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Elimina todos los productos del carrito en la base de datos
                CartDAO cartDAO = CartDAO.getInstance();
                cartDAO.deleteAll();


                // Vacía la lista de productos en el carrito
                CartList.clear();

                // Actualiza la tabla
                tableCart.refresh();
            } catch (SQLException e) {
                // Manejo de excepciones
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Database error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("There was an error accessing the database");
                errorAlert.showAndWait();
            }
        }
    }
    @FXML
    private void goBack() throws IOException {
        App.setRoot("users");
    }
}
