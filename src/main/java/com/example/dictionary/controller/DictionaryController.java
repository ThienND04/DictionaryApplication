package com.example.dictionary.controller;

import com.example.dictionary.word.Data;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

public class DictionaryController extends SuperController {
    @FXML
    private TextField wordToFind;
    @FXML
    private ListView<String> listView;
    @FXML
    private WebView definitionView;

    @Override
    protected void initComponents() {
        loadWordList();
    }

    private void handleSelectWord(String newValue) {
        if (newValue != null) {
            definitionView.getEngine().loadContent(Data.getInstance().getSubData().get(newValue).getDef());
        } else {
            definitionView.getEngine().loadContent("");
        }
    }

    @Override
    protected void initEvents() {
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> handleSelectWord(newValue));
        wordToFind.textProperty().addListener((observable, oldValue, newValue) -> loadWordList());
    }

    private void loadWordList() {
        listView.getItems().clear();
        listView.getItems().addAll(Data.getInstance().getSubTrie().getAllWordsStartWith(wordToFind.getText()));
    }
}
