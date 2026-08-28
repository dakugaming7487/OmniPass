package gui;

import gui.components.TopBar;

import core.PasswordEntry;

import javafx.geometry.Insets;

import javafx.scene.Parent;
import javafx.scene.layout.*;

import javafx.scene.control.*;

public class DashboardView {

    private BorderPane root;
    private VBox passwordCenter;

    private ListView<PasswordEntry> passwordList;

    private final VaultService vaultService;

    public DashboardView(VaultService vaultService){
        this.vaultService = vaultService;
    }

    private void showAddPaddwordDialog(ListView<PasswordEntry> passwordList){

        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setTitle("Add Password");
        dialog.setHeaderText("Add a new password");

        ButtonType saveButton = new ButtonType("Save",ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(saveButton,ButtonType.CANCEL);

        TextField websitesField = new TextField();
        websitesField.setPromptText("Website");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextArea notesField = new TextArea();
        notesField.setPromptText("Notes");
        notesField.setPrefRowCount(3);

        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Website:"),0,0);
        grid.add(websitesField,1,0);
        
        grid.add(new Label("Username:"),0,1);
        grid.add(usernameField,1,1);

        grid.add(new Label("Password:"),0,2);
        grid.add(passwordField,1,2);

        grid.add(new Label("Notes:"),0,3);
        grid.add(notesField,1,3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button->{

            if (button == saveButton){
                String website = websitesField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                String notes = notesField.getText();

                if(website.isBlank() || username.isBlank() || password.isBlank()){
                    return null;
                }

                vaultService.addEntry(website, username, password, notes);

                passwordList.getItems().setAll(vaultService.getEntries());
            }

            return button;
        });

        dialog.showAndWait();
    }

    private void showEditPasswordDialog(PasswordEntry entry,ListView<PasswordEntry> passwordList){

        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setTitle("Edit Password");
        dialog.setHeaderText("Edit password");

        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        TextField websiteField = new TextField(entry.getWebsite());
        TextField usernameField = new TextField(entry.getUsername());
        PasswordField passwordField = new PasswordField();
        TextArea notesField = new TextArea(entry.getNotes());

        websiteField.setPromptText("Website");
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        notesField.setPromptText("Notes");
        notesField.setPrefRowCount(3);

        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Website:"),0,0);
        grid.add(websiteField,1,0);
        
        grid.add(new Label("Username:"),0,1);
        grid.add(usernameField,1,1);

        grid.add(new Label("Password:"),0,2);
        grid.add(passwordField,1,2);

        grid.add(new Label("Notes:"),0,3);
        grid.add(notesField,1,3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button->{

            if (button == saveButton){

                String website = websiteField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                String notes = notesField.getText();

                if (website.isBlank()||username.isBlank()){
                    
                    if (password.isBlank()){password = entry.getPassword(); return null;}
                    return null;
                }

                vaultService.updateEntry(entry, website, username, password, notes);

                passwordList.getItems().setAll(vaultService.getEntries());
            };

            return button;

        });

        dialog.showAndWait();
    }

    public Parent createContent() {

    root = new BorderPane();
    root.setPadding(new Insets(20));

    passwordList = new ListView<>();
    passwordList.getItems().setAll(vaultService.getEntries());

    TopBar topBar = new TopBar();

    topBar.getAddButton().setOnAction(
        event -> showAddPaddwordDialog(passwordList)
    );

    topBar.getSettingsButton().setOnAction(
        event -> showSettings()
    );

    root.setTop(topBar);

    passwordList.setCellFactory(list -> {
        return new ListCell<PasswordEntry>() {

            @Override
            protected void updateItem(PasswordEntry entry, boolean empty) {
                super.updateItem(entry, empty);

                if (empty || entry == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                Label website = new Label(entry.getWebsite());
                Label username = new Label(entry.getUsername());

                Button editButton = new Button("Edit");
                Button deleteButton = new Button("Delete");

                HBox info = new HBox(15, website, username);
                HBox buttons = new HBox(10, editButton, deleteButton);

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                HBox row = new HBox(20, info, spacer, buttons);

                setGraphic(row);

                deleteButton.setOnAction(event -> {
                    vaultService.deleteEntry(entry);
                    passwordList.getItems().remove(entry);
                });

                editButton.setOnAction(event -> {
                    showEditPasswordDialog(entry, passwordList);
                });
            }
        };
    });

    passwordCenter = new VBox();
    VBox.setVgrow(passwordList, Priority.ALWAYS);
    passwordCenter.getChildren().add(passwordList);

    root.setCenter(passwordCenter);

    ThemeManager.applyThemeToRoot(
        root,
        ThemeManager.getCurrentTheme()
    );

    return root;
}

    private void showSettings() {

        SettingsView settingsView = new SettingsView(this::showDashboard);

        root.setTop(null);
        root.setCenter(settingsView.createContent());
    }

    private void showDashboard() {

        TopBar topBar = new TopBar();  

        topBar.getAddButton().setOnAction(event->showAddPaddwordDialog(passwordList));

        topBar.getSettingsButton().setOnAction(event -> showSettings());

        root.setTop(topBar);
        root.setCenter(passwordCenter);

        ThemeManager.applyThemeToRoot(root, ThemeManager.getCurrentTheme());
    }
}
