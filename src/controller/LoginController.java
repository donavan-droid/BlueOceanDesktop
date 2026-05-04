package controller;

import app.MainApp;
import app.Session;
import dao.UtilisateurDAO;
import model.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label erreurLabel;

    @FXML
    public void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            erreurLabel.setText("Veuillez remplir tous les champs.");
            return;
        }

        Utilisateur u = new UtilisateurDAO().login(username, password);
        if (u != null) {
            Session.setUtilisateur(u);
            try {
                if ("admin".equals(u.getRole())) {
                    MainApp.changerScene("dashboard_admin.fxml", 1100, 700);
                } else {
                    MainApp.changerScene("dashboard_pecheur.fxml", 1100, 700);
                }
            } catch (Exception e) {
                erreurLabel.setText("Erreur de navigation.");
                e.printStackTrace();
            }
        } else {
            erreurLabel.setText("Identifiants incorrects.");
        }
    }

    @FXML
    public void allerInscription() {
        try {
            MainApp.changerScene("inscription.fxml", 500, 500);
        } catch (Exception e) { e.printStackTrace(); }
    }
}