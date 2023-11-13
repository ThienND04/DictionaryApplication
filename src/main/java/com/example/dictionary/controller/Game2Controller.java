package com.example.dictionary.controller;

import com.example.dictionary.game.Game2;
import com.example.dictionary.scene.SuperScene;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Worker;
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
    @FXML
    Button pauseBtn;
    private final Game2 game = new Game2();
    private final int n = 10;

    private final AtomicLong time = new AtomicLong(0);
    private final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> timeLabel.setText(String.valueOf((double) time.incrementAndGet() / 10))));
    private int solvedQuestion = 0;
    private boolean isPaused;

    @FXML
    void initialize() {
        initComponents();
        isPaused = true;
    }

    private WebView clickedWord;
    private String clickedText;
    private ArrayList<String> list = new ArrayList<>();

    private void initComponents() {
        pauseBtn.setVisible(false);
        newGame.setOnAction(event -> {
            newGame();
            pauseBtn.setVisible(true);
        });
        timeline.setCycleCount(Animation.INDEFINITE);

        for (int i = 0; i < grid.getChildren().size(); i++) {
            WebView temp = (WebView) grid.getChildren().get(i);
            temp.setVisible(false);
            int finalI = i;

            // set default color
            temp.getEngine().getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    setWebViewStyle(temp, "backgroundColor = 'gray'");
                }
            });

            temp.setOnMouseClicked(event -> {
                if (clickedWord != null) {
                    if (game.checkAnswer(clickedText, list.get(finalI))) {
                        clickedWord.setVisible(false);
                        temp.setVisible(false);
                        this.solvedQuestion++;
                        if (this.solvedQuestion == n)
                            finishGame();
                    }
                    setWebViewStyle(clickedWord, "backgroundColor = 'gray'");
                    setWebViewStyle(temp, "backgroundColor = 'gray'");
                    clickedWord = null;
                } else {
                    setWebViewStyle(temp, "backgroundColor = 'lightblue'");
                    clickedWord = temp;
                    clickedText = list.get(finalI);
                }
            });
        }
    }

    private void newGame() {
        isPaused = false;
        list = game.generate(n);
        for (int i = 0; i < grid.getChildren().size(); i++) {
            WebView temp = (WebView) grid.getChildren().get(i);
            if (i < 2 * n) {
                loadContentWithStyle(temp, list.get(i));
                temp.setVisible(true);

                // set default color
                temp.getEngine().getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
                    if (newState == Worker.State.SUCCEEDED) {
                        setWebViewStyle(temp, "backgroundColor = 'gray'");
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

    @FXML
    void handlePauseBtn() {
        isPaused = !isPaused;
        if(isPaused) timeline.pause();
        else timeline.play();
        pauseBtn.setText(isPaused ? "Tiếp tục" : "Tạm dừng");
        for (int i = 0; i < grid.getChildren().size(); i++) {
            WebView temp = (WebView) grid.getChildren().get(i);
            if (i < 2 * n) {
                temp.setVisible(! isPaused);
            }
        }
    }

    private void finishGame() {
        timeline.stop();
        newGame.setVisible(true);
        pauseBtn.setVisible(false);
    }

    private void setWebViewStyle(WebView webView, String style) {
        webView.getEngine().executeScript(String.format("document.body.style.%s", style));
    }

    /**
     * Note: after loading content, webview need to load.
     * @param webView webview
     * @param content web's content
     */
    private void loadContentWithStyle(WebView webView, String content) {
        String css = SuperScene.class.getResource("common.css").toExternalForm();
        webView.getEngine().setUserStyleSheetLocation(css);
        webView.getEngine().loadContent(String.format(
                "<div class = 'container noselect'> %s </div>", content));
    }
}
