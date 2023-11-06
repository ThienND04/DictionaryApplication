package com.example.dictionary.controller;

import com.example.dictionary.Data;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

public class DictionaryController {
    @FXML
    TextField wordToFind;
    @FXML
    ListView listView;
    @FXML
    WebView definitionView;

    @FXML
    public void initialize() {
        loadWordList();
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        definitionView.getEngine().loadContent(Data.getInstance().getSubData().get(newValue).getDef());
                    } else {
                        definitionView.getEngine().loadContent("");
                    }
                }
        );

        wordToFind.textProperty().addListener((observable, oldValue, newValue) -> loadWordList());
    }

    public void loadWordList() {
        listView.getItems().clear();
        listView.getItems().addAll(Data.getInstance().getSubTrie().getAllWordsStartWith(wordToFind.getText()));
    }
}