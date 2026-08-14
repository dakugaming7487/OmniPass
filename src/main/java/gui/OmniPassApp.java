package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OmniPassApp extends Application {

    @Override
    public void start(Stage stage) {

        LoginView loginView = new LoginView();

        Scene scene = new Scene(loginView.createContent(stage), 500, 300);

        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

        stage.setTitle("OmniPass");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}