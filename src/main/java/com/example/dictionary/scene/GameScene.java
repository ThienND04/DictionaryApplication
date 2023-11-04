package com.example.dictionary.scene;

import com.example.dictionary.Dictionary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public final class GameScene extends AScene {
    @FXML
    private Button game1Nav;
    @FXML
    private Button game2Nav;
    @FXML
    private Button game3Nav;

    public GameScene(Dictionary dictionary) throws Exception {
        super(dictionary, SceneType.GAME);
    }

    @Override
    protected void init() {
        super.init();
        game1Nav = (Button) this.getScene().lookup("#game1Nav");
        game1Nav.setOnAction(event -> this.dictionary.setSceneType(SceneType.GAME_1));
        game2Nav = (Button) this.getScene().lookup("#game2Nav");
        game2Nav.setOnAction(event -> this.dictionary.setSceneType(SceneType.GAME_2));
        game3Nav = (Button) this.getScene().lookup("#game3Nav");
        game3Nav.setOnAction(event -> this.dictionary.setSceneType(SceneType.GAME_3));
    }
}
