package com.example.dictionary.scene;

import com.example.dictionary.Dictionary;
import com.example.dictionary.game.AGame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;

public class GameOverScene extends AScene {
    public static GameOverScene instance;
    public static GameOverScene getInstance() {return instance; }
    @FXML
    WebView scoreView;

    public GameOverScene(Dictionary dictionary) throws Exception {
        super(dictionary, SceneType.GAME_OVER);
        this.scoreView = (WebView) getScene().lookup("#scoreView");
        instance = this;
    }

    AGame curGame;

    public AGame getCurGame() {
        return curGame;
    }

    public void setCurGame(AGame curGame) {
        this.curGame = curGame;
    }

    public void update() {
        // set score to red color format and put to webview.
        this.scoreView.getEngine().loadContent(
                String.format(
                        "<html> <p style='color : red'>Score: %d<p> </html>",
                        curGame.getScore()));
    }
}
