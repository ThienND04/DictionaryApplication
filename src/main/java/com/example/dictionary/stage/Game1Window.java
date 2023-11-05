package com.example.dictionary.stage;

import com.example.dictionary.scene.SceneConstants;
import com.example.dictionary.scene.SuperScene;
import javafx.stage.Stage;

public class Game1Window extends Window {
    public Game1Window(Stage stage) {
        window = new Stage();
        window.setTitle("Game1");
        window.setResizable(false);
        window.setScene((new SuperScene(SceneConstants.GAME_1)).getScene());
    }
}
