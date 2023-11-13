package com.example.dictionary.controller;

import com.example.dictionary.Application;
import com.example.dictionary.user.Data;
import com.example.dictionary.Word;
import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.stage.PrimaryWindow;
import com.example.dictionary.stage.WindowEnum;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import java.util.ArrayList;
import java.util.Optional;

public class HomeController  {
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

    private ArrayList<String> wordsEveryDay;

    public static HomeController getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {
        instance = this;
        initComponents();
    }

    private void loadRandomWords(int i) {
        randomWords.setText(wordsEveryDay.get(2*i));
        randomDefinitions.getEngine().loadContent(wordsEveryDay.get(2*i+1));
    }

    private int i = 0;

    private void initComponents() {
        rightBtn.setOnMouseClicked(event -> {
            loadRandomWords(++i);
            leftBtn.setVisible(true);
            if(i == WORDS_EVERY_DAY - 1)
                rightBtn.setVisible(false);
        });

        leftBtn.setOnMouseClicked(event -> {
            loadRandomWords(--i);
            rightBtn.setVisible(true);
            if(i == 0)
                leftBtn.setVisible(false);
        });

        listView.getSelectionModel().selectedItemProperty().addListener(
                (a, b, c) -> {
                    if (c != null) {
                        deleteBtn.setVisible(true);
                        editBtn.setVisible(true);
                        searchBtn.setVisible(true);
                        definitionView.getEngine().loadContent(Data.getInstance().getData().get(c).getDef());
                        searchBtn.setOnAction(event -> {
                            PrimaryWindow.getInstance().setSceneType(SceneEnum.TRANSLATE);
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
                Data.getInstance().removeWord(listView.getSelectionModel().getSelectedItem());
                loadWordList();
            }
        });

        editBtn.setOnAction(event -> {
            EditorController.getInstance().loadData(listView.getSelectionModel().getSelectedItem(),
                    Data.getInstance().getData().get(listView.getSelectionModel().getSelectedItem()).getDef(), 0);
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
        Data.getInstance().removeWord(oldWord);
        Data.getInstance().addWord(new Word(newWord, newDefinition));
        loadWordList();
        listView.getSelectionModel().select(newWord);
    }

    public void loadWordList() {
        listView.getItems().clear();
        listView.getItems().addAll(Data.getInstance().getTrie().getAllWordsStartWith(wordToFind.getText()));
    }

    public void loadData() {
        wordsEveryDay = Data.getInstance().getRandomWordsByDay(WORDS_EVERY_DAY);
        loadRandomWords(0);
        loadWordList();
    }
}