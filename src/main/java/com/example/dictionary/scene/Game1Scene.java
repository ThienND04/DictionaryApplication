package com.example.dictionary.scene;

import com.example.dictionary.Dictionary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public final class Game1Scene extends AScene {
    public static Game1Scene instance;
    public static Game1Scene getInstance() {
        return instance;
    }

    @FXML
    private Button homeNav;
    @FXML
    private Button gameNav;
    @FXML
    private Button myListNav;
    @FXML
    private Button ans1;
    @FXML
    private Button ans2;
    @FXML
    private Button ans3;

    public Game1Scene(Dictionary dictionary) throws Exception {
        super(dictionary, "game1-view.fxml");
        this.gameNav = (Button) this.scene.lookup("#gameNav");
        this.homeNav = (Button) this.scene.lookup("#homeNav");
        this.myListNav = (Button) this.scene.lookup("#myListNav");
        gameNav.setOnAction(event -> dictionary.setSceneType(SceneType.GAME));
        myListNav.setOnAction(event -> dictionary.setSceneType(SceneType.MY_LIST));
        homeNav.setOnAction(event -> dictionary.setSceneType(SceneType.HOME));
        instance = this;
    }

    @Override
    public void update() {

    }
}
