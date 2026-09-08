package gui;

import core.security.MasterPassword;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class OmniPassApp extends Application {

    @Override
    public void start(Stage stage) {

        File dataDirectory = new File("data");
        if (!dataDirectory.exists()){dataDirectory.mkdir();}

        Parent root;

        if (MasterPassword.exists()) {
            LoginView loginView = new LoginView();
            root = loginView.createContent(stage);
        } else {
            MasterPasswordSetupView setupView =
                    new MasterPasswordSetupView();
            root = setupView.createcontent(stage);
        }

        Scene scene = new Scene(root, 500, 300);

        scene.getStylesheets().add(
                getClass()
                        .getResource("/styles/style.css")
                        .toExternalForm()
        );

        ThemeManager.applyTheme(
                scene,
                ThemeManager.getCurrentTheme()
        );

        stage.setTitle("OmniPass");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}