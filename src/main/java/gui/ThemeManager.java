package gui;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class ThemeManager {

    private static String currentTheme = "Dark";

    public static void setTheme(String theme) {
        currentTheme = theme;
    }

    public static String getCurrentTheme() {
        return currentTheme;
    }

    // Apply theme to a Scene
    public static void applyTheme(Scene scene, String theme) {
        if (scene == null) {
            return;
        }

        applyThemeToRoot(scene.getRoot(), theme);
    }

    // Apply theme directly to a root node
    public static void applyThemeToRoot(Parent root, String theme) {
        if (root == null) {
            return;
        }

        root.getStyleClass().removeAll(
            "light-theme",
            "dark-theme"
        );

        if ("Light".equals(theme)) {
            root.getStyleClass().add("light-theme");
        } else {
            root.getStyleClass().add("dark-theme");
        }
    }
}