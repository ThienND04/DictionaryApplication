package com.example.dictionary.controller;

import com.example.dictionary.Dictionary;
import com.example.dictionary.game.Game1;
import com.example.dictionary.scene.GameOverScene;
import com.example.dictionary.scene.SceneType;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;

public class GameOverController {
    private static GameOverController instance;
    public static GameOverController getInstance() {
        return instance;
    }

    @FXML
    Button gameNav;
    @FXML
    Button homeNav;
    @FXML
    Button myListNav;
    @FXML
    WebView scoreView;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button playAgainBtn;

    @FXML
    public void initialize() {
        instance = this;
    }

    @FXML
    public void cancelBtnClicked() {
        GameOverScene.getInstance().getDictionary().setSceneType(SceneType.GAME);
    }

    @FXML
    public void playAgainBtnClicked() {
        Game1.getInstance().playAgain();
        GameOverScene.getInstance().getDictionary().setSceneType(SceneType.GAME_1);
    }
}
