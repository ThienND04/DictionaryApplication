package com.example.dictionary.controller;

import com.example.dictionary.game.Game1;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.web.WebView;

public class Game1Controller {
    private final Game1 game1 = new Game1();

    @FXML
    WebView quesLabel;
    @FXML
    ButtonBar ansSelections;
    @FXML
    Button skipBtn;
    @FXML
    Button checkBtn;
    @FXML
    Button playAgainBtn;

    @FXML
    public void initialize() {
        this.initComponents();
        update();
    }

    @FXML
    public void handlePlayAgain() {
        game1.playAgain();
        update();
    }

    private void initComponents() {
        skipBtn.setOnAction(actionEvent -> {
            game1.toNextQuestion();
            update();
        });
        checkBtn.setText("Kiểm tra");
        checkBtn.setOnAction(actionEvent -> checkAns());

        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            final int curBtnIndex = i;
            Button btn = (Button) ansSelections.getButtons().get(i);
            btn.setDisable(false);
            btn.setOnAction(actionEvent -> {
                game1.selectAns(curBtnIndex);
                update();
            });
        }
    }

    void update() {
        skipBtn.setDisable(game1.isLastQuestion());
        checkBtn.setDisable(false);
        quesLabel.getEngine().loadContent(game1.getQuestion());
        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            Button btn = (Button) ansSelections.getButtons().get(i);
            if(i == game1.getSelectedAns()) btn.setStyle("-fx-background-color: lightblue");
            else btn.setStyle("-fx-background-color: gray");
            btn.setText(game1.getSelections().get(i));
        }
    }

    void checkAns() {
        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            Button btn = (Button) ansSelections.getButtons().get(i);
            btn.setOnAction(actionEvent -> {});
            if(i == game1.getSelectedAns()) btn.setStyle("-fx-background-color: red");
            if(i == game1.getAnswerIndex()) btn.setStyle("-fx-background-color: lawngreen");
            btn.setText(game1.getSelections().get(i));
        }
        checkBtn.setText("Next question");
        checkBtn.setDisable(game1.isLastQuestion());
        checkBtn.setOnAction(actionEvent -> {
            game1.toNextQuestion();
            initComponents();
            update();
        });
    }
}
