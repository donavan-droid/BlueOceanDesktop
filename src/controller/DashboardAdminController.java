package controller;

import app.MainApp;
import app.Session;
import dao.*;
import model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class DashboardAdminController {

    @FXML private Label lblUsers, lblCaptures,
                        lblBateaux, lblSignalements;

    @FXML
    public void initialize() {
        lblUsers.setText(
            String.valueOf(new UtilisateurDAO().listerTous().size()));
        lblCaptures.setText(
            String.valueOf(new CaptureDAO().listerToutes().size()));
        lblBateaux.setText(
            String.valueOf(new BateauDAO().listerTous().size()));
        lblSignalements.setText(
            String.valueOf(new SignalementDAO().listerTous().size()));
    }

    @FXML public void allerUtilisateurs() {
        try { MainApp.changerScene("admin_utilisateurs.fxml", 1000, 650); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML public void allerCaptures() {
        try { MainApp.changerScene("admin_captures.fxml", 1000, 650); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML public void allerGPS() {
        try { MainApp.changerScene("admin_gps.fxml", 1100, 700); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML public void allerMeteo() {
        try { MainApp.changerScene("meteo.fxml", 900, 600); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML public void allerSignalements() {
        try { MainApp.changerScene("admin_signalements.fxml", 1000, 650); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML public void deconnecter() {
        Session.clear();
        try { MainApp.changerScene("login.fxml", 500, 400); }
        catch (Exception e) { e.printStackTrace(); }
    }
}