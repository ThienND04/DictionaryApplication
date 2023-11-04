package com.example.dictionary.controller;

import com.example.dictionary.game.Game2;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Game2Controller {
    @FXML
    Button bt1;
    @FXML
    Button bt2;
    @FXML
    Button bt3;
    @FXML
    Button bt4;
    @FXML
    Button bt5;
    @FXML
    Button bt6;
    @FXML
    Button bt7;
    @FXML
    Button bt8;
    @FXML
    Button bt9;
    @FXML
    Button bt10;
    @FXML
    Button bt11;
    @FXML
    Button bt12;
    @FXML
    Button newGame;
    @FXML
    Label timeLabel;
    private final Game2 game = new Game2();
    private final ArrayList<Button> buttonList = new ArrayList<Button>();

    private int solvedQuestion;

    private AtomicInteger time;
    private Timeline timeline;

    @FXML
    void initialize() {
        initComponent();
    }

    private Button clickedButton = null;

    private void newGame() {
        solvedQuestion = 0;
        ArrayList<String> list = game.generate();
        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setVisible(true);
            buttonList.get(i).setText(list.get(i));
        }
        time.set(0);
        timeline.play();
    }

    private void finishGame() {
        timeline.stop();
    }

    private void initComponent() {
        buttonList.add(bt1);
        buttonList.add(bt2);
        buttonList.add(bt3);
        buttonList.add(bt4);
        buttonList.add(bt5);
        buttonList.add(bt6);
        buttonList.add(bt7);
        buttonList.add(bt8);
        buttonList.add(bt9);
        buttonList.add(bt10);
        buttonList.add(bt11);
        buttonList.add(bt12);

        newGame.setOnAction(event -> newGame());

        time = new AtomicInteger(0);
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            int currentCount = time.incrementAndGet();
            timeLabel.setText(String.valueOf(currentCount));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        for (int i = 0; i < buttonList.size(); i++) {
            Button temp = buttonList.get(i);
            temp.setOnAction(event -> {
                if (!temp.getText().equals("")) {
                    if (clickedButton != null) {
                        if (game.checkAnswer(clickedButton.getText(), temp.getText())) {
                            clickedButton.setVisible(false);
                            temp.setVisible(false);
                            this.solvedQuestion++;
                            if(this.solvedQuestion == 6)
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
}
