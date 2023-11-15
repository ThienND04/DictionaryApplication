package com.example.dictionary.controller;

import com.example.dictionary.game.Game1;
import com.example.dictionary.scene.SuperScene;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebView;

public class Game1Controller {
    private final Game1 game1 = new Game1();

    public static Game1Controller getInstance() {
        return instance;
    }
    private static Game1Controller instance;
    private final String BLUE_2 = "#05386B";

    @FXML
    WebView quesLabel;
    @FXML
    ButtonBar ansSelections;
    @FXML
    Button skipBtn;
    @FXML
    Button checkBtn;
    @FXML
    Button newGameBtn;
    @FXML
    ProgressBar progressBar;
    @FXML
    Label solved;
    @FXML
    Label fault;

    @FXML
    public void initialize() {
        instance = this;
        this.initComponents();
    }

    private void initComponents() {
        newGameBtn.setVisible(true);
        solved.setVisible(false);
        fault.setVisible(false);
        checkBtn.setVisible(false);
        quesLabel.setVisible(false);

        newGameBtn.setOnAction(actionEvent -> newGame());
        skipBtn.setOnAction(actionEvent -> {
            game1.toNextQuestion();
            updateQuestion();
        });
        checkBtn.setText("Kiểm tra");
        checkBtn.setOnAction(actionEvent -> checkAns());
        quesLabel.getEngine().getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                setContainerColor(quesLabel, BLUE_2);
            }
        });
        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            final int curBtnIndex = i;
            WebView btn = (WebView) ansSelections.getButtons().get(i);
            btn.setVisible(false);

            btn.getEngine().getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    setContainerColor(btn, BLUE_2);
                }
            });
            btn.setOnMouseClicked(actionEvent -> {
                if(game1.getSelectedAns() >= 0) setContainerColor(getSelectedAns(), BLUE_2);
                game1.selectAns(curBtnIndex);
                setContainerColor(getSelectedAns(), "lightblue");
            });
        }
    }

    private void newGame() {
        game1.init();
        progressBar.setProgress(0);
        if(! game1.isReady()) {
            new Alert(Alert.AlertType.WARNING, "Không đủ số lượng từ").show();
            return;
        }
        newGameBtn.setVisible(false);
        updateQuestion();
        updateStatus();
        checkBtn.setVisible(true);
        skipBtn.setVisible(true);
        quesLabel.setVisible(true);
        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            WebView btn = (WebView) ansSelections.getButtons().get(i);
            btn.setVisible(true);
        }
    }

    private void finish() {
        initComponents();
    }

    private void updateQuestion() {
        skipBtn.setVisible(game1.questionRemain() > 1);
        checkBtn.setDisable(false);
        loadContentWithStyle(quesLabel, game1.getQuestion());
        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            WebView btn = (WebView) ansSelections.getButtons().get(i);
            loadContentWithStyle(btn, game1.getSelections().get(i));
            btn.setDisable(false);
        }
    }

    private void updateStatus() {
        progressBar.setVisible(true);
        progressBar.setProgress(game1.getProgress());
        solved.setVisible(true);
        fault.setVisible(true);
        solved.setText(String.format("Solved: %02d / %02d", game1.getSolved() , Game1.NUM_QUESTION));
        fault.setText(String.format("Solved: %02d / %02d", game1.getFault(), Game1.MAX_FAULT));
    }

    private void checkAns() {
        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            WebView btn = (WebView) ansSelections.getButtons().get(i);
            btn.setDisable(true);
            if(i == game1.getSelectedAns()) setContainerColor(btn, "red");
            if(i == game1.getAnswerIndex()) setContainerColor(btn, "lawngreen");
        }
        game1.submit();
        updateStatus();
        skipBtn.setVisible(false);
        checkBtn.setText("Tiếp tục");
        if(game1.checkFinish()) {
            checkBtn.setOnAction(actionEvent -> finish());
        } else {
            checkBtn.setOnAction(actionEvent -> {
                game1.toNextQuestion();
                checkBtn.setText("Kiểm tra");
                checkBtn.setOnAction(actionEvent1 -> checkAns());
                updateQuestion();
            });
        }
    }

    private void setContainerColor(WebView webView, String color) {
        webView.getEngine().executeScript(String.format(
                "document.getElementsByClassName('container')[0].style.backgroundColor = '%s'", color));
        webView.getEngine().executeScript(String.format(
                "document.getElementsByClassName('container')[0].style.borderColor = '%s'", color));
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
