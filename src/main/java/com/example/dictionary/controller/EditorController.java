package com.example.dictionary.controller;

import com.example.dictionary.Application;
import com.example.dictionary.stage.WindowEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;

public class EditorController {
    @FXML
    TextField word;
    @FXML
    HTMLEditor definition;
    @FXML
    Button saveBtn;

    private static EditorController instance;

    public static EditorController getInstance() {
        return instance;
    }

    public void loadData(String word, String definition, int type) {
        this.word.setText(word);
        this.definition.setHtmlText(definition);

        saveBtn.setOnAction(event -> {
            String newWord = this.word.getText().trim();
            String newDefinition = this.definition.getHtmlText().replace("contenteditable=\"true\"", "contenteditable=\"false\"");

            if (newWord.length() == 0) {
                new Alert(Alert.AlertType.WARNING, "Không được để trống").show();
                return;
            }

            if (type == 0) {
                HomeController.getInstance().handleEdited(newWord, newDefinition, word);
            } else {
                TranslateController.getInstance().handleEdited(newWord, newDefinition);
            }
            Application.getInstance().hideWindow(WindowEnum.EDITOR);
        });
    }

    @FXML
    void initialize() {
        instance = this;
    }

}
