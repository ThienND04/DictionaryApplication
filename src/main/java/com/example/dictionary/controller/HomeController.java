package com.example.dictionary.controller;

import com.example.dictionary.Application;
import com.example.dictionary.api.TextToSpeech;
import com.example.dictionary.word.Data;
import com.example.dictionary.word.Word;
import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.stage.PrimaryWindow;
import com.example.dictionary.stage.WindowEnum;
import com.example.dictionary.user.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;

import java.util.ArrayList;
import java.util.Optional;

public class HomeController {
    @FXML
    Button speakBtn;
    @FXML
    ImageView speakImg;
    @FXML
    Circle userNav;
    @FXML
    Label translateNav;
    @FXML
    Label gameNav;
    @FXML
    WebView randomDefinitions;
    @FXML
    Label leftBtn;
    @FXML
    Label rightBtn;
    @FXML
    Label randomWords;
    @FXML
    private Button addBtn;
    @FXML
    private Button showDictionaryBtn;
    @FXML
    private ListView<String> listView;
    @FXML
    private WebView definitionView;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button editBtn;
    @FXML
    private TextField wordToFind;
    @FXML
    private Button searchBtn;

    private static HomeController instance;

    public static final int WORDS_EVERY_DAY = 5;

    private final ArrayList<Word> wordsEveryDay = Data.getInstance().getRandomWordsByDay(WORDS_EVERY_DAY);
    ;

    public static HomeController getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {
        instance = this;
        translateNav.setOnMouseClicked(e -> PrimaryWindow.getInstance().changeScene(SceneEnum.TRANSLATE));
        gameNav.setOnMouseClicked(e -> PrimaryWindow.getInstance().changeScene(SceneEnum.GAME));
        userNav.setOnMouseClicked(e -> PrimaryWindow.getInstance().changeScene(SceneEnum.USER));
        speakImg.setImage(new Image(getClass().getResourceAsStream("speaker.png")));
        speakBtn.setOnAction(e -> TextToSpeech.textToSpeech(listView.getSelectionModel().getSelectedItem()));

        initComponents();
    }

    private void loadRandomWords(int i) {
        randomWords.setText(wordsEveryDay.get(i).getWord());
        randomDefinitions.getEngine().loadContent(wordsEveryDay.get(i).getDef());
    }

    private int i = 0;

    private void initComponents() {
        loadRandomWords(0);

        rightBtn.setOnMouseClicked(event -> {
            loadRandomWords(++i);
            leftBtn.setVisible(true);
            if (i == WORDS_EVERY_DAY - 1)
                rightBtn.setVisible(false);
        });

        leftBtn.setOnMouseClicked(event -> {
            loadRandomWords(--i);
            rightBtn.setVisible(true);
            if (i == 0)
                leftBtn.setVisible(false);
        });

        randomWords.setOnMouseClicked(e -> TextToSpeech.textToSpeech(randomWords.getText()));

        listView.getSelectionModel().selectedItemProperty().addListener(
                (a, b, c) -> {
                    if (c != null) {
                        deleteBtn.setVisible(true);
                        editBtn.setVisible(true);
                        searchBtn.setVisible(true);
                        definitionView.getEngine().loadContent(UserManager.getInstance().getCurrentUser().getWords().get(c).getDef());
                        searchBtn.setOnAction(event -> {
                            PrimaryWindow.getInstance().changeScene(SceneEnum.TRANSLATE);
                            TranslateController.getInstance().find(c);
                        });
                    } else {
                        deleteBtn.setVisible(false);
                        editBtn.setVisible(false);
                        searchBtn.setVisible(false);
                        definitionView.getEngine().loadContent("");
                    }
                }
        );

        deleteBtn.setOnAction(event -> {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có muốn xóa từ này không?");
            Optional<ButtonType> result = a.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                UserManager.getInstance().getCurrentUser().removeWord(listView.getSelectionModel().getSelectedItem());
                loadWordList();
            }
        });

        editBtn.setOnAction(event -> {
            EditorController.getInstance().loadData(listView.getSelectionModel().getSelectedItem(),
                    UserManager.getInstance().getCurrentUser().getWords().get(listView.getSelectionModel().getSelectedItem()).getDef(), 0);
            Application.getInstance().showWindow(WindowEnum.EDITOR);
        });

        addBtn.setOnAction(event -> {
            EditorController.getInstance().loadData("", "", 0);
            Application.getInstance().showWindow(WindowEnum.EDITOR);
        });

        wordToFind.textProperty().addListener((a, b, c) -> loadWordList());

        showDictionaryBtn.setOnAction(event -> Application.getInstance().showWindow(WindowEnum.DICTIONARY));
    }

    public void handleEdited(String newWord, String newDefinition, String oldWord) {
        UserManager.getInstance().getCurrentUser().removeWord(oldWord);
        UserManager.getInstance().getCurrentUser().addWord(new Word(newWord, newDefinition));
        loadWordList();
        listView.getSelectionModel().select(newWord);
    }

    public void loadWordList() {
        listView.getItems().clear();
        listView.getItems().addAll(UserManager.getInstance().getCurrentUser().getTrie().getAllWordsStartWith(wordToFind.getText()));
    }

    public void handleLogin() {
        loadWordList();
        initUserImage();
    }

    public void initUserImage() {
        ImagePattern imagePattern = new ImagePattern(UserManager.getInstance().getCurrentUser().getImage());
        userNav.setFill(imagePattern);
    }
}