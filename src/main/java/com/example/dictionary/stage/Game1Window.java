package com.example.dictionary.stage;

import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.scene.SuperScene;
import javafx.stage.Stage;

public class Game1Window extends Window {
    @Override
    protected void initScenes() {
        scenes.put(SceneEnum.GAME_1, new SuperScene(SceneEnum.GAME_1));
    }

    public Game1Window(Stage stage) {
        super("Game1");
        window.setResizable(false);
        changeScene(SceneEnum.GAME_1);
    }
}
