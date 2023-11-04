package com.example.dictionary.controller;

import com.example.dictionary.DataList;
import com.example.dictionary.Dictionary;
import com.example.dictionary.Word;
import com.example.dictionary.utils.TextToSpeech;
import com.example.dictionary.utils.Translate;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TranslateController {

    @FXML
    Button translateBtn;
    @FXML
    Button addBtn;
    @FXML
    TextField wordToTranslate;
    @FXML
    TextArea translatedWord;
    @FXML
    WebView detail;
    @FXML
    Button speakBtn;


    @FXML
    public void initialize() {

        translateBtn.setOnAction(event -> {
            String word = wordToTranslate.getText();
            if(!word.equals("")) {
                Task<Void> task = new Task<>() {
                    @Override
                    protected Void call() {
                        String translated = Translate.translate(word, "en", "vi");
                        String detailContent = Translate.getDetail(word);
                        Platform.runLater(() -> {
                            translatedWord.setText(translated);
                            detail.getEngine().loadContent(detailContent);
                            Dictionary.loadingDialog.hide();
                        });
                        return null;
                    }
                };
                Dictionary.loadingDialog.show();
                new Thread(task).start();
            }
        });

        speakBtn.setOnAction(event -> TextToSpeech.textToSpeech(wordToTranslate.getText()));

        addBtn.setOnAction(event -> {
            if(!wordToTranslate.getText().equals("") && !translatedWord.getText().equals("")) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Đã thêm thành công");
                a.show();
                DataList.getInstance().addWord(new Word(wordToTranslate.getText(), translatedWord.getText()));
                HomeController.getInstance().loadWordList();
                wordToTranslate.setText("");
                translatedWord.setText("");
                detail.getEngine().loadContent("");
            }
        });
    }
}



