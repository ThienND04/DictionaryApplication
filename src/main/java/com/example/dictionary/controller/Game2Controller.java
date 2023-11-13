package com.example.dictionary.controller;

import com.example.dictionary.game.Game2;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class Game2Controller {

    public static final int NUMBER_OF_QUESTIONS = 10;

    public static Game2Controller getInstance() {
        return instance;
    }

    private static Game2Controller instance;
    @FXML
    GridPane grid;
    @FXML
    Button newGameBtn;
    @FXML
    Label timeLabel;
    @FXML
    Button stopGameBtn;
    private final Game2 game = new Game2();

    private final AtomicLong time = new AtomicLong(0);
    private final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> timeLabel.setText(String.valueOf((double) time.incrementAndGet() / 10))));
    private int solvedQuestion = 0;

    @FXML
    void initialize() {
        instance = this;
        newGameBtn.setOnAction(event -> newGame());
        stopGameBtn.setOnAction(event -> finishGame());
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private WebView clickedWord;
    private String clickedText;

    private void newGame() {
        ArrayList<String> list = game.generate();

        if (list.size() == 0) {
            new Alert(Alert.AlertType.WARNING, "Không đủ số lượng từ").show();
            return;
        }

        for (int i = 0; i < 2 * NUMBER_OF_QUESTIONS; i++) {
            WebView temp = (WebView) grid.getChildren().get(i);
            temp.getEngine().loadContent(list.get(i));
            temp.setVisible(true);
            int finalI = i;
            temp.setOnMouseClicked(event -> {
                if (clickedWord != null) {
                    if (game.checkAnswer(clickedText, list.get(finalI))) {
                        clickedWord.setVisible(false);
                        temp.setVisible(false);
                        this.solvedQuestion++;
                        if (this.solvedQuestion == NUMBER_OF_QUESTIONS)
                            finishGame();
                    } else {
                        clickedWord.getEngine().executeScript("document.body.style.backgroundColor = 'white'");
                        clickedWord.getEngine().executeScript("document.body.style.color = 'black'");
                    }
                    clickedWord = null;
                } else {
                    temp.getEngine().executeScript("document.body.style.backgroundColor = '#05386B'");
                    temp.getEngine().executeScript("document.body.style.color = '#EDF5E1'");
                    clickedWord = temp;
                    clickedText = list.get(finalI);
                }
            });
        }
        stopGameBtn.setVisible(true);
        newGameBtn.setVisible(false);
        timeline.play();
    }

    public void finishGame() {
        clickedWord = null;
        solvedQuestion = 0;
        time.set(0);
        timeline.stop();
        newGameBtn.setVisible(true);
        stopGameBtn.setVisible(false);

        for (int i = 0; i < 2 * NUMBER_OF_QUESTIONS; i++) {
            grid.getChildren().get(i).setVisible(false);
        }

    }
}
