package controller;

import app.MainApp;
import app.Session;
import dao.BateauDAO;
import model.Bateau;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class BateauController {

    @FXML private ComboBox<String> typeCombo;
    @FXML private Label msgLabel;
    @FXML private TableView<Bateau> tableBateaux;
    @FXML private TableColumn<Bateau, String> colId, colType, colAction;

    private BateauDAO dao = new BateauDAO();
    private ObservableList<Bateau> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        typeCombo.setItems(FXCollections.observableArrayList(
            "Pirogue", "Bateau moteur", "Chalutier", "Voilier", "Autre"
        ));
        colId.setCellValueFactory(c ->
            new SimpleStringProperty(
                String.valueOf(c.getValue().getIdBateau())));
        colType.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getTypeBateau()));

        // Colonne bouton Supprimer
        colAction.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Supprimer");
            {
                btn.getStyleClass().add("btn-danger");
                btn.setOnAction(e -> {
                    Bateau b = getTableView().getItems().get(getIndex());
                    boolean ok = dao.supprimer(b.getIdBateau());
                    if (ok) charger();
                });
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        tableBateaux.setItems(data);
        charger();
    }

    private void charger() {
        data.clear();
        List<Bateau> liste = dao.listerParPecheur(Session.getIdUser());
        data.addAll(liste);
    }

    @FXML
    public void ajouterBateau() {
        String type = typeCombo.getValue();
        if (type == null) {
            msgLabel.setStyle("-fx-text-fill:#E63946;");
            msgLabel.setText("Choisissez un type.");
            return;
        }
        Bateau b = new Bateau(0, Session.getIdUser(), type);
        boolean ok = dao.ajouter(b);
        if (ok) {
            msgLabel.setStyle("-fx-text-fill:#007A33;");
            msgLabel.setText("Bateau ajoute !");
            typeCombo.setValue(null);
            charger();
        }
    }

    @FXML public void retourDashboard() {
        try { MainApp.changerScene("dashboard_pecheur.fxml", 1100, 700); }
        catch (Exception e) { e.printStackTrace(); }
    }
}