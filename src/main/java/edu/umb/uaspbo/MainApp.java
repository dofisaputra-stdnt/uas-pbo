package edu.umb.uaspbo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("DOFI SAPUTRA - 41522110006");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
