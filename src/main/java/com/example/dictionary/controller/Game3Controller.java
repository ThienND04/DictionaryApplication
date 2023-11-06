package com.example.dictionary.controller;

import com.example.dictionary.game.Game3;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Game3Controller {

    @FXML
    public Label inform;
    @FXML
    private Label meaning;
    @FXML
    private HBox guessWord;
    @FXML
    private HBox input;
    @FXML
    private Button newGame;
    @FXML
    ProgressBar bar;
    @FXML
    Button nextBtn;
    @FXML
    void initialize() {
        time = new AtomicLong(0);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> time.incrementAndGet()));
        timeline.setCycleCount(Animation.INDEFINITE);

        newGame.setOnAction(event -> newGame());

        nextBtn.setOnAction(event -> nextQuestion());
    }
    private AtomicLong time;
    private final Game3 game = new Game3();
    private int solvedQuestion = 0;
    private final int n = 5;

    private void newGame() {
        solvedQuestion = 0;
        newGame.setVisible(false);
        nextQuestion();
        bar.setProgress(0);
    }

    private void finish() {
        newGame.setVisible(true);
    }

    private void nextQuestion() {
        ArrayList<String> data = game.generate();
        meaning.setText(data.get(1));
        inform.setText("");
        guessWord.getChildren().clear();
        input.getChildren().clear();
        List<String> list = Arrays.asList(data.get(0).split(""));
        Collections.shuffle(list);

        for (String s : list) {
            Button btn = new Button();
            btn.setText(s);
            btn.setOnAction(event -> {
                btn.setVisible(false);
                Button temp = new Button();
                temp.setText(btn.getText());
                temp.setOnAction(event1 -> {
                    btn.setVisible(true);
                    guessWord.getChildren().remove(temp);
                });
                guessWord.getChildren().add(temp);

                if (guessWord.getChildren().size() == data.get(0).length()) {
                    boolean flag = true;
                    for (int j = 0; j < data.get(0).length(); j++) {
                        Button button = (Button) guessWord.getChildren().get(j);
                        if (data.get(0).charAt(j) != button.getText().charAt(0)) {
                            inform.setText("Wrong");
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        solvedQuestion++;
                        bar.setProgress(solvedQuestion * 1.0 / n);
                        if (solvedQuestion == n)
                            finish();
                        else {
                            nextQuestion();
                        }
                    }
                }
            });
            input.getChildren().add(btn);
        }
    }
}
