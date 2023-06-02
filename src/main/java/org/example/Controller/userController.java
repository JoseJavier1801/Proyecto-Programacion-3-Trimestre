package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.example.App;
import org.example.DAO.*;
import org.example.DOMAIN.User;
import org.example.UTILS.Encrypt;
import org.example.UTILS.ValidationDATA;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Clase userController que se encargar de la vista de usuario y permite al usuario acceder a
 * comprar productos, mostrar el carrito, modificar sus datos y cerrar su sesion
 */
public  class userController {
    private User user;

   @FXML
   private Label ulabel;

    @FXML
    private Button logbutton;

    public void initialize() {
        try {

            // Obtén el ID del administrador utilizando AdminDAO
            int userId = UserDAO.getInstance().getUserId();

            // Obtén el nombre del administrador utilizando el ID
            String UserName = UserDAO.getUserNameById(userId);

            // Establece el nombre del administrador en el Label
            ulabel.setText("Welcome "+UserName);

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
     * metodo que modifica el usuario y contraseña del usuario actual
     * @throws IOException
     */
    @FXML
    private void modifyUser() throws IOException{
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modify User");
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

            // Validar el nuevo usuario
            if (!ValidationDATA.isValidUsername(username)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("The user is invalid. Please enter a valid username.");
                alert.showAndWait();
                return;
            }

            // Validar la contraseña
            if (!ValidationDATA.isValidPassword(password)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("The password is invalid. Please enter a valid password.");
                alert.showAndWait();
                return;
            }

            try {
                // Obtener el usuario actual, junto a datos almacenados del login
                UserDAO UDAO = UserDAO.getInstance();
                int Id = UDAO.userId;
                String email = UDAO.userMail;
                String DNI = UDAO.userDNI;

                // Encriptar la contraseña
                String hashedPassword = Encrypt.EncryptPassword(password);

                // Se crea un nuevo user utilizando los datos nuevos y los datos que no se modifican
                User newUser = new User(Id, username, hashedPassword, email, DNI);

                // guardar los nuevos datos
                User savedUser = UDAO.Update(newUser);

                if (savedUser == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("An user with the same username or password already exists.");
                    alert.showAndWait();
                    return;
                }

                user = savedUser;

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("User has been updated, The session will close.");
                alert.showAndWait();
                App.setRoot("UsersLogin");
            } catch (SQLException | IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("An error occurred while updating the user: " + e.getMessage());
                alert.showAndWait();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    private void showCart() throws IOException{
        App.setRoot("ShowCart");
    }

    /**
     * metodo que llama a la vista addcart
     * @throws IOException
     */
    @FXML
    private void addCart() throws IOException{
        App.setRoot("addCart");
    }

    /**
     * metodo que cierra la sesion
     * @throws IOException
     */
    @FXML
    private void closesesion() throws IOException{
        App.setRoot("UsersLogin");
    }

}
