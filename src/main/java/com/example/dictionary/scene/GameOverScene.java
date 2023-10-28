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
    private Button homeNav;
    @FXML
    private Button gameNav;
    @FXML
    private Button myListNav;
    @FXML
    WebView scoreView;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button playAgainBtn;

    public GameOverScene(Dictionary dictionary) throws Exception {
        super(dictionary, "gameover-view.fxml");

        this.gameNav = (Button) this.scene.lookup("#gameNav");
        this.homeNav = (Button) this.scene.lookup("#homeNav");
        this.myListNav = (Button) this.scene.lookup("#myListNav");
        this.scoreView = (WebView) this.scene.lookup("#scoreView");
        this.cancelBtn = (Button) this.scene.lookup("#cancelBtn");
        this.playAgainBtn = (Button) this.scene.lookup("#playAgainBtn");

        gameNav.setOnAction(event -> this.dictionary.setSceneType(SceneType.GAME));
        myListNav.setOnAction(event -> this.dictionary.setSceneType(SceneType.MY_LIST));

        instance = this;
    }

    public void update(AGame game) {
        this.scoreView.getEngine().loadContent(String.format("<html> <p style='color : red'>Score: %d<p> </html>", game.getScore()));
    }
}
