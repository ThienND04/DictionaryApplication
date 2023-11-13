package com.example.dictionary.controller;

import com.example.dictionary.Application;
import com.example.dictionary.game.Game1;
import com.example.dictionary.scene.SuperScene;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.web.WebView;

public class Game1Controller {
    private Game1 game1;

    public static Game1Controller getInstance() {
        return instance;
    }
    private static Game1Controller instance;

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
        instance = this;
        this.initComponents();
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
        });
        checkBtn.setText("Kiểm tra");
        checkBtn.setOnAction(actionEvent -> checkAns());

        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            final int curBtnIndex = i;
            WebView btn = (WebView) ansSelections.getButtons().get(i);

            btn.getEngine().getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    setWebViewStyle(btn, "backgroundColor = 'gray'");
                }
            });

            btn.setOnMouseClicked(actionEvent -> {
                if(game1.getSelectedAns() >= 0) setWebViewStyle(getSelectedAns(), "backgroundColor = 'gray'");
                game1.selectAns(curBtnIndex);
                setWebViewStyle(getSelectedAns(), "backgroundColor = 'lightblue'");
            });
        }
    }

    private void updateQuestion() {
        skipBtn.setDisable(game1.isLastQuestion());
        checkBtn.setDisable(false);
        loadContentWithStyle(quesLabel, game1.getQuestion());
        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            WebView btn = (WebView) ansSelections.getButtons().get(i);
            loadContentWithStyle(btn, game1.getSelections().get(i));
        }
    }

    private void checkAns() {
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

    public void loadData() {
        game1 = new Game1();
        updateQuestion();
    }

    private void setWebViewStyle(WebView webView, String style) {
        webView.getEngine().executeScript(String.format("document.body.style.%s", style));
    }

    private void loadContentWithStyle(WebView webView, String content) {
        String css = SuperScene.class.getResource("common.css").toExternalForm();
        webView.getEngine().setUserStyleSheetLocation(css);
        webView.getEngine().loadContent(String.format(
                "<div class = 'container noselect'> %s </div>", content));
    }

    private WebView getSelectedAns() {
        return (WebView) ansSelections.getButtons().get(game1.getSelectedAns());
    }
}
