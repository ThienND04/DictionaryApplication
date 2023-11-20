package com.example.dictionary.controller;

import com.example.dictionary.game.Game1;
import com.example.dictionary.game.GameInfo;
import com.example.dictionary.game.GameManager;
import com.example.dictionary.scene.SuperScene;
import com.example.dictionary.user.User;
import com.example.dictionary.user.UserManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Game1Controller {
    private final Game1 game1 = new Game1();

    public static Game1Controller getInstance() {
        return instance;
    }
    private static Game1Controller instance;
    private final String BLUE_2 = "#05386B";
    private final int MAX_PLAYER_SHOW = 5;

    @FXML
    Label quesLabel;
    @FXML
    ButtonBar ansSelections;
    @FXML
    Button skipBtn;
    @FXML
    Button checkBtn;
    @FXML
    Button newGameBtn;
    @FXML
    Button hintBtn;
    @FXML
    ProgressBar progressBar;
    @FXML
    Label solved;
    @FXML
    Label fault;
    @FXML
    Label timeLabel;
    @FXML
    TableView<User> topPlayer;
    @FXML
    TableColumn<User, Integer> sttCol;
    @FXML
    TableColumn<User, String> userCol;
    @FXML
    TableColumn<User, Double> timeCol;
    private int cntHint = 0;
    private final AtomicLong time = new AtomicLong(0);
    private final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1),
            event -> timeLabel.setText(String.valueOf((double) time.incrementAndGet() / 10))));

    @FXML
    public void initialize() {
        instance = this;
        this.initComponents();
    }

    private void initComponents() {
        newGameBtn.setVisible(true);
        hintBtn.setVisible(false);
        solved.setVisible(false);
        fault.setVisible(false);
        checkBtn.setVisible(false);
        quesLabel.setVisible(false);
        timeLabel.setStyle("-fx-text-fill: white");
        timeline.setCycleCount(Animation.INDEFINITE);

        newGameBtn.setOnAction(actionEvent -> newGame());
        hintBtn.setOnAction(actionEvent -> hint());
        skipBtn.setOnAction(actionEvent -> {
            game1.toNextQuestion();
            updateQuestion();
        });
        checkBtn.setText("Kiểm tra");
        checkBtn.setOnAction(actionEvent -> checkAns());
        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            final int finalI = i;
            Button btn = (Button) ansSelections.getButtons().get(i);
            btn.setVisible(false);
            btn.setOnMouseEntered(mouseEvent -> {
                btn.setEffect(new Lighting());
            });
            btn.setOnMouseExited(mouseEvent -> {
                btn.setEffect(null);
            });
            btn.setOnAction(actionEvent -> {
                if(game1.getSelectedAns() >= 0) {
                    getSelectedAns().setStyle("-fx-text-fill: white");
                    getSelectedAns().setStyle("-fx-background-color: " + BLUE_2);
                }
                game1.selectAns(finalI);
                btn.setStyle("-fx-text-fill: black; -fx-background-color: lightblue");
            });
        }
        sttCol.setCellValueFactory(collumn -> new ReadOnlyObjectWrapper<>(topPlayer.getItems().indexOf(collumn.getValue()) + 1));
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        timeCol.setCellValueFactory(collumn -> new ReadOnlyObjectWrapper<>(
                GameManager.getInstance().getBestTime(Game1.GAME_ID, collumn.getValue().getId())));
        updateBXH();
    }

    private void newGame() {
        game1.init();
        cntHint = 0;
        progressBar.setProgress(0);
        if(! game1.isReady()) {
            new Alert(Alert.AlertType.WARNING, "Không đủ số lượng từ").show();
            return;
        }
        newGameBtn.setVisible(false);
        hintBtn.setVisible(true);
        updateQuestion();
        updateStatus();
        checkBtn.setVisible(true);
        skipBtn.setVisible(true);
        quesLabel.setVisible(true);
        timeLabel.setVisible(true);
        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            Button btn = (Button) ansSelections.getButtons().get(i);
            btn.setVisible(true);
        }
        timeline.play();
    }

    private void finish() {
        timeline.stop();
        double playedTime = 1.0 * time.get() / 10;
        if(game1.questionRemain() == 0) {
            GameManager.getInstance().getPlayersHistory().add(new GameInfo(Game1.GAME_ID, playedTime, GameInfo.Status.WIN));
        } else {
            GameManager.getInstance().getPlayersHistory().add(new GameInfo(Game1.GAME_ID, playedTime, GameInfo.Status.LOSE));
        }
        newGameBtn.setVisible(true);
        solved.setVisible(false);
        fault.setVisible(false);
        checkBtn.setVisible(false);
        quesLabel.setVisible(false);
        hintBtn.setVisible(false);
        time.set(0);
        checkBtn.setOnAction(actionEvent -> checkAns());
        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            Button btn = (Button) ansSelections.getButtons().get(i);
            btn.setVisible(false);
        }
        updateBXH();
    }

    private void updateQuestion() {
        skipBtn.setVisible(game1.questionRemain() > 1);
        checkBtn.setDisable(false);
        quesLabel.setText(game1.getQuestion());
        for(int i = 0; i < ansSelections.getButtons().size(); i ++) {
            Button btn = (Button) ansSelections.getButtons().get(i);
            btn.setText(game1.getSelections().get(i));
            btn.setStyle("-fx-background-color: " + BLUE_2);
            btn.setDisable(false);
            btn.setVisible(true);
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
            Button btn = (Button) ansSelections.getButtons().get(i);
            btn.setDisable(true);
            btn.setOpacity(1);
            if(i == game1.getSelectedAns()) btn.setStyle("-fx-background-color: red");
            if(i == game1.getAnswerIndex()) btn.setStyle("-fx-background-color: lawngreen");
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

    private void hint() {
        ArrayList<Integer> slt = new ArrayList<>(Arrays.asList(0, 1, 2));
        slt.removeIf(i ->
            ! ansSelections.getButtons().get(i).isVisible()
            || i == game1.getAnswerIndex());
        if(slt.isEmpty() || cntHint == Game1.MAX_HINT) {
            new Alert(Alert.AlertType.WARNING, "Không thể dùng ").show();
        } else {
            Random rd = new Random();
            int i = slt.get(rd.nextInt(slt.size()));
            ansSelections.getButtons().get(i).setVisible(false);
            cntHint ++;
        }
    }

    public void updateBXH() {
        ObservableList<User> players = FXCollections.observableArrayList(UserManager.getInstance().getUsers()).
                filtered(user -> GameManager.getInstance().getPlayersHistory().stream().
                        anyMatch(gameInfo -> gameInfo.getPlayerId() == user.getId() && gameInfo.getGameId() == Game1.GAME_ID)).
                sorted(Comparator.comparingDouble(u -> GameManager.getInstance().getBestTime(Game1.GAME_ID, u.getId())));
        topPlayer.getItems().clear();
        topPlayer.setItems(FXCollections.observableArrayList(
                players.stream().filter(player -> players.indexOf(player) < MAX_PLAYER_SHOW).collect(Collectors.toList())));
    }

    private Button getSelectedAns() {
        return (Button) ansSelections.getButtons().get(game1.getSelectedAns());
    }
}
