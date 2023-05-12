package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.App;
import org.example.DAO.AdminDAO;
import org.example.DAO.UserDAO;
import org.example.DOMAIN.Admin;
import org.example.DOMAIN.User;
import org.example.UTILS.Encrypt;
import org.example.UTILS.ValidationDATA;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    public CheckBox foradmin;


    @FXML
    private TextField username;

    @FXML
    private PasswordField passwd;

    @FXML
    private TextField mail;

    @FXML
    private TextField DNI;

    @FXML
    private void goBack() throws IOException {
        App.setRoot("home");
    }

    /**
     * Metodo register que crea usuario/administrador con los datos que se introducen en los textlabel/passwordlabel
     */
    @FXML
    private void register() {
        // Llamando a los DAO
        UserDAO UDAO = UserDAO.getInstance();
        AdminDAO ADAO = AdminDAO.getInstance();

        // Obtener datos de los textbox y checkbox
        String user = username.getText();
        String password = passwd.getText();
        String email = mail.getText();
        String dni = DNI.getText();
        boolean isAdmin = foradmin.isSelected();

        // Validar datos
        if (!ValidationDATA.isValidUsername(user)) {
            showmesaje("Invalid username. Must be at least 4 characters long and contain only letters and numbers.");
            return;
        }
        if (!ValidationDATA.isValidPassword(password)) {
            showmesaje("Invalid password. Must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number.");
            return;
        }
        if (!ValidationDATA.isValidEmail(email)) {
            showmesaje("Invalid email address.");
            return;
        }
        if (!ValidationDATA.isValidDNI(dni)) {
            showmesaje("Invalid DNI. Must be 8 numbers and a letter.");
            return;
        }

        try {
            // Encriptar la contraseña
            String hashedPassword = Encrypt.EncryptPassword(password);

            if (isAdmin) {
                Admin a = new Admin(0, user, hashedPassword, dni, email);
                Admin result = ADAO.save(a);

                // Mostrar mensaje
                if (result != null) {
                    showmesaje("Admin created successfully");
                } else {
                    showmesaje("Admin Already exist");
                }
            } else {
                User u = new User(0, user, hashedPassword, email, dni);
                User result = UDAO.save(u);

                // Mostrar mensaje
                if (result !=null) {
                    showmesaje("User created successfully.");
                } else {
                    showmesaje("User Already exist");
                }
            }
            App.setRoot("home");
        } catch (NoSuchAlgorithmException | IOException e) {
            // Manejo de excepciones
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            showmesaje("Error occurred while encrypting password.");
        } catch (SQLException e) {
            showmesaje("Cant create User or Admin");
            e.printStackTrace();
        }
    }
    private void showmesaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mesage");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}