package edu.umb.uaspbo.controller;

import edu.umb.uaspbo.MainApp;
import edu.umb.uaspbo.dao.UserDAO;
import edu.umb.uaspbo.util.DialogUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class LoginController {

    private final UserDAO userDAO;

    public LoginController() {
        userDAO = new UserDAO();
    }

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txPassword;

    @FXML
    private TextField txUsername;

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnLogin) {
            String username = txUsername.getText();
            String password = txPassword.getText();

            if (userDAO.login(username, password)) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("dashboard.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene dashboardScene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(dashboardScene);
                    stage.show();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } else {
                DialogUtil.showError("Login Gagal");
            }
        }
    }

}
