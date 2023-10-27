package com.example.dictionary.controller;

import com.example.dictionary.DataList;
import com.example.dictionary.Word;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HomeController {
    @FXML
    Button gameNav;
    @FXML
    Button homeNav;    
    @FXML
    ListView<String> listView;
    @FXML
    TextArea definitionView;
    @FXML
    Button deleteBtn;
    @FXML
    Button editBtn;
    @FXML
    Button saveBtn;
    @FXML
    TextField wordToFind;

    private static HomeController instance;
    public static HomeController getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {
        this.initComponents();
        this.loadWordList();
        instance  = this;
    }

    private void initComponents() {
        this.listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    this.saveBtn.setVisible(false);
                    this.definitionView.setEditable(false);
                    if (newValue != null) {
                        this.deleteBtn.setVisible(true);                        
                        this.editBtn.setVisible(true);
                        Word selectedWord = DataList.getInstance().getData().get(newValue);
                        String definition = selectedWord.getDef();
                        definitionView.setText(definition);
                    } else {
                        this.deleteBtn.setVisible(false);
                        this.editBtn.setVisible(false);
                        definitionView.setText("");
                    }
                }
        );

        this.deleteBtn.setOnAction(event -> {
            DataList.getInstance().removeWord((String) listView.getSelectionModel().getSelectedItem());
            loadWordList();
        });

        this.editBtn.setOnAction(event -> {
            this.definitionView.setEditable(true);
            this.definitionView.requestFocus();
            this.saveBtn.setVisible(true);
            this.editBtn.setVisible(false);
            this.deleteBtn.setVisible(false);
        });

        this.saveBtn.setOnAction(event -> {
            if(!this.definitionView.getText().trim().equals("")) {
                this.definitionView.setEditable(false);
                this.saveBtn.setVisible(false);
                this.editBtn.setVisible(true);
                this.deleteBtn.setVisible(true);
                DataList.getInstance().addWord(new Word(listView.getSelectionModel().getSelectedItem(), this.definitionView.getText()));
            }
        });
        
        this.wordToFind.textProperty().addListener((observable, oldValue, newValue) -> loadWordList());
    }

    public void loadWordList() {
        this.listView.getItems().clear();
        this.listView.getItems().addAll(DataList.getInstance().getTrie().getAllWordsStartWith(wordToFind.getText()));
    }
}