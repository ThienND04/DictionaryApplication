package com.example.dictionary.controller;

import com.example.dictionary.DataList;
import com.example.dictionary.Word;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;

public class MyListController {
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
    public void initialize() {
        this.initComponents();
        this.loadWordList();
        rmBtn.setOnAction(event -> {
            String word = listView.getSelectionModel().getSelectedItem();
            DataList.getInstance().removeWordFromList(word);
            this.listView.getItems().remove(word);
        });
    }

    private void initComponents() {
        this.listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    Word selectedWord = DataList.getInstance().getData().get(newValue.trim());
                    String definition = selectedWord.getDef();
                    definitionView.getEngine().loadContent(definition, "text/html");
                }
        );
    }

    private void loadWordList() {
        this.listView.getItems().addAll(DataList.getInstance().getMyList());
    }
}