package com.example.dictionary.controller;

import com.example.dictionary.game.Game3;
import com.example.dictionary.scene.SuperScene;
import com.example.dictionary.user.User;
import com.example.dictionary.user.UserManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Game3Controller {
    @FXML
    public Label inform;
    @FXML
    private Label meaning;
    @FXML
    private HBox guessWord;
    @FXML
    private HBox input;
    @FXML
    private Button newGame;
    @FXML
    private Button pauseBtn;
    @FXML
    ProgressBar bar;
    @FXML
    Button nextBtn;
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
    private boolean isPaused = false;

    private final AtomicLong time = new AtomicLong(0);
    private final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1),
            event -> timeLabel.setText(String.valueOf((double) time.incrementAndGet() / 10))));

    @FXML
    void initialize() {
        instance = this;
        timeline.setCycleCount(Animation.INDEFINITE);
        meaning.setVisible(false);
        newGame.setOnAction(event -> newGame());
        nextBtn.setOnAction(event -> nextQuestion());
        pauseBtn.setOnAction(actionEvent -> {
            isPaused = ! isPaused;
            pauseBtn.setText(isPaused ? "Tiếp tục" : "Dừng");
            guessWord.setVisible(! isPaused);
            input.setVisible(! isPaused);
            nextBtn.setVisible(! isPaused);
            if(isPaused) timeline.pause();
            else timeline.play();
        });
        ObservableList<User> players = FXCollections.observableArrayList(UserManager.getInstance().getUsers()).
                sorted(Comparator.comparingDouble(User::getBestTime3));
        sttCol.setCellValueFactory(collumn -> new ReadOnlyObjectWrapper<>(topPlayer.getItems().indexOf(collumn.getValue()) + 1));
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("bestTime3"));
        topPlayer.getItems().clear();
        topPlayer.setItems(FXCollections.observableArrayList(
                players.stream().filter(player -> players.indexOf(player) < MAX_PLAYER_SHOW).collect(Collectors.toList())));
    }
    private final Game3 game = new Game3();

    private void newGame() {
        game.newGame();
        if(! game.isReady()) {
            new Alert(Alert.AlertType.WARNING, "Không đủ số lượng từ").show();
            return;
        }
        newGame.setVisible(false);
        meaning.setVisible(true);
        timeline.play();
        timeLabel.setVisible(true);
        nextQuestion();
        bar.setProgress(0);
        isPaused = false;
        pauseBtn.setVisible(true);
        pauseBtn.setText("Dừng");
        nextBtn.setVisible(true);
    }

    private void finish() {
        newGame.setVisible(true);
        pauseBtn.setVisible(false);
        timeline.pause();
        double playedTime = 1.0 * time.get() / 10;
        if(playedTime < UserManager.getInstance().getCurrentUser().getBestTime3()) {
            UserManager.getInstance().getCurrentUser().setBestTime3(playedTime);
        }
        time.set(0);
        topPlayer.refresh();
    }

    private void nextQuestion() {
        game.nextQuestion();
        meaning.setText(game.getMeaning());
        inform.setText("");
        guessWord.getChildren().clear();
        input.getChildren().clear();
        List<String> list = Arrays.asList(game.getGuessWord().split(""));
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

                if (guessWord.getChildren().size() == game.getGuessWord().length()) {
                    StringBuilder playerGuess = new StringBuilder();
                    guessWord.getChildren().stream().map(button -> ((Button)button).getText()).
                            reduce(playerGuess, (StringBuilder::append), StringBuilder::append);
                    if (game.isGuessedWord(playerGuess.toString())) {
                        game.increaseSolvedQuestion();
                        bar.setProgress(game.getProgress());
                        if (game.isFinished())
                            finish();
                        else {
                            nextQuestion();
                        }
                    } else {
                        inform.setText("Wrong");
                    }
                }
            });
            input.getChildren().add(btn);
        }
    }

    private static final int MAX_PLAYER_SHOW = 10;
    private static Game3Controller instance;
    public static Game3Controller getInstance() {
        return instance;
    }
    public static void setInstance(Game3Controller instance) {
        Game3Controller.instance = instance;
    }
}