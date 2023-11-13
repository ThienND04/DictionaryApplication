package com.example.dictionary.controller;

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

    public static final int NUMBER_OF_QUESTIONS = 10;
    private static Game2Controller instance;
    private final Game2 game = new Game2();
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
    private WebView clickedWord;
    private String clickedText;

    public static Game2Controller getInstance() {
        return instance;
    }

    @FXML
    void initialize() {
        instance = this;
        newGameBtn.setOnAction(event -> newGame());
        pauseBtn.setOnAction(event -> handlePauseBtn());
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void newGame() {
        pauseBtn.setVisible(true);
        ArrayList<String> list = game.generate();

        if (list.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Không đủ số lượng từ").show();
            return;
        }

        for (int i = 0; i < 2 * NUMBER_OF_QUESTIONS; i++) {
            WebView temp = (WebView) grid.getChildren().get(i);
            loadContentWithStyle(temp, list.get(i));
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
                        setWebViewStyle(clickedWord, "backgroundColor = 'white'");
                        setWebViewStyle(clickedWord, "color = 'black'");
                    }
                    clickedWord = null;
                } else {
                    setWebViewStyle(temp, "backgroundColor = '#05386B'");
                    setWebViewStyle(temp,"color = '#EDF5E1'");
                    clickedWord = temp;
                    clickedText = list.get(finalI);
                }
            });
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
        for (int i = 0; i < grid.getChildren().size(); i++) {
            WebView temp = (WebView) grid.getChildren().get(i);
            if (i < 2 * NUMBER_OF_QUESTIONS) {
                temp.setDisable(isPaused);
            }
        }
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

    private void setWebViewStyle(WebView webView, String style) {
        webView.getEngine().executeScript(String.format("document.body.style.%s", style));
    }

    /**
     * Note: after loading content, webview need to load.
     *
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
