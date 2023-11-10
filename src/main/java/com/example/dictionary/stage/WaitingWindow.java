package com.example.dictionary.stage;

import com.example.dictionary.scene.WaitingScene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class WaitingWindow extends Window {
    public WaitingWindow(Stage stage) {
        window = new Stage();
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene((new WaitingScene()).getScene());
        window.setAlwaysOnTop(true);
        window.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> {
            window.setX(stage.getX() + stage.getWidth() / 2 - window.getWidth() / 2);
            window.setY(stage.getY() + stage.getHeight() / 2 - window.getHeight() / 2);

        });
        stage.xProperty().addListener((a, b, c) -> window.setX(stage.getX() + stage.getWidth() / 2 - window.getWidth() / 2));
        stage.yProperty().addListener((a, b, c) -> window.setY(stage.getY() + stage.getHeight() / 2 - window.getHeight() / 2));
        stage.widthProperty().addListener((a, b, c) -> window.setX(stage.getX() + stage.getWidth() / 2 - window.getWidth() / 2));
        stage.heightProperty().addListener((a, b, c) -> window.setY(stage.getY() + stage.getHeight() / 2 - window.getHeight() / 2));
    }
}
