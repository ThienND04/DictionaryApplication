package com.example.dictionary.stage;

import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.scene.SuperScene;
import com.example.dictionary.user.UserManager;
import javafx.stage.Stage;

public class Game2Window extends Window {
    @Override
    protected void initScenes() {
        scenes.put(SceneEnum.GAME_2, new SuperScene(SceneEnum.GAME_2));
    }
    public Game2Window() {
        window = new Stage();
        window.setTitle("Game2");
        window.setResizable(false);
        changeScene(SceneEnum.GAME_2);
        window.setOnHiding(windowEvent -> {
            scenes.put(SceneEnum.GAME_2, new SuperScene(SceneEnum.GAME_2));
            scenes.get(SceneEnum.GAME_2).initTheme(SceneEnum.GAME_2, UserManager.getInstance().getCurrentUser().getTheme());
            changeScene(SceneEnum.GAME_2);
        });
    }
}
