package controller;

import app.MainApp;
import app.Session;
import model.Signalement;
import service.SignalementService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class SignalementController {

    @FXML private ComboBox<String> typeCombo;
    @FXML private TextField localisationField;
    @FXML private TextArea descriptionArea;
    @FXML private Label msgLabel;
    @FXML private TableView<Signalement> tableSignalements;
    @FXML private TableColumn<Signalement, String> colType, colLocalisation,
                                                    colDescription, colDate;

    private SignalementService service = new SignalementService();
    private ObservableList<Signalement> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        typeCombo.setItems(FXCollections.observableArrayList(
            "peche", "meteo", "securite", "autre"
        ));
        colType.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getType()));
        colLocalisation.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getLocalisation()));
        colDescription.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getDescription()));
        colDate.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getDateSignalement() != null
                    ? c.getValue().getDateSignalement().toString() : ""));
        tableSignalements.setItems(data);
        charger();
    }

    private void charger() {
        data.clear();
        List<Signalement> liste =
            service.getSignalementsPecheur(Session.getIdUser());
        data.addAll(liste);
    }

    @FXML
    public void envoyerSignalement() {
        String type        = typeCombo.getValue();
        String localisation= localisationField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (type == null || description.isEmpty()) {
            msgLabel.setStyle("-fx-text-fill:#E63946;");
            msgLabel.setText("Type et description obligatoires.");
            return;
        }

        boolean ok = service.signalerProbleme(
            Session.getIdUser(), type, description, localisation);
        if (ok) {
            msgLabel.setStyle("-fx-text-fill:#007A33;");
            msgLabel.setText("Signalement envoye !");
            typeCombo.setValue(null);
            localisationField.clear();
            descriptionArea.clear();
            charger();
        } else {
            msgLabel.setStyle("-fx-text-fill:#E63946;");
            msgLabel.setText("Erreur d'enregistrement.");
        }
    }

    @FXML public void retourDashboard() {
        try { MainApp.changerScene("dashboard_pecheur.fxml", 1100, 700); }
        catch (Exception e) { e.printStackTrace(); }
    }
}