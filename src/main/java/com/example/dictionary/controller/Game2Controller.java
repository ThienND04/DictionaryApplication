package com.example.dictionary.controller;

import com.example.dictionary.game.Game2;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    private final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
        timeLabel.setText(String.valueOf((double) time.incrementAndGet() / 10));
    }));
    private int solvedQuestion = 0;

    @FXML
    void initialize() {
        newGame.setOnAction(event -> newGame());
        timeline.setCycleCount(Animation.INDEFINITE);

        for (int i = 0; i < 2 * n; i++) {
            Button temp = (Button) ((AnchorPane) grid.getChildren().get(i)).getChildren().get(0);
            temp.setOnAction(event -> {
                if (!temp.getText().equals("")) {
                    if (clickedButton != null) {
                        if (game.checkAnswer(clickedButton.getText(), temp.getText())) {
                            clickedButton.setVisible(false);
                            temp.setVisible(false);
                            this.solvedQuestion++;
                            if (this.solvedQuestion == n)
                                finishGame();
                        }
                        clickedButton.setStyle(null);
                        clickedButton = null;
                    } else {
                        temp.setStyle("-fx-background-color:gray");
                        clickedButton = temp;
                    }
                }
            });
        }
    }

    private Button clickedButton = null;

    private void newGame() {
        ArrayList<String> list = game.generate(n);
        for (int i = 0; i < grid.getChildren().size(); i++) {
            Button temp = (Button) ((AnchorPane) grid.getChildren().get(i)).getChildren().get(0);
            if (i < 2 * n) {
                temp.setText(list.get(i));
                temp.setVisible(true);
            } else
                temp.setVisible(false);
        }
        clickedButton = null;
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
