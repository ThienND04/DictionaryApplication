package com.example.dictionary.controller;

import com.example.dictionary.game.Game1;
import com.example.dictionary.scene.SuperScene;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.web.WebView;

public class Game1Controller {
    private Game1 game1;

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
    public void initialize() {
        instance = this;
        this.initComponents();
    }

    private void initComponents() {
        newGameBtn.setOnAction(actionEvent -> newGame());
        skipBtn.setOnAction(actionEvent -> {
            game1.toNextQuestion();
            initComponents();
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
        if(! game1.isReady()) {
            new Alert(Alert.AlertType.WARNING, "Không đủ số lượng từ").show();
            return;
        }
        updateQuestion();
        checkBtn.setVisible(true);
        skipBtn.setVisible(true);
        quesLabel.setVisible(true);
        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            WebView btn = (WebView) ansSelections.getButtons().get(i);
            btn.setVisible(true);
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
            if(i == game1.getSelectedAns()) setContainerColor(btn, "red");
            if(i == game1.getAnswerIndex()) setContainerColor(btn, "lawngreen");
        }
        checkBtn.setText("Next question");
        checkBtn.setDisable(game1.isLastQuestion());
        checkBtn.setOnAction(actionEvent -> {
            game1.toNextQuestion();
            initComponents();
            updateQuestion();
        });
    }

    public void handleLogin() {
        game1 = new Game1();
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
