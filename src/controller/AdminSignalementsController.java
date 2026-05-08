package controller;

import app.MainApp;
import dao.SignalementDAO;
import model.Signalement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdminSignalementsController {

    @FXML private TableView<Signalement> tableSignalements;
    @FXML private TableColumn<Signalement, String> colId, colPecheur,
                                                    colType, colLoc,
                                                    colDesc, colDate;

    private ObservableList<Signalement> data =
        FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(c ->
            new SimpleStringProperty(
                String.valueOf(c.getValue().getIdSignalement())));
        colPecheur.setCellValueFactory(c ->
            new SimpleStringProperty(
                String.valueOf(c.getValue().getIdPecheur())));
        colType.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getType()));
        colLoc.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getLocalisation()));
        colDesc.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getDescription()));
        colDate.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getDateSignalement() != null
                    ? c.getValue().getDateSignalement().toString() : ""));

        tableSignalements.setItems(data);
        data.addAll(new SignalementDAO().listerTous());
    }

    @FXML public void retour() {
        try { MainApp.changerScene("dashboard_admin.fxml", 1100, 700); }
        catch (Exception e) { e.printStackTrace(); }
    }
}