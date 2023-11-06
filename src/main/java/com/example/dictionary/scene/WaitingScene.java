package com.example.dictionary.scene;

import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;

public class WaitingScene extends SuperScene {
    public WaitingScene() {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        StackPane rootPane = new StackPane(progressIndicator);
        rootPane.setBackground(null);
        scene = new Scene(rootPane);
        scene.setFill(null);
    }
}
