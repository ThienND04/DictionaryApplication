package com.example.dictionary.controller;

import com.example.dictionary.Application;
import com.example.dictionary.Data;
import com.example.dictionary.Word;
import com.example.dictionary.scene.SceneEnum;
import com.example.dictionary.stage.PrimaryWindow;
import com.example.dictionary.stage.WindowEnum;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebView;

import java.util.Optional;


public class HomeController {
    @FXML
    Button showDictionaryBtn;
    @FXML
    ListView<String> listView;
    @FXML
    WebView definitionView;
    @FXML
    Button deleteBtn;
    @FXML
    Button editBtn;
    @FXML
    TextField wordToFind;
    @FXML
    Button searchBtn;

    private static HomeController instance;

    public static HomeController getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {
        instance = this;
        initComponents();
        loadWordList();
    }

    private void initComponents() {

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
            editBtn.setVisible(false);
            deleteBtn.setVisible(false);
            EditorController.getInstance().loadData(listView.getSelectionModel().getSelectedItem(),
                    Data.getInstance().getData().get(listView.getSelectionModel().getSelectedItem()).getDef(), 0);
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
}