package gui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TopBar extends BorderPane {

    private final TextField searchField;
    private final Button addButton;
    private final Button settingsButton;

    // Constructor
    public TopBar() {

        searchField = new TextField();
        searchField.setPromptText("Search passwords...");
        searchField.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        addButton = new Button("+ Add Password");
        settingsButton = new Button("⚙");

        HBox rightBox = new HBox(10);
        rightBox.getChildren().addAll(addButton, settingsButton);
        settingsButton.setPrefWidth(40);

        setCenter(searchField);
        setRight(rightBox);

        setPadding(new Insets(15));
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }
}