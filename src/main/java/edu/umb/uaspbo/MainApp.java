package edu.umb.uaspbo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    private double x, y;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("DOFI SAPUTRA - 41522110006");
//        stage.initStyle(StageStyle.TRANSPARENT);
//
//        scene.setOnMousePressed(event -> {
//            x = event.getSceneX();
//            y = event.getSceneY();
//        });
//
//        scene.setOnMouseDragged(event -> {
//            stage.setX(event.getScreenX() - x);
//            stage.setY(event.getScreenY() - y);
//        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
