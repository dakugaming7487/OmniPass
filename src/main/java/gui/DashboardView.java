package gui;

import gui.components.TopBar;

import javafx.geometry.Insets;

import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;

public class DashboardView {

    public Parent createContent() {

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        TopBar topBar = new TopBar();

        ListView<String> passowrdList = new ListView<>();

        VBox center = new VBox();
        VBox.setVgrow(passowrdList, Priority.ALWAYS);
        center.getChildren().add(passowrdList);

        root.setTop(topBar);
        root.setCenter(center);

        return root;

    }
}
