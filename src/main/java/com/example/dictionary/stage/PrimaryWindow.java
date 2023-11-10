package com.example.dictionary.stage;

import com.example.dictionary.scene.*;
import javafx.stage.Stage;
import java.util.HashMap;

public class PrimaryWindow extends Window{
    private final HashMap<SceneEnum, PrimaryScene> map = new HashMap<>();

    private static PrimaryWindow instance;

    public static PrimaryWindow getInstance() {
        return  instance;
    }

    public PrimaryWindow(Stage stage) {
        instance = this;
        window = stage;
        window.setTitle("Dictionary Application");
        initScenes();
        setSceneType(SceneEnum.HOME);
    }
    private void initScenes() {
        map.put(SceneEnum.GAME, new PrimaryScene(this, SceneEnum.GAME));
        map.put(SceneEnum.HOME, new PrimaryScene(this, SceneEnum.HOME));
        map.put(SceneEnum.TRANSLATE, new PrimaryScene(this, SceneEnum.TRANSLATE));
    }
    public void setSceneType(SceneEnum sceneType) {
        window.setScene(map.get(sceneType).getScene());
        window.setMaximized(false);
    }
}

