package com.example.dictionary.stage;

import com.example.dictionary.scene.SceneConstants;
import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.scene.SuperScene;
import javafx.stage.Stage;

public class Game2Window extends Window {
    public Game2Window(Stage stage) {
        window = new Stage();
        window.setTitle("Game2");
        window.setResizable(false);
        window.setScene((new SuperScene(SceneEnum.GAME_2)).getScene());
    }
}
