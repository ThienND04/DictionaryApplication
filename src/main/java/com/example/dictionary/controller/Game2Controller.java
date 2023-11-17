package com.example.dictionary.controller;

import com.example.dictionary.game.Game1;
import com.example.dictionary.game.Game2;
import com.example.dictionary.scene.SuperScene;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Worker;
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
    private static Game2Controller instance;

    public static Game2Controller getInstance() {
        return instance;
    }
    public static final int NUMBER_OF_QUESTIONS = 10;
    private Game2 game = new Game2();
    private ArrayList<String> list;
    private final AtomicLong time = new AtomicLong(0);
    @FXML
    GridPane grid;
    @FXML
    Button newGameBtn;
    @FXML
    Label timeLabel;
    private final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> timeLabel.setText(String.valueOf((double) time.incrementAndGet() / 10))));
    @FXML
    Button pauseBtn;
    private int solvedQuestion = 0;
    private boolean isPaused;
    private Button clickedWord;
    private String clickedText;


    @FXML
    public void initialize() {
        instance = this;
        newGameBtn.setOnAction(event -> newGame());
        pauseBtn.setOnAction(event -> handlePauseBtn());
        timeline.setCycleCount(Animation.INDEFINITE);

        for (int i = 0; i < 2 * NUMBER_OF_QUESTIONS; i++) {
            Button btn = (Button) grid.getChildren().get(i);
            btn.setVisible(false);
            int finalI = i;
            btn.setOnMouseClicked(event -> {
                if (clickedWord != null) {
                    if (game.checkAnswer(clickedText, list.get(finalI))) {
                        clickedWord.setVisible(false);
                        btn.setVisible(false);
                        this.solvedQuestion++;
                        if (this.solvedQuestion == NUMBER_OF_QUESTIONS)
                            finishGame();
                    } else {
                        btn.setStyle("-fx-background-color: #05386B; -fx-text-fill: white");
                        clickedWord.setStyle("-fx-background-color: #05386B; -fx-text-fill: white");
                    }
                    clickedWord = null;
                } else {
                    btn.setStyle("-fx-background-color: lightblue; -fx-text-fill: black");
                    clickedWord = btn;
                    clickedText = list.get(finalI);
                }
            });
        }
    }

    private void newGame() {
        pauseBtn.setVisible(true);
        list = game.generate();

        if (list.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Không đủ số lượng từ").show();
            return;
        }

        for (int i = 0; i < 2 * NUMBER_OF_QUESTIONS; i++) {
            Button btn = (Button) grid.getChildren().get(i);
            btn.setText(list.get(i));
            btn.setVisible(true);
            btn.setStyle("-fx-background-color: #05386B; -fx-text-fill: white");
        }
        isPaused = false;
        pauseBtn.setVisible(true);
        newGameBtn.setVisible(false);
        timeline.play();
    }

    @FXML
    void handlePauseBtn() {
        isPaused = !isPaused;
        if (isPaused) timeline.pause();
        else timeline.play();
        pauseBtn.setText(isPaused ? "Tiếp tục" : "Tạm dừng");
        grid.setVisible(! isPaused);
    }

    private void finishGame() {
        clickedWord = null;
        solvedQuestion = 0;
        time.set(0);
        timeline.stop();
        newGameBtn.setVisible(true);
        pauseBtn.setVisible(false);

          for (int i = 0; i < 2 * NUMBER_OF_QUESTIONS; i++) {
            grid.getChildren().get(i).setVisible(false);
        }
    }
}
