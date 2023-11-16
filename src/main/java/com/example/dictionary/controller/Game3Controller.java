package com.example.dictionary.controller;

import com.example.dictionary.game.Game3;
import com.example.dictionary.scene.SuperScene;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
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
    private Button pauseBtn;
    @FXML
    ProgressBar bar;
    @FXML
    Button nextBtn;
    private boolean isPaused = false;

    @FXML
    void initialize() {
        instance = this;
        time = new AtomicLong(0);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> time.incrementAndGet()));
        timeline.setCycleCount(Animation.INDEFINITE);
        meaning.setVisible(false);
        newGame.setOnAction(event -> newGame());
        nextBtn.setOnAction(event -> nextQuestion());
        pauseBtn.setOnAction(actionEvent -> {
            isPaused = ! isPaused;
            pauseBtn.setText(isPaused ? "Tiếp tục" : "Dừng");
            guessWord.setVisible(! isPaused);
            input.setVisible(! isPaused);
            nextBtn.setVisible(! isPaused);
        });
    }
    private AtomicLong time;
    private final Game3 game = new Game3();

    private void newGame() {
        game.newGame();
        if(! game.isReady()) {
            new Alert(Alert.AlertType.WARNING, "Không đủ số lượng từ").show();
            return;
        }
        newGame.setVisible(false);
        meaning.setVisible(true);
        nextQuestion();
        bar.setProgress(0);
        isPaused = false;
        pauseBtn.setVisible(true);
        pauseBtn.setText("Dừng");
        nextBtn.setVisible(true);
    }

    private void finish() {
        newGame.setVisible(true);
    }

    private void nextQuestion() {
        game.nextQuestion();
        meaning.setText(game.getMeaning());
        inform.setText("");
        guessWord.getChildren().clear();
        input.getChildren().clear();
        List<String> list = Arrays.asList(game.getGuessWord().split(""));
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

                if (guessWord.getChildren().size() == game.getGuessWord().length()) {
                    StringBuilder playerGuess = new StringBuilder();
                    guessWord.getChildren().stream().map(button -> ((Button)button).getText()).
                            reduce(playerGuess, (StringBuilder::append), StringBuilder::append);
                    if (game.isGuessedWord(playerGuess.toString())) {
                        game.increaseSolvedQuestion();
                        bar.setProgress(game.getProgress());
                        if (game.isFinished())
                            finish();
                        else {
                            nextQuestion();
                        }
                    } else {
                        inform.setText("Wrong");
                    }
                }
            });
            input.getChildren().add(btn);
        }
    }

    private static Game3Controller instance;
    public static Game3Controller getInstance() {
        return instance;
    }
    public static void setInstance(Game3Controller instance) {
        Game3Controller.instance = instance;
    }
}