package com.example.dictionary.stage;

import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.scene.SuperScene;
import javafx.stage.Stage;

public class Game3Window extends Window {
    @Override
    protected void initScenes() {
        scenes.put(SceneEnum.GAME_3, new SuperScene(SceneEnum.GAME_3));
    }
    public Game3Window(Stage stage) {
        window = new Stage();
        window.setTitle("Game3");
        window.setResizable(false);
        changeScene(SceneEnum.GAME_3);
    }
}
