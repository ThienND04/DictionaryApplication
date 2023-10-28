package com.example.dictionary.controller;

import com.example.dictionary.Dictionary;
import com.example.dictionary.game.Game1;
import com.example.dictionary.scene.Game1Scene;
import com.example.dictionary.scene.GameOverScene;
import com.example.dictionary.scene.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;

public class Game1Controller {
    private static Game1Controller instance;
    public static Game1Controller getInstance() {
        return instance;
    }

    @FXML
    Button gameNav;
    @FXML
    Button homeNav;
    @FXML
    Button myListNav;
    @FXML
    WebView question;
    @FXML
    private Button ans1;
    @FXML
    private Button ans2;
    @FXML
    private Button ans3;
    @FXML
    private Button prevBtn;
    @FXML
    private Button nextBtn;
    @FXML
    private Button submitBtn;

    @FXML
    public void initialize() {
        instance = this;
        updateScene();
    }

    @FXML
    public void ans1BtnClicked() {
        Game1.getInstance().getCurrentQuestion().setSelectedAnswer(1);
        updateScene();
    }

    @FXML
    public void ans2BtnClicked() {
        Game1.getInstance().getCurrentQuestion().setSelectedAnswer(2);
        updateScene();
    }

    @FXML
    public void ans3BtnClicked() {
        Game1.getInstance().getCurrentQuestion().setSelectedAnswer(3);
        updateScene();
    }

    @FXML
    public void prevBtnClicked() {
        Game1.getInstance().prevQuestion();
        updateScene();
    }

    @FXML
    public void nextBtnClicked() {
        Game1.getInstance().nextQuestion();
        updateScene();
    }
    @FXML
    public void submitBtnClicked() {
        GameOverScene.getInstance().update(Game1.getInstance());
        Game1Scene.getInstance().dictionary.setSceneType(SceneType.GAME_OVER);
    }

    public void updateScene() {
        Game1.Question qs = Game1.getInstance().getCurrentQuestion();
        int selectedAns = qs.getSelectedAnswer();

        question.getEngine().loadContent(qs.getQuestion());

        ans1.setText(qs.getAnswers().get(0));
        ans2.setText(qs.getAnswers().get(1));
        ans3.setText(qs.getAnswers().get(2));

        ans1.setStyle("-fx-background-color: steelblue");
        ans2.setStyle("-fx-background-color: steelblue");
        ans3.setStyle("-fx-background-color: steelblue");

        if(selectedAns == 1) {
            ans1.setStyle("-fx-background-color: Gray");
        }
        else if(selectedAns == 2) {
            ans2.setStyle("-fx-background-color: Gray");
        }
        else if(selectedAns == 3) {
            ans3.setStyle("-fx-background-color: Gray");
        }
    }
}
