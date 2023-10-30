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
    @FXML
    private Button game1Nav;

    public GameScene(Dictionary dictionary) throws Exception {
        super(dictionary, "game-view.fxml");
        this.gameNav = (Button) this.scene.lookup("#gameNav");
        this.homeNav = (Button) this.scene.lookup("#homeNav");
        this.myListNav = (Button) this.scene.lookup("#myListNav");
        this.game1Nav = (Button) this.scene.lookup("#game1Nav");
        myListNav.setOnAction(event -> dictionary.setSceneType(SceneType.MY_LIST));
        homeNav.setOnAction(event -> dictionary.setSceneType(SceneType.HOME));
        game1Nav.setOnAction(event -> dictionary.setSceneType(SceneType.GAME_1));
    }

    @Override
    public void update() {

    }
}
