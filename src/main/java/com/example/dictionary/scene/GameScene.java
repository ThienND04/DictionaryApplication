package com.example.dictionary.scene;

import com.example.dictionary.Dictionary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public final class GameScene extends AScene {
    @FXML
    private Button game1Nav;

    public GameScene(Dictionary dictionary) throws Exception {
        super(dictionary, SceneType.GAME);
    }

    @Override
    protected void init() {
        super.init();
        this.game1Nav = (Button) this.getScene().lookup("#game1Nav");
        game1Nav.setOnAction(event -> this.getDictionary().setSceneType(SceneType.GAME_1));
    }
}
