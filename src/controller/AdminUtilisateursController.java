package controller;

import app.MainApp;
import app.Session;
import dao.UtilisateurDAO;
import model.Utilisateur;
import service.UtilisateurService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class AdminUtilisateursController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label msgLabel;
    @FXML private TableView<Utilisateur> tableUsers;
    @FXML private TableColumn<Utilisateur, String> colId, colUsername,
                                                    colRole, colAction;

    private UtilisateurDAO dao = new UtilisateurDAO();
    private ObservableList<Utilisateur> data =
        FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(c ->
            new SimpleStringProperty(
                String.valueOf(c.getValue().getIdUser())));
        colUsername.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getUsername()));
        colRole.setCellValueFactory(c ->
            new SimpleStringProperty(c.getValue().getRole()));

        colAction.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("🗑 Supprimer");
            {
                btn.getStyleClass().add("btn-danger");
                btn.setOnAction(e -> {
                    Utilisateur u =
                        getTableView().getItems().get(getIndex());
                    // Empêcher suppression de son propre compte
                    if (u.getIdUser() ==
                            Session.getUtilisateur().getIdUser()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING,
                            "Vous ne pouvez pas supprimer votre propre compte.");
                        alert.showAndWait();
                        return;
                    }
                    Alert confirm = new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "Supprimer " + u.getUsername() + " ?",
                        ButtonType.YES, ButtonType.NO
                    );
                    confirm.showAndWait().ifPresent(r -> {
                        if (r == ButtonType.YES) {
                            dao.supprimerUtilisateur(u.getIdUser());
                            charger();
                        }
                    });
                });
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        tableUsers.setItems(data);
        charger();
    }

    private void charger() {
        data.clear();
        List<Utilisateur> liste = dao.listerTous();
        System.out.println("Utilisateurs chargés : " + liste.size());
        data.addAll(liste);
    }

    @FXML
    public void ajouterAdmin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            msgLabel.setStyle("-fx-text-fill:#E63946;");
            msgLabel.setText("Remplissez tous les champs.");
            return;
        }

        Utilisateur u = new Utilisateur(0, username, password, "admin");
        boolean ok = dao.inscrire(u);
        if (ok) {
            msgLabel.setStyle("-fx-text-fill:#007A33;");
            msgLabel.setText("✅ Admin créé !");
            usernameField.clear();
            passwordField.clear();
            charger();
        } else {
            msgLabel.setStyle("-fx-text-fill:#E63946;");
            msgLabel.setText("❌ Erreur — username déjà utilisé ?");
        }
    }

    @FXML public void retour() {
        try { MainApp.changerScene("dashboard_admin.fxml", 1100, 700); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML public void deconnecter() {
        Session.clear();
        try { MainApp.changerScene("login.fxml", 500, 400); }
        catch (Exception e) { e.printStackTrace(); }
    }
}