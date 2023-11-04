package com.example.dictionary.controller;

import com.example.dictionary.DataList;
import com.example.dictionary.Word;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

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
        initComponents();
        loadWordList();
        instance = this;
    }

    private void initComponents() {
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    saveBtn.setVisible(false);
                    definitionView.setEditable(false);
                    if (newValue != null) {
                        deleteBtn.setVisible(true);
                        editBtn.setVisible(true);
                        definitionView.setText(DataList.getInstance().getData().get(newValue).getDef());
                    } else {
                        deleteBtn.setVisible(false);
                        editBtn.setVisible(false);
                        definitionView.setText("");
                    }
                }
        );

        deleteBtn.setOnAction(event -> {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có muốn xóa từ này không?");
            Optional<ButtonType> result = a.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                DataList.getInstance().removeWord((String) listView.getSelectionModel().getSelectedItem());
                loadWordList();
            }
        });

        editBtn.setOnAction(event -> {
            definitionView.setEditable(true);
            definitionView.requestFocus();
            saveBtn.setVisible(true);
            editBtn.setVisible(false);
            deleteBtn.setVisible(false);
        });

        saveBtn.setOnAction(event -> {
            if (!definitionView.getText().trim().equals("")) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Đã lưu thành công");
                a.show();
                definitionView.setEditable(false);
                saveBtn.setVisible(false);
                editBtn.setVisible(true);
                deleteBtn.setVisible(true);
                DataList.getInstance().addWord(new Word(listView.getSelectionModel().getSelectedItem(), definitionView.getText()));
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING, "Không để trống định nghĩa");
                a.show();
            }
        });

        wordToFind.textProperty().addListener((observable, oldValue, newValue) -> loadWordList());
    }

    public void loadWordList() {
        listView.getItems().clear();
        listView.getItems().addAll(DataList.getInstance().getTrie().getAllWordsStartWith(wordToFind.getText()));
    }
}