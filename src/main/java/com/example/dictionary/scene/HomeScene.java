package com.example.dictionary.scene;

import com.example.dictionary.Dictionary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public final class HomeScene extends AScene{
    @FXML
    private Button homeNav;
    @FXML
    private Button gameNav;
    @FXML
    private Button myListNav;
    public HomeScene(Dictionary dictionary) throws Exception {
        super(dictionary, "home-view.fxml");

        this.gameNav = (Button) this.scene.lookup("#gameNav");
        this.homeNav = (Button) this.scene.lookup("#homeNav");
        this.myListNav = (Button) this.scene.lookup("#myListNav");

        gameNav.setOnAction(event -> this.dictionary.setSceneType(SceneType.GAME));
        myListNav.setOnAction(event -> this.dictionary.setSceneType(SceneType.MY_LIST));
    }

    @Override
    public void update() {

    }
}
