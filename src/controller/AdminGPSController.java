package controller;

import app.MainApp;
import dao.BateauDAO;
import dao.PositionGPSDAO;
import model.Bateau;
import model.PositionGPS;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class AdminGPSController {

    @FXML private TableView<Bateau> tableBateaux;
    @FXML private TableColumn<Bateau, String> colId, colType, colLat,
                                               colLng, colDate, colStatut;

    private ObservableList<Bateau> data =
        FXCollections.observableArrayList();
    private PositionGPSDAO posDAO = new PositionGPSDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(c ->
            new SimpleStringProperty(
                String.valueOf(c.getValue().getIdBateau())));
        colType.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getTypeBateau()));

        colLat.setCellValueFactory(c -> {
            PositionGPS pos =
                posDAO.getDernierePosition(c.getValue().getIdBateau());
            return new SimpleStringProperty(
                pos != null
                    ? String.valueOf(pos.getLatitude()) : "—");
        });
        colLng.setCellValueFactory(c -> {
            PositionGPS pos =
                posDAO.getDernierePosition(c.getValue().getIdBateau());
            return new SimpleStringProperty(
                pos != null
                    ? String.valueOf(pos.getLongitude()) : "—");
        });
        colDate.setCellValueFactory(c -> {
            PositionGPS pos =
                posDAO.getDernierePosition(c.getValue().getIdBateau());
            return new SimpleStringProperty(
                pos != null
                    ? pos.getDatePosition().toString() : "—");
        });
        colStatut.setCellValueFactory(c -> {
            PositionGPS pos =
                posDAO.getDernierePosition(c.getValue().getIdBateau());
            return new SimpleStringProperty(
                pos != null ? "🟢 En mer" : "⚫ Inconnu");
        });

        tableBateaux.setItems(data);
        data.addAll(new BateauDAO().listerTous());
    }

    @FXML public void retour() {
        try { MainApp.changerScene("dashboard_admin.fxml", 1100, 700); }
        catch (Exception e) { e.printStackTrace(); }
    }
}