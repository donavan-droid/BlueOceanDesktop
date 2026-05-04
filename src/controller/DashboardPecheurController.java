package controller;

import app.MainApp;
import app.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardPecheurController {

    @FXML private Label bienvenue;

    @FXML
    public void initialize() {
        bienvenue.setText("Bonjour, "
            + Session.getUtilisateur().getUsername() + " !");
    }

    @FXML public void allerCaptures() {
        try { MainApp.changerScene("captures.fxml", 900, 600); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML public void allerBateaux() {
        try { MainApp.changerScene("bateau.fxml", 800, 550); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML public void allerMeteo() {
        try { MainApp.changerScene("meteo.fxml", 900, 600); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML public void allerSignalement() {
        try { MainApp.changerScene("signalement.fxml", 900, 600); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML public void deconnecter() {
        Session.clear();
        try { MainApp.changerScene("login.fxml", 500, 400); }
        catch (Exception e) { e.printStackTrace(); }
    }
}