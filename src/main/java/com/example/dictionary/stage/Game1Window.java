package com.example.dictionary.stage;

import com.example.dictionary.controller.Game1Controller;
import com.example.dictionary.game.Game1;
import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.scene.SuperScene;
import com.example.dictionary.user.UserManager;
import javafx.stage.Stage;

public class Game1Window extends Window {
    @Override
    protected void initScenes() {
        scenes.put(SceneEnum.GAME_1, new SuperScene(SceneEnum.GAME_1));
    }

    public Game1Window() {
        super("Game1");
        window.setResizable(false);
        changeScene(SceneEnum.GAME_1);
        window.setOnHiding(windowEvent -> {
            scenes.put(SceneEnum.GAME_1, new SuperScene(SceneEnum.GAME_1));
            scenes.get(SceneEnum.GAME_1).initTheme(SceneEnum.GAME_1, UserManager.getInstance().getCurrentUser().getTheme());
            changeScene(SceneEnum.GAME_1);
        });
    }
}
