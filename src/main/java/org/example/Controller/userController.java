package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.App;
import org.example.DAO.*;
import org.example.DOMAIN.Admin;
import org.example.DOMAIN.User;
import org.example.UTILS.ValidationDATA;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class userController {
    private User user;

    public void setAUser(User user) {
        this.user = user;
    }

    @FXML
    private Button logbutton;

    @FXML
    private void closesesion() throws IOException{
        App.setRoot("UsersLogin");
    }

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

                // Se crea un nuevo user utilizando los datos nuevos y los datos que no se modifican
                User newUser = new User(Id, username, password, email, DNI);

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
            }
        }
    }
    @FXML
    private void showCart() throws IOException{

    }
    @FXML
    private void addCart() throws IOException{
        App.setRoot("addCart");
    }


}
