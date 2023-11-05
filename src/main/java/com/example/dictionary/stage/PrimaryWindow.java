package com.example.dictionary.stage;

import com.example.dictionary.scene.*;
import javafx.stage.Stage;
import java.util.HashMap;

public class PrimaryWindow extends Window{
    private final HashMap<Integer, PrimaryScene> map = new HashMap<>();

    private static PrimaryWindow instance;

    public static PrimaryWindow getInstance() {
        return  instance;
    }

    public PrimaryWindow(Stage stage) {
        instance = this;
        window = stage;
        window.setTitle("Dictionary Application");
        initScenes();
        setSceneType(SceneConstants.HOME);
    }
    private void initScenes() {
        map.put(SceneConstants.GAME, new PrimaryScene(this, SceneConstants.GAME));
        map.put(SceneConstants.HOME, new PrimaryScene(this, SceneConstants.HOME));
        map.put(SceneConstants.TRANSLATE, new PrimaryScene(this, SceneConstants.TRANSLATE));
    }
    public void setSceneType(int sceneType) {
        window.setScene(map.get(sceneType).getScene());
    }
}

