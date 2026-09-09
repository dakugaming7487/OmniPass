package gui;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.Parent;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class SettingsView {

    private final Runnable onBack;
    private final VaultService vaultService;

    public SettingsView(VaultService vaultService,Runnable onBack) {
        this.vaultService = vaultService;
        this.onBack = onBack;
    }

    private void applyThemeToDialog(DialogPane dialogPane){ThemeManager.applyThemeToDialog(dialogPane);}

    public Parent createContent() {

        Label title = new Label("⚙ Settings");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label vaultLabel = new Label("Vault");
        vaultLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button exportButton = new Button("📤 Export Vault");
        exportButton.setOnAction(event->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export OmniPass Vault");
            fileChooser.setInitialFileName("omnipass-backup.opb");

            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("OmniPass Backup", "*.opb"));

            Window window = exportButton.getScene().getWindow();

            java.io.File file = fileChooser.showSaveDialog(window);

            if (file == null){return;}

            Dialog<String> passwordDialog = new Dialog<>();
            passwordDialog.setTitle("Export Vault");

            DialogPane dialogPane = passwordDialog.getDialogPane();

            Label titleLabel = new Label("Create an export password");
            titleLabel.setStyle("-fx-font-size: 18px;" + "-fx-font-weight: bold;");

            Label passwordLabel = new Label("Export Password:");

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Enter a password for thsi backup");
            passwordField.setPrefWidth(260);
            passwordField.setMinWidth(260);
            passwordField.setPrefHeight(35);
            passwordField.setMaxHeight(35);

            HBox passwordRow = new HBox(10);
            passwordRow.setAlignment(Pos.CENTER_LEFT);
            passwordRow.getChildren().addAll(passwordLabel,passwordField);

            VBox content = new VBox(15);
            content.setPadding(new Insets(20));
            content.getChildren().addAll(titleLabel,passwordRow);

            dialogPane.setContent(content);

            ButtonType exportButtonType = new ButtonType("Export", ButtonBar.ButtonData.OK_DONE);

            dialogPane.getButtonTypes().addAll(exportButtonType,ButtonType.CANCEL);

            applyThemeToDialog(dialogPane);

            passwordDialog.setResultConverter(button -> {
                if(button == exportButtonType){return passwordField.getText();}
                return null;
            });

            passwordDialog.showAndWait().ifPresent(exportPassword ->{

                if (exportPassword.isBlank()){return;}

                try {
                    vaultService.exportVault(file.getAbsolutePath(), exportPassword);

                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    applyThemeToDialog(success.getDialogPane());

                    success.setTitle("Export Successful");
                    success.setHeaderText(null);
                    success.setContentText("Your OmniPass vault was exported successfully.");

                    success.showAndWait();
                } catch (Exception e){
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    applyThemeToDialog(error.getDialogPane());

                    error.setTitle("Export failed");
                    error.setTitle(null);
                    error.setContentText("Failed tp export the vault.");

                    error.showAndWait();
                }
            
            });

        });

        Button importButton = new Button("📥 Import Vault");
        importButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Import OmniPass Vault");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("OmniPass Backup", "*.opb"));

            Window window = importButton.getScene().getWindow();

            java.io.File file = fileChooser.showOpenDialog(window);

            if (file == null){return;}

            Dialog<String> passwordDialog = new Dialog<>();

            passwordDialog.setTitle("Import Vault");

            DialogPane dialogPane = passwordDialog.getDialogPane();

            Label titleLabel = new Label("Enter the backup password");
            titleLabel.setStyle("-fx-font-size: 18px;"+"-fx-font-weight: bold;");

            Label passwordLabel = new Label("Backup Password:");
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Enter the password for yhis backup");

            passwordField.setPrefWidth(260);
            passwordField.setMinWidth(260);
            passwordField.setPrefHeight(35);
            passwordField.setMaxHeight(35);

            HBox passwordRow = new HBox(10);
            passwordRow.setAlignment(Pos.CENTER_LEFT);

            passwordRow.getChildren().addAll(passwordLabel,passwordField);

            VBox content = new VBox(15);
            content.setPadding(new Insets(20));
            content.getChildren().addAll(titleLabel,passwordRow);

            dialogPane.setContent(content);

            ButtonType importButtonType = new ButtonType("Import",ButtonBar.ButtonData.OK_DONE);

            dialogPane.getButtonTypes().addAll(importButtonType,ButtonType.CANCEL);

            applyThemeToDialog(dialogPane);

            passwordDialog.setResultConverter(button ->{
                if (button == importButtonType){return passwordField.getText();}
                return null;
            });

            passwordDialog.showAndWait().ifPresent(importPassword -> {
                if (importPassword.isBlank()){return;}

                try {
                    vaultService.importVault(file.getAbsolutePath(), importPassword);

                    Alert success = new Alert(Alert.AlertType.INFORMATION);

                    applyThemeToDialog(success.getDialogPane());

                    success.setTitle("Import Successful");

                    success.setHeaderText(null);

                    success.setContentText("Your OmniPass vault was imported successfully.");

                    success.showAndWait();
                } catch (Exception e){
                    Alert error = new Alert(Alert.AlertType.ERROR);

                    applyThemeToDialog(error.getDialogPane());

                    error.setTitle("Import failed");

                    error.setHeaderText(null);

                    error.setContentText("Failed to import the vault. " + "Check the backup file and password.");

                    error.showAndWait();
                }
            });

        });

        Button deleteButton = new Button("🗑 Delete Vault");

        VBox vaultSection = new VBox(10);
        vaultSection.getChildren().addAll(exportButton,importButton,deleteButton);

        // Security section
        Label securityLabel = new Label("Security");
        securityLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button changePasswordButton = new Button("🔐 Change Master Password");
        Button lockButton = new Button("🔒 Lock OmniPass");

        VBox securitySection = new VBox(10);
        securitySection.getChildren().addAll(changePasswordButton,lockButton);

        Label appearanceLabel = new Label("Appearance");
        appearanceLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label themeLabel = new Label("Theme");

        ComboBox<String> themeBox = new ComboBox<>();
        themeBox.getItems().addAll("Light", "Dark");
        themeBox.setValue(ThemeManager.getCurrentTheme());
        themeBox.setPrefWidth(120);
        themeBox.setMinWidth(120);

        HBox themeRow = new HBox(15);
        themeRow.setAlignment(Pos.CENTER_LEFT);
        themeRow.getChildren().addAll(themeLabel, themeBox);

        Label aboutLabel = new Label("About");
        aboutLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button aboutButton = new Button("ℹ About OmniPass");

        Button backButton = new Button("<- Back");
        backButton.setOnAction(event -> onBack.run());

        Separator separator = new Separator();

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));

        root.getChildren().addAll(title,separator,vaultLabel,vaultSection,securityLabel,securitySection,appearanceLabel,themeRow,aboutLabel,aboutButton,backButton);

        themeBox.setOnAction(event -> {
            String selectedTheme = themeBox.getValue();

            ThemeManager.setTheme(selectedTheme);
            ThemeManager.applyThemeToRoot(root, selectedTheme);
        });

        ThemeManager.applyThemeToRoot(root,ThemeManager.getCurrentTheme());
        return root;
    }
}