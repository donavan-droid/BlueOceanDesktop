package controller;

import app.MainApp;
import app.Session;
import dao.*;
import model.Utilisateur;
import model.Signalement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class DashboardAdminController {

    @FXML private Label lblUsers, lblCaptures, lblSignalements;
    @FXML private TableView<Utilisateur> tableUsers;
    @FXML private TableColumn<Utilisateur, String> colId, colUsername,
                                                    colRole;
    @FXML private TableColumn<Utilisateur, String> colAction;

    @FXML private TableView<Signalement> tableSignalements;
    @FXML private TableColumn<Signalement, String> colSType, colSDesc,
                                                    colSLoc, colSDate;

    private UtilisateurDAO uDao = new UtilisateurDAO();
    private ObservableList<Utilisateur> dataUsers =
        FXCollections.observableArrayList();
    private ObservableList<Signalement> dataSignalements =
        FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Stats
        lblUsers.setText(
            String.valueOf(uDao.listerTous().size()));
        lblCaptures.setText(
            String.valueOf(new CaptureDAO().listerToutes().size()));
        lblSignalements.setText(
            String.valueOf(new SignalementDAO().listerTous().size()));

        // Table utilisateurs
        colId.setCellValueFactory(c ->
            new SimpleStringProperty(
                String.valueOf(c.getValue().getIdUser())));
        colUsername.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getUsername()));
        colRole.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getRole()));

        colAction.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Supprimer");
            {
                btn.getStyleClass().add("btn-danger");
                btn.setOnAction(e -> {
                    Utilisateur u =
                        getTableView().getItems().get(getIndex());
                    uDao.supprimerUtilisateur(u.getIdUser());
                    charger();
                });
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        tableUsers.setItems(dataUsers);

        // Table signalements
        colSType.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getType()));
        colSDesc.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getDescription()));
        colSLoc.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getLocalisation()));
        colSDate.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getDateSignalement() != null
                    ? c.getValue().getDateSignalement().toString() : ""));

        tableSignalements.setItems(dataSignalements);
        charger();
    }

    private void charger() {
        dataUsers.clear();
        dataUsers.addAll(uDao.listerTous());
        dataSignalements.clear();
        dataSignalements.addAll(new SignalementDAO().listerTous());
    }

    @FXML public void deconnecter() {
        Session.clear();
        try { MainApp.changerScene("login.fxml", 500, 400); }
        catch (Exception e) { e.printStackTrace(); }
    }
}