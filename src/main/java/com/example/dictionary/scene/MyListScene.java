package com.example.dictionary.scene;

import com.example.dictionary.Dictionary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public final class MyListScene extends AScene {
    @FXML
    private Button homeNav;
    @FXML
    private Button gameNav;
    @FXML
    private Button myListNav;
    public MyListScene(Dictionary dictionary) throws Exception{
        super(dictionary, "my-list-view.fxml");

        this.gameNav = (Button) this.scene.lookup("#gameNav");
        this.homeNav = (Button) this.scene.lookup("#homeNav");
        this.myListNav = (Button) this.scene.lookup("#myListNav");
        gameNav.setOnAction(event -> dictionary.setSceneType(SceneType.GAME));
        homeNav.setOnAction(event -> dictionary.setSceneType(SceneType.HOME));
    }
}
