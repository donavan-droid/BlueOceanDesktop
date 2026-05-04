package controller;

import app.MainApp;
import app.Session;
import dao.CaptureDAO;
import model.Capture;
import service.CaptureService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class CaptureController {

    @FXML private TextField especeField, poidsField;
    @FXML private Label msgLabel;
    @FXML private TableView<Capture> tableCaptures;
    @FXML private TableColumn<Capture, String> colEspece, colPoids, colDate;

    private CaptureService service = new CaptureService();
    private ObservableList<Capture> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colEspece.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getTypePoisson()));
        colPoids.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getPoids() + " kg"));
        colDate.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getDateCapture() != null
                    ? c.getValue().getDateCapture().toString() : ""));
        tableCaptures.setItems(data);
        charger();
    }

    private void charger() {
        data.clear();
        List<Capture> liste =
            service.getCapturesPecheur(Session.getIdUser());
        data.addAll(liste);
    }

    @FXML
    public void ajouterCapture() {
        String espece = especeField.getText().trim();
        String poidsStr = poidsField.getText().trim();

        if (espece.isEmpty() || poidsStr.isEmpty()) {
            msgLabel.setStyle("-fx-text-fill:#E63946;");
            msgLabel.setText("Remplissez tous les champs.");
            return;
        }
        try {
            double poids = Double.parseDouble(poidsStr);
            boolean ok = service.enregistrerCapture(
                Session.getIdUser(), espece, poids);
            if (ok) {
                msgLabel.setStyle("-fx-text-fill:#007A33;");
                msgLabel.setText("Capture enregistree !");
                especeField.clear();
                poidsField.clear();
                charger();
            }
        } catch (NumberFormatException e) {
            msgLabel.setStyle("-fx-text-fill:#E63946;");
            msgLabel.setText("Poids invalide.");
        }
    }

    @FXML public void retourDashboard() {
        try { MainApp.changerScene("dashboard_pecheur.fxml", 1100, 700); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML public void deconnecter() {
        Session.clear();
        try { MainApp.changerScene("login.fxml", 500, 400); }
        catch (Exception e) { e.printStackTrace(); }
    }
}