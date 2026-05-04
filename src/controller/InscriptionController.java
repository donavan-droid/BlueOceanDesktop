package controller;

import app.MainApp;
import service.UtilisateurService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class InscriptionController {

    @FXML private TextField nomField, cinField, telField, usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label msgLabel;

    private UtilisateurService service = new UtilisateurService();

    @FXML
    public void handleInscription() {
        String nom      = nomField.getText().trim();
        String cin      = cinField.getText().trim();
        String tel      = telField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (nom.isEmpty() || cin.isEmpty() || tel.isEmpty()
                || username.isEmpty() || password.isEmpty()) {
            msgLabel.setStyle("-fx-text-fill:#E63946;");
            msgLabel.setText("Veuillez remplir tous les champs.");
            return;
        }
        if (password.length() < 6) {
            msgLabel.setStyle("-fx-text-fill:#E63946;");
            msgLabel.setText("Mot de passe trop court (min. 6 caracteres).");
            return;
        }

        boolean ok = service.inscrire(username, password, nom, cin, tel);
        if (ok) {
            msgLabel.setStyle("-fx-text-fill:#007A33;");
            msgLabel.setText("Inscription reussie ! Redirection...");
            try {
                Thread.sleep(1000);
                MainApp.changerScene("login.fxml", 500, 400);
            } catch (Exception e) { e.printStackTrace(); }
        } else {
            msgLabel.setStyle("-fx-text-fill:#E63946;");
            msgLabel.setText("Erreur : username deja utilise.");
        }
    }

    @FXML
    public void allerLogin() {
        try { MainApp.changerScene("login.fxml", 500, 400); }
        catch (Exception e) { e.printStackTrace(); }
    }
}