package gui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;

public class ThemeManager {

    private static String currentTheme = "Dark";

    public static void setTheme(String theme) {
        currentTheme = theme;
    }

    public static String getCurrentTheme() {
        return currentTheme;
    }

    public static void applyThemeToDialog(DialogPane dialogPane) {

        String theme = getCurrentTheme();

        if ("Dark".equals(theme)) {
            dialogPane.setStyle("-fx-background-color: #1e1e1e;");

            javafx.scene.Node header = dialogPane.lookup(".header-panel");

            if (header != null) {header.setStyle("-fx-background-color: #1e1e1e;");}

        javafx.scene.Node content = dialogPane.lookup(".content");

        if (content != null) {content.setStyle("-fx-background-color: #1e1e1e;");}

        for (javafx.scene.Node node : dialogPane.lookupAll(".label")) {
            node.setStyle("-fx-text-fill: #ffffff;");
        }

        for (javafx.scene.Node node : dialogPane.lookupAll(".text-field")) {
            node.setStyle(
                "-fx-background-color: #2b2b2b;" +
                "-fx-text-fill: #ffffff;" +
                "-fx-prompt-text-fill: #888888;" +
                "-fx-border-color: #555555;"
            );
        }

        for (javafx.scene.Node node : dialogPane.lookupAll(".button")) {
            node.setStyle(
                "-fx-background-color: #333333;" +
                "-fx-text-fill: #ffffff;"
            );
        }

    } else {

        dialogPane.setStyle("-fx-background-color: #f4f4f4;");

        for (javafx.scene.Node node : dialogPane.lookupAll(".label")) {
            node.setStyle("-fx-text-fill: #222222;");
        }
    }
}

    // Apply theme to a Scene
    public static void applyTheme(Scene scene, String theme) {
        if (scene == null) {return;}

        applyThemeToRoot(scene.getRoot(), theme);
    }

    // Apply theme directly to a root node
    public static void applyThemeToRoot(Parent root, String theme) {
        if (root == null) {return;}

        root.getStyleClass().removeAll("light-theme","dark-theme");

        if ("Light".equals(theme)) {
            root.getStyleClass().add("light-theme");
        } else {
            root.getStyleClass().add("dark-theme");
        }
    }
}