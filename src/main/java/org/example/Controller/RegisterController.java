package org.example.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.App;
import org.example.DAO.AdminDAO;
import org.example.DAO.UserDAO;
import org.example.DOMAIN.Admin;
import org.example.DOMAIN.User;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    public CheckBox foradmin;

    @FXML
    private Button btn_main;

    @FXML
    private Button btn_reg;


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

    @FXML
    private void register() throws IOException, SQLException {
        // llamando a los DAO
        UserDAO UDAO = UserDAO.getInstance();
        AdminDAO ADAO = AdminDAO.getInstance();

        //obtener datos de los textbox y checkbox
        String user = username.getText();
        String password = passwd.getText();
        String email = mail.getText();
        String dni = DNI.getText();
        boolean isAdmin = foradmin.isSelected();


        if (isAdmin) {
            Admin a = new Admin(0, user, password, dni, email);
            Admin result = ADAO.save(a);

            // Mostrar mensaje
            if (result != null) {
                showmesaje("Admin created successfully");
            } else {
                showmesaje("Admin Already exist");
            }
        } else {
            User u = new User(0, user, password, email, dni);
            User result = UDAO.save(u);

            // Mostrar mensaje
            if (result !=null) {
                showmesaje("User created successfully.");
            } else {
                showmesaje("User Already exist");
            }
        }
        App.setRoot("home");
    }
    private void showmesaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mesage");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}