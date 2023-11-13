package com.example.dictionary.controller;

import com.example.dictionary.game.Game3;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

    public static Game3Controller getInstance() {
        return instance;
    }

    private static Game3Controller instance;

    @FXML
    public Label inform;
    @FXML
    private WebView meaning;
    @FXML
    private HBox guessWord;
    @FXML
    private HBox input;
    @FXML
    private Button newGameBtn;
    @FXML
    private Button stopGameBtn;
    @FXML
    ProgressBar bar;
    @FXML
    Button nextBtn;

    @FXML
    void initialize() {
        instance = this;
        timeline.setCycleCount(Animation.INDEFINITE);

        newGameBtn.setOnAction(event -> {
            if (game.isReady()) {
                newGame();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Không đủ số lượng từ");
            }
        });

        stopGameBtn.setOnAction(event -> finishGame());

        nextBtn.setOnAction(event -> nextQuestion());
    }

    private final AtomicLong time = new AtomicLong(0);

    private final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> time.incrementAndGet()));


    private final Game3 game = new Game3();
    private int solvedQuestion = 0;
    public static final int NUMBER_OF_QUESTIONS = 5;

    private ArrayList<String> data;

    private void newGame() {
        newGameBtn.setVisible(false);
        nextBtn.setVisible(false);
        data = game.generate();
        stopGameBtn.setVisible(true);

        if (data.size() == 0) {
            new Alert(Alert.AlertType.WARNING, "Không đủ số từ").show();
            return;
        }
        nextQuestion();
    }

    private void finishGame() {

        meaning.getEngine().loadContent("");
        inform.setText("");
        guessWord.getChildren().clear();
        input.getChildren().clear();

        bar.setProgress(0);
        solvedQuestion = 0;
        newGameBtn.setVisible(true);
        nextBtn.setVisible(false);
        stopGameBtn.setVisible(false);
        time.set(0);
    }

    private void nextQuestion() {
        meaning.getEngine().loadContent(data.get(2 * solvedQuestion + 1));
        inform.setText("");
        guessWord.getChildren().clear();
        input.getChildren().clear();

        List<String> list = Arrays.asList(data.get(2 * solvedQuestion).split(""));
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
                        bar.setProgress(solvedQuestion * 1.0 / NUMBER_OF_QUESTIONS);
                        if (solvedQuestion == NUMBER_OF_QUESTIONS)
                            finishGame();
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
