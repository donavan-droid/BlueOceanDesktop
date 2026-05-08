package controller;

import app.MainApp;
import app.Session;
import dao.AlerteDAO;
import model.Alerte;
import service.MeteoService;
import com.google.gson.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class MeteoController {

    @FXML private Label villeLabel, tempLabel, descLabel,
                        humiditeLabel, ventLabel;
    @FXML private TableView<Alerte> tableAlertes;
    @FXML private TableColumn<Alerte, String> colType, colNiveau,
                                                    colMessage, colDate;

    private MeteoService meteoService = new MeteoService();
    private ObservableList<Alerte> data =
        FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colType.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getType()));
        colNiveau.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getNiveau()));
        colMessage.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getMessage()));
        colDate.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getDateAlerte() != null
                    ? c.getValue().getDateAlerte().toString() : ""));
        tableAlertes.setItems(data);
        chargerMeteo();
        chargerAlertes();
    }

    private void chargerMeteo() {
        try {
            String json = meteoService.getMeteoJSON(-18.9137, 47.5361);
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
            if (obj.has("main")) {
                villeLabel.setText("📍 " +
                    obj.get("name").getAsString() + ", Madagascar");
                tempLabel.setText(Math.round(
                    obj.getAsJsonObject("main")
                       .get("temp").getAsDouble()) + "°C");
                descLabel.setText(
                    obj.getAsJsonArray("weather").get(0)
                       .getAsJsonObject()
                       .get("description").getAsString());
                humiditeLabel.setText("💧 Humidité: " +
                    obj.getAsJsonObject("main")
                       .get("humidity").getAsInt() + "%");
                ventLabel.setText("🌬 Vent: " +
                    Math.round(obj.getAsJsonObject("wind")
                        .get("speed").getAsDouble() * 3.6) + " km/h");
                meteoService.verifierEtCreerAlerte(json);
            } else {
                villeLabel.setText("⚠️ Données météo indisponibles");
                tempLabel.setText("—");
            }
        } catch (Exception e) {
            villeLabel.setText("❌ Erreur API météo");
            e.printStackTrace();
        }
    }

    private void chargerAlertes() {
        data.clear();
        List<Alerte> alertes = new AlerteDAO().listerToutes();
        data.addAll(alertes);
    }

    // ← Navigation intelligente selon le rôle
    @FXML
    public void retourDashboard() {
        try {
            if (Session.estAdmin()) {
                MainApp.changerScene("dashboard_admin.fxml", 1100, 700);
            } else {
                MainApp.changerScene("dashboard_pecheur.fxml", 1100, 700);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}