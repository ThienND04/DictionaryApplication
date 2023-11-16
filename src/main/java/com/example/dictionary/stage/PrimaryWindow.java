package com.example.dictionary.stage;

import com.example.dictionary.scene.*;
import javafx.stage.Stage;

public class PrimaryWindow extends Window {

    private static PrimaryWindow instance;

    public static PrimaryWindow getInstance() {
        return instance;
    }

    public PrimaryWindow(Stage stage) {
        super(stage, "Dictionary Application");
        instance = this;
        changeScene(SceneEnum.LOGIN);
    }


    @Override
    public void initScenes() {
        scenes.put(SceneEnum.LOGIN, new SuperScene(SceneEnum.LOGIN));
        scenes.put(SceneEnum.GAME, new SuperScene(SceneEnum.GAME));
        scenes.put(SceneEnum.TRANSLATE, new SuperScene(SceneEnum.TRANSLATE));
        scenes.put(SceneEnum.USER, new SuperScene(SceneEnum.USER));
        scenes.put(SceneEnum.SIGNUP, new SuperScene(SceneEnum.SIGNUP));
        scenes.put(SceneEnum.HOME, new SuperScene(SceneEnum.HOME));
    }
}

