package com.example.dictionary.controller;

import com.example.dictionary.game.Game2;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class Game2Controller {
    @FXML
    GridPane grid;
    @FXML
    Button newGame;
    @FXML
    Label timeLabel;
    private final Game2 game = new Game2();
    private final int n = 10;

    private final AtomicLong time = new AtomicLong(0);
    private final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> timeLabel.setText(String.valueOf((double) time.incrementAndGet() / 10))));
    private int solvedQuestion = 0;

    @FXML
    void initialize() {
        newGame.setOnAction(event -> newGame());
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private WebView clickedWord;
    private String clickedText;
    private ArrayList<String> list = new ArrayList<>();

    private void newGame() {
        list = game.generate(n);
        for (int i = 0; i < grid.getChildren().size(); i++) {
            WebView temp = (WebView) grid.getChildren().get(i);
            if (i < 2 * n) {
                temp.getEngine().loadContent(list.get(i));
                temp.setVisible(true);
                int finalI = i;

                temp.setOnMouseClicked(event -> {
                    if (clickedWord != null) {
                        if (game.checkAnswer(clickedText, list.get(finalI))) {
                            clickedWord.setVisible(false);
                            temp.setVisible(false);
                            this.solvedQuestion++;
                            if (this.solvedQuestion == n)
                                finishGame();
                        }
                        clickedWord.getEngine().executeScript("document.body.style.backgroundColor = 'white'");
                        temp.getEngine().executeScript("document.body.style.backgroundColor = 'white'");
                        clickedWord = null;
                    } else {
                        temp.getEngine().executeScript("document.body.style.backgroundColor = 'red'");
                        clickedWord = temp;
                        clickedText = list.get(finalI);
                    }
                });
            } else {
                temp.setVisible(false);
            }
        }

        clickedWord = null;
        solvedQuestion = 0;
        time.set(0);
        newGame.setVisible(false);
        timeline.play();
    }

    private void finishGame() {
        timeline.stop();
        newGame.setVisible(true);
    }
}
