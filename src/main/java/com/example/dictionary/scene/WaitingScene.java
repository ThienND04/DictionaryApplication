package com.example.dictionary.scene;

import com.example.dictionary.Application;
import com.example.dictionary.stage.WindowEnum;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;

public class WaitingScene extends SuperScene {
    public WaitingScene() {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        Button btn = new Button("Hủy");
        btn.setOnAction(event -> Application.getInstance().hideWindow(WindowEnum.WAITING));
        StackPane rootPane = new StackPane(progressIndicator, btn);
        rootPane.setBackground(null);
        scene = new Scene(rootPane);
        scene.setFill(null);
    }
}
