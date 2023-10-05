package com.example.dictionary.scene;

import com.example.dictionary.Dictionary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public final class GameScene extends AScene {
    @FXML
    private Button homeNav;
    @FXML
    private Button gameNav;
    @FXML
    private Button myListNav;
    public GameScene(Dictionary dictionary) throws Exception{
        super(dictionary, "game-view.fxml");
        this.gameNav = (Button) this.scene.lookup("#gameNav");
        this.homeNav = (Button) this.scene.lookup("#homeNav");
        this.myListNav = (Button) this.scene.lookup("#myListNav");
        myListNav.setOnAction(event -> dictionary.setSceneType(SceneType.MY_LIST));
        homeNav.setOnAction(event -> dictionary.setSceneType(SceneType.HOME));
    }
}
