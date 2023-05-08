package org.example.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.example.App;
import org.example.DAO.*;
import org.example.DOMAIN.Admin;
import org.example.DOMAIN.Products;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Controlador de la vista de administrador que puede añadir,modificar,buscar productos
 */
public class adminController {

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

    /**
     * ObservableList que crea un FXcollection para mostrar un arraylist en la tabla del FXML
     */
    private ObservableList<Products> productsList = FXCollections.observableArrayList();

    private Admin admin;

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public void initialize() throws SQLException {
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
    }

    @FXML
    private void modifyAdmin() throws SQLException, IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modify Admin");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter new username and password:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            if (username.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Username and password cannot be empty.");
                alert.showAndWait();
                return;
            }

            // Obtener el Admin actual
            AdminDAO ADAO = AdminDAO.getInstance();
            int adminId = ADAO.adminId;
            String email=ADAO.adminMail;
            String DNI=ADAO.adminDNI;

            // Se crea un nuevo admin utilizando los datos nuevos y los datos que no se modifican
            Admin newAdmin = new Admin(adminId, username, password, email, DNI);

            // guardar los datos nuevos
            Admin savedAdmin = ADAO.save(newAdmin);

            if (savedAdmin == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("An admin with the same username or email already exists.");
                alert.showAndWait();
                return;
            }

            admin = savedAdmin;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Admin has been updated, The session will close.");
            alert.showAndWait();
            App.setRoot("adminLogin");
        }
    }

    /**
     * Metodo Delete que al seleccionar el boton muestr una ventana que pide el nombre del producto a eliminar
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    private void delete() throws IOException, SQLException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Products");
        dialog.setHeaderText(null);
        dialog.setContentText("Instert the product name to delete:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String productName = result.get();
            if (productName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Product name no added");
                alert.showAndWait();
                return;
            }

            ProductsDAO PDAO = ProductsDAO.getInstance();
            Products product = PDAO.findByName(productName);
            if (product == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Product doesn't exist");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Are you sure you want to delete this product?");
                Optional<ButtonType> resultAlert = alert.showAndWait();
                if (resultAlert.isPresent() && resultAlert.get() == ButtonType.OK) {
                    PDAO.delete(product);
                    tableProducts.setItems(productsList);
                }
            }
        }
    }

    @FXML
    private void closesesion() throws IOException {
        App.setRoot("adminLogin");
    }

    @FXML
    private void add() throws IOException {
        App.setRoot("addproducts");
    }

    @FXML
    private void search() throws IOException {

    }

    @FXML
    private void modify() throws IOException, SQLException {

    }
}
