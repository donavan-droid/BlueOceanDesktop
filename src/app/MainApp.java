package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/fxml/login.fxml")
        );
        Scene scene = new Scene(loader.load(), 500, 400);
        scene.getStylesheets().add(
            getClass().getResource("/css/style.css").toExternalForm()
        );
        stage.setTitle("BlueOcean Desktop");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void changerScene(String fxml, int width, int height)
            throws Exception {
        FXMLLoader loader = new FXMLLoader(
            MainApp.class.getResource("/fxml/" + fxml)
        );
        Scene scene = new Scene(loader.load(), width, height);
        scene.getStylesheets().add(
            MainApp.class.getResource("/css/style.css").toExternalForm()
        );
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}