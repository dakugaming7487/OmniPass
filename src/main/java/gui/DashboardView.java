package gui;

import javafx.geometry.Insets;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;

public class DashboardView {

    public Parent createContent() {

        BorderPane root = new BorderPane();

        root.setPadding(new Insets(20));

        HBox topBar = createTopBar();

        VBox center = createCenter();

        root.setTop(topBar);
        root.setCenter(center);

        return root;

    }

    private HBox createTopBar() {

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search password");

        Button addButton = new Button("+ Add Password");

        HBox.setHgrow(searchBar, Priority.ALWAYS);

        HBox topBar = new HBox(15);
        topBar.getChildren().addAll(searchBar, addButton);

        return topBar;
    }

    private VBox createCenter() {

        ListView<String> passwordList = new ListView<>();

        VBox center = new VBox();

        VBox.setVgrow(passwordList, Priority.ALWAYS);

        center.getChildren().add(passwordList);

        return center;
    }
}
