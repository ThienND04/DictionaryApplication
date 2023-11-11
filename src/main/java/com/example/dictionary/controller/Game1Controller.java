package com.example.dictionary.controller;

import com.example.dictionary.game.Game1;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.HBox;
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
        updateQuestion();
    }

    @FXML
    public void handlePlayAgain() {
        game1.playAgain();
        updateQuestion();
    }

    private void initComponents() {
        skipBtn.setOnAction(actionEvent -> {
            game1.toNextQuestion();
            initComponents();
            updateQuestion();
        });
        checkBtn.setText("Kiểm tra");
        checkBtn.setOnAction(actionEvent -> checkAns());

        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            final int curBtnIndex = i;
            WebView btn = (WebView) ansSelections.getButtons().get(i);
            System.out.println(btn.getPrefHeight());

            btn.getEngine().getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    setWebViewStyle(btn, "backgroundColor = 'gray'");
                    setWebViewStyle(btn, "textAlign = 'center'");
                }
            });

            btn.setOnMouseClicked(actionEvent -> {
                if(game1.getSelectedAns() >= 0) setWebViewStyle(getSelectedAns(), "backgroundColor = 'gray'");
                game1.selectAns(curBtnIndex);
                setWebViewStyle(getSelectedAns(), "backgroundColor = 'red'");
            });
        }
    }

    void updateQuestion() {
        skipBtn.setDisable(game1.isLastQuestion());
        checkBtn.setDisable(false);
        loadContentWithStyle(quesLabel, game1.getQuestion());
        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            WebView btn = (WebView) ansSelections.getButtons().get(i);
            loadContentWithStyle(btn, game1.getSelections().get(i));
        }
    }

    void checkAns() {
        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            WebView btn = (WebView) ansSelections.getButtons().get(i);
            btn.setOnMouseClicked(actionEvent -> {});
            if(i == game1.getSelectedAns()) setWebViewStyle(btn, "backgroundColor = 'red'");
            if(i == game1.getAnswerIndex()) setWebViewStyle(btn, "backgroundColor = 'lawngreen'");
        }
        checkBtn.setText("Next question");
        checkBtn.setDisable(game1.isLastQuestion());
        checkBtn.setOnAction(actionEvent -> {
            game1.toNextQuestion();
            initComponents();
            updateQuestion();
        });
    }

    void setWebViewStyle(WebView webView, String style) {
        webView.getEngine().executeScript(String.format("document.body.style.%s", style));
    }

    void loadContentWithStyle(WebView webView, String content) {
        System.out.println(content);
        System.out.println(webView.getPrefHeight());
        String containerStyle = String.format(".container {\n" +
                "    display: grid;\n" +
                "    align-items: center;\n" +
                "    text-align: center;\n" +
                "    height: %dpx;\n" +
                "}", (int) Math.round(webView.getPrefHeight()) - 20);
        webView.getEngine().loadContent(String.format("<style> %s </style> " +
                "<div class = 'container'> %s </div>", containerStyle, content));
    }

    WebView getSelectedAns() {
        return (WebView) ansSelections.getButtons().get(game1.getSelectedAns());
    }
}
