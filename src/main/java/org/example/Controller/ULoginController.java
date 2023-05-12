package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.App;
import org.example.DAO.*;
import org.example.DOMAIN.User;
import org.example.UTILS.Encrypt;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;

public class ULoginController {

    @FXML
    private TextField usertxt;
    @FXML
    private PasswordField psswdtxt;

    /**
     * Metodo btnlogin que verifica los datos del usuario introducidos y le permite iniciar sesion
     * @throws IOException
     */

    @FXML
    private void btnLogin() throws IOException {
        String username = usertxt.getText();
        String password = psswdtxt.getText();

        UserDAO UDAO = UserDAO.getInstance();
        try {
            User u = UDAO.findByName(username);
            if (u != null && u.getPassword().equals(Encrypt.EncryptPassword(password))) {
                //  guardar los demas datos del usuario logueado para su futuro uso en otros metodos
                UDAO.userId = u.getId();
                UDAO.userDNI=u.getDNI();
                UDAO.userMail=u.getEmail();
                // Usuario autenticado, navegar a la vista de admin
                App.setRoot("users");
            } else {
                // Usuario o contraseña incorrectos, mostrar alerta de error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Log-in Error");
                alert.setHeaderText(null);
                alert.setContentText("Incorrect User or Password.");
                alert.showAndWait();
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            // Error al buscar en la base de datos, mostrar alerta de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Couldn't find user in the database.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * metodo btndelete que recoge el nombre del usuario a eliminar
     * @throws IOException
     */
    @FXML
    private void btnDelete() throws IOException{
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete User");
        dialog.setHeaderText("Enter the name of the User you want to delete:");
        dialog.setContentText("Username:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String userName = result.get();

            UserDAO UDAO = UserDAO.getInstance();
            try {
                User u = UDAO.findByName(userName);
                if (u != null) {
                    UDAO.delete(u);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("User Deleted");
                    alert.setHeaderText(null);
                    alert.setContentText("User " + userName + " has been deleted successfully!");
                    alert.showAndWait();
                } else {
                    showError("User " + userName + " doesn't exist.");
                }
            } catch (SQLException e) {
                showError("Error occurred while deleting User " + userName+ ".");
                e.printStackTrace();
            }
        }
    }

    private void showError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Log-in Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void goBack() throws IOException {
        App.setRoot("home");
    }
}
