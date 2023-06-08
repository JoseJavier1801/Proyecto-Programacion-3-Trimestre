package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.DAO.AdminDAO;
import org.example.DAO.ProductsDAO;
import org.example.DOMAIN.Admin;
import org.example.DOMAIN.Products;
import org.example.UTILS.ValidationDATA;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Clase AddproductsController encargadar de la vista addproducts que llama el administrador
 * incluye el metodo para añadir productos a la base de datos
 */

public class AddproductsController {

    @FXML
    private TextField nameP;

    @FXML
    private TextField descP;

    @FXML
    private TextField Stock;

    @FXML
    private TextField Price;

    @FXML
    private void insert() throws IOException {
        String name = nameP.getText();
        String description = descP.getText();
        String stockStr = Stock.getText();
        String priceStr = Price.getText();

        // Validar que el stock sea un número entero
        if (!ValidationDATA.isValidInt(stockStr)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Stock Error");
            alert.setHeaderText(null);
            alert.setContentText("Stock should be a integer");
            alert.showAndWait();
            return;
        }
        int stock = Integer.parseInt(stockStr);

        // Validar que el precio sea un número decimal
        if (!ValidationDATA.isValidDouble(priceStr)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Price Error");
            alert.setHeaderText(null);
            alert.setContentText("Product price should be decimal");
            alert.showAndWait();
            return;
        }
        double price = Double.parseDouble(priceStr);

        ProductsDAO PDAO = ProductsDAO.getInstance();
        try {
            Admin a = new Admin();
            a.setId(AdminDAO.getAdminId()); // Obtener el ID del admin logueado
            Products p = new Products(0, name, description, stock, price, a);
            PDAO.save(p);

            // Mostrar alerta de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ADD Product");
            alert.setHeaderText(null);
            alert.setContentText("Product Added Succesfully");
            alert.showAndWait();
            // Navegar a la vista de admin
            App.setRoot("admin");
        } catch (SQLException e) {
            // Error al insertar en la base de datos, mostrar alerta de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DataBase Error");
            alert.setHeaderText(null);
            alert.setContentText("Product cant be added pn the database");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void cancel() throws IOException {
        App.setRoot("admin");
    }
}