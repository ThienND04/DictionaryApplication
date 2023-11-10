package com.example.dictionary.stage;

import com.example.dictionary.scene.SceneConstants;
import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.scene.SuperScene;
import javafx.stage.Stage;

public class Game3Window extends Window {
    public Game3Window(Stage stage) {
        window = new Stage();
        window.setTitle("Game3");
        window.setResizable(false);
        window.setScene((new SuperScene(SceneEnum.GAME_3)).getScene());
    }
}
