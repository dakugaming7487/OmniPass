package gui;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class SettingsView {

    private final Runnable onBack;

    public SettingsView(Runnable onBack) {
        this.onBack = onBack;
    }

    public Parent createContent() {

        Label title = new Label("Settings");
        title.setStyle("-fx-font-size: 24px;  -fx-font-weight: bold;");

        Label appearanceLabel = new Label("Appearance");
        appearanceLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; ");

        Label themeLabel = new Label("Theme");

        ComboBox<String> themeBox = new ComboBox<>();
        themeBox.getItems().addAll("Light", "Dark");
        themeBox.setValue(ThemeManager.getCurrentTheme());

        themeBox.setPrefWidth(120);
        themeBox.setMinWidth(120);

        HBox themeRow = new HBox(15);
        themeRow.setAlignment(Pos.CENTER_LEFT);
        themeRow.getChildren().addAll(themeLabel, themeBox);

        Button backButton = new Button("<- back");
        backButton.setOnAction(event -> onBack.run());

        Separator separator = new Separator();

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));

        root.getChildren().addAll(title, separator, appearanceLabel, themeRow, backButton);

        themeBox.setOnAction(event -> {
    String selectedTheme = themeBox.getValue();

    ThemeManager.setTheme(selectedTheme);

    ThemeManager.applyThemeToRoot(root, selectedTheme);
});

        ThemeManager.applyThemeToRoot(root, ThemeManager.getCurrentTheme());
    
        return root;
    }
}
