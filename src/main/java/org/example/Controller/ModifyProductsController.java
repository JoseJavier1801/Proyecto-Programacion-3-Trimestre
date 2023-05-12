package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.DAO.AdminDAO;
import org.example.DAO.ProductsDAO;
import org.example.DOMAIN.Admin;
import org.example.DOMAIN.Products;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Clase ModifyProductsCOntroller que incluye los metodos para modificar los productos
 */

public class ModifyProductsController {

    @FXML
    private Label ModP_label;

    @FXML
    private TextField Product;
    @FXML
    private TextField newStock;
    @FXML
    private TextField newPrice;

    /**
     * Metodo saveModify que guarda los nuevos stock y precio  del producto buscado
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    private void saveModify() throws SQLException, IOException {
        String productName = Product.getText().trim();
        String newStockValue = newStock.getText().trim();
        String newPriceValue = newPrice.getText().trim();

        if (productName.isEmpty() || newStockValue.isEmpty() || newPriceValue.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter all fields.");
            alert.showAndWait();
            return;
        }

        int newStock = Integer.parseInt(newStockValue);
        double newPrice = Double.parseDouble(newPriceValue);

        ProductsDAO PDAO = ProductsDAO.getInstance();
        Products product = PDAO.findByName(productName);

        // Verificar si el producto existe
        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Product doesn't exist.");
            alert.showAndWait();
            return;
        }


        // Verificar si el producto pertenece al administrador actual
        AdminDAO ADAO = AdminDAO.getInstance();
        Admin productAdmin = product.getId_admin();
        if (productAdmin.getId() != ADAO.adminId) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You can only modify products that belong to you.");
            alert.showAndWait();
            return;
        }

        // Actualizar el producto
        product.setStock(newStock);
        product.setPrice(newPrice);
        Products savedProduct = PDAO.save(product);

        if (savedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("An error occurred while updating the product.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText("Product updated successfully.");
        alert.showAndWait();
        App.setRoot("admin");
    }

    /**
     * metodo que cancela la modificacion y regresa a la vista de admin
     * @throws IOException
     */
    @FXML
    private void Cancel() throws IOException {
        App.setRoot("admin");
    }


}
