package com.example.dictionary.scene;

import com.example.dictionary.stage.PrimaryWindow;
import javafx.scene.control.Button;

public class PrimaryScene extends SuperScene {
    public PrimaryScene(PrimaryWindow primaryWindow, int sceneType) {
        super(sceneType);
        ((Button) scene.lookup("#gameNav")).setOnAction(event -> primaryWindow.setSceneType(SceneConstants.GAME));
        ((Button) scene.lookup("#homeNav")).setOnAction(event -> primaryWindow.setSceneType(SceneConstants.HOME));
        ((Button) scene.lookup("#translateNav")).setOnAction(event -> primaryWindow.setSceneType(SceneConstants.TRANSLATE));
    }
}
