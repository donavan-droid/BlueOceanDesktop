module BlueOceanDesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires mysql.connector.j;

    opens app to javafx.graphics;
    opens controller to javafx.fxml;
    opens model to com.google.gson;

    exports app;
    exports controller;
    exports model;
    exports dao;
    exports service;
}