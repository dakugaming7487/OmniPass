package gui;

import core.Vault;

import javafx.geometry.Pos;

import javafx.stage.Stage;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import core.security.MasterPassword;
import core.storage.VaultStorage;

import javax.crypto.SecretKey;

public class LoginView {

    private PasswordField passwordField = new PasswordField();

    public Parent createContent(Stage stage) {

        Label title = new Label("🔐 OmniPass");

        Label passwordLabel = new Label("Master Password");

        passwordField.setMaxWidth(250);
        passwordField.setPromptText("Enter master password");

        Button loginButton = new Button("Unlock Vault");
        loginButton.setMaxWidth(250);
        loginButton.setOnAction(event -> {

            String password = getPassword();

            SecretKey key = MasterPassword.authenticate(password);

            if (key == null) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Incorrect master password.");
                alert.showAndWait();

                return;
            }

            Vault vault = VaultStorage.load("data/vault.dat", key);

            if (vault == null){vault = new Vault();}

            VaultService vaultService = new VaultService(vault, key);
            DashboardView dashboard = new DashboardView(vaultService);
            Scene dashboardScene = new Scene(dashboard.createContent(), 900, 600);
            dashboardScene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
            ThemeManager.applyTheme(dashboardScene, ThemeManager.getCurrentTheme());

            stage.setScene(dashboardScene);
        });

        VBox root = new VBox(20);

        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        root.getChildren().addAll(title, passwordLabel, passwordField, loginButton);

        return root;
    }

    public String getPassword() {
        return passwordField.getText();
    }
}
