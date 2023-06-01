package org.example.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.DAO.CartDAO;
import org.example.DAO.ProductsDAO;
import org.example.DAO.UserDAO;
import org.example.DOMAIN.Products;
import org.example.DOMAIN.User;
import org.example.DOMAIN.cart;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import static java.lang.Integer.parseInt;

/**
 * Clase AddCartController encargada de la vista Addcart
 * que es donde el usuario introduce los productos en el carrito de la compra
 */

public class AddCartController {

    @FXML
    private TableView<Products> tableProducts;
    @FXML
    private TableColumn<Products, Integer> idColumn;
    @FXML
    private TableColumn<Products, String> nameColumn;
    @FXML
    private TableColumn<Products, String> descriptionColumn;
    @FXML
    private TableColumn<Products, Integer> stockColumn;
    @FXML
    private TableColumn<Products, Double> priceColumn;
    @FXML
    private TextField TextProduct;
    @FXML
    private TextField TextQuantity;

    /**
     * Metodo initialize que inicializa la tabla de javafx y muestra los productos existentes
     */

    private ObservableList<Products> productsList = FXCollections.observableArrayList();

    public void initialize() {
        try {
            // Inicializa las columnas
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            // Obtiene los productos de la base de datos y los agrega a la lista
            ProductsDAO PDAO = ProductsDAO.getInstance();
            productsList.addAll(PDAO.findAll());

            // Establece la lista de productos
            tableProducts.setItems(productsList);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database error");
            alert.setHeaderText(null);
            alert.setContentText("There was an error accessing the database");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * Metodo addCart que agrega un producto al carrito
     * pasando el nombre del producto a comprar y la cantidad de ese producto
     */

    @FXML
    private void addCart() {
        UserDAO UDAO = UserDAO.getInstance();
        ProductsDAO PDAO = ProductsDAO.getInstance();
        CartDAO CDAO = CartDAO.getInstance();

        String name = TextProduct.getText();
        int quantity = Integer.parseInt(TextQuantity.getText());
        User u = new User();
        u.setId(UDAO.getUserId());

        try {
            // Buscar el producto en la base de datos
            Products p;
            p = PDAO.findByName(name);
            p.setId(PDAO.findId(name));

            if (p != null && p.getStock() >= quantity) {
                int productId = p.getId();
                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                // Crear carrito
                cart myCart = new cart(u, p, sqlDate, quantity, p.getPrice());

                CDAO.save(myCart);

                p.setStock(p.getStock() - quantity);
                PDAO.UpdateStock(p);

                // producto añadido correctamente
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Product added to cart!");
                alert.showAndWait();
                tableProducts.setItems(productsList);

            } else {
                // Mensaje de error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Product not found or not enough quantity available!");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("There was an error");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

        @FXML
    private void goBack() throws IOException {
        App.setRoot("users");
    }
}
