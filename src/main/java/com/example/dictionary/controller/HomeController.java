package com.example.dictionary.controller;

import com.example.dictionary.DataList;
import com.example.dictionary.Word;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;

public class HomeController {
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
    Button addBtn;
    @FXML
    public void initialize() {
        this.initComponents();
        this.loadWordList();
        addBtn.setOnAction(event -> {
            DataList.getInstance().addWordToList((String) listView.getSelectionModel().getSelectedItem());
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
        this.listView.getItems().addAll(DataList.getInstance().getData().keySet());
    }
}