package com.example.dictionary.controller;

import com.example.dictionary.DataList;
import com.example.dictionary.Word;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

public class MyListController {
    private static MyListController instance;

    public static MyListController getInstance() {
        return instance;
    }

    @FXML
    Button gameNav;
    @FXML
    Button homeNav;
    @FXML
    Button myListNav;
    @FXML
    ListView<String> listView;
    @FXML
    WebView definitionView;
    @FXML
    Button rmBtn;
    @FXML
    TextField wordToFind;

    @FXML
    public void initialize() {
        instance = this;
        this.initComponents();
        this.loadWordList();
    }

    private void initComponents() {
        this.listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        this.rmBtn.setVisible(true);
                        Word selectedWord = DataList.getInstance().getData().get(newValue.trim());
                        String definition = selectedWord.getDef();
                        definitionView.getEngine().loadContent(definition, "text/html");
                    } else {
                        this.rmBtn.setVisible(false);
                        definitionView.getEngine().loadContent("", "text/html");
                    }
                }
        );
        this.rmBtn.setOnAction(event -> {
            String word = listView.getSelectionModel().getSelectedItem();
            DataList.getInstance().removeWordFromList(word);
            loadWordList();
        });
        this.wordToFind.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadWordList();
            }
        });
    }

    public void loadWordList() {
        this.listView.getItems().clear();
        this.listView.getItems().addAll(DataList.getInstance().getMyList().allWordsStartWith(wordToFind.getText()));
    }
}