package com.example.dictionary.scene;

import com.example.dictionary.stage.PrimaryWindow;
import javafx.scene.control.Button;

public class PrimaryScene extends SuperScene {
    public PrimaryScene(PrimaryWindow primaryWindow, SceneEnum sceneType) {
        super(sceneType);
        ((Button) scene.lookup("#gameNav")).setOnAction(event -> primaryWindow.setSceneType(SceneEnum.GAME));
        ((Button) scene.lookup("#homeNav")).setOnAction(event -> primaryWindow.setSceneType(SceneEnum.HOME));
        ((Button) scene.lookup("#translateNav")).setOnAction(event -> primaryWindow.setSceneType(SceneEnum.TRANSLATE));
    }
}
