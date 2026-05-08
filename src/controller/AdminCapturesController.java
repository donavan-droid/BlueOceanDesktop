package controller;

import app.MainApp;
import dao.CaptureDAO;
import model.Capture;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class AdminCapturesController {

    @FXML private TableView<Capture> tableCaptures;
    @FXML private TableColumn<Capture, String> colId, colPecheur,
                                                colEspece, colPoids, colDate;

    private ObservableList<Capture> data =
        FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(c ->
            new SimpleStringProperty(
                String.valueOf(c.getValue().getIdCapture())));
        colPecheur.setCellValueFactory(c ->
            new SimpleStringProperty(
                String.valueOf(c.getValue().getIdPecheur())));
        colEspece.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getTypePoisson()));
        colPoids.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getPoids() + " kg"));
        colDate.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getDateCapture() != null
                    ? c.getValue().getDateCapture().toString() : ""));

        tableCaptures.setItems(data);
        data.addAll(new CaptureDAO().listerToutes());
    }

    @FXML public void retour() {
        try { MainApp.changerScene("dashboard_admin.fxml", 1100, 700); }
        catch (Exception e) { e.printStackTrace(); }
    }
}