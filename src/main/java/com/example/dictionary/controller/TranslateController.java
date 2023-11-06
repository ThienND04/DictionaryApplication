package com.example.dictionary.controller;

import com.example.dictionary.Data;
import com.example.dictionary.Application;
import com.example.dictionary.Word;
import com.example.dictionary.api.TextToSpeech;
import com.example.dictionary.api.Translate;
import com.example.dictionary.stage.WindowEnum;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class TranslateController {
    private static Connector connector = new Connector();
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

    public void find(String word) {
        wordToTranslate.setText(word);
        translateBtn.fire();
    }

    public static TranslateController getInstance() {
        return instance;
    }

    private static TranslateController instance;

    @FXML
    public void initialize() {
        instance = this;

        translateBtn.setOnAction(event -> {
            String word = wordToTranslate.getText();

            if(!word.equals("")) {
                Task<Void> task = new Task<>() {
                    @Override
                    protected Void call() {
                        try {
                            String translated = Translate.translate(word, "en", "vi");
                            String detailContent = Translate.getDetail(word);
                            Platform.runLater(() -> {
                                translatedWord.setText(translated);
                                detail.getEngine().loadContent(detailContent);

                                detail.getEngine().getLoadWorker().stateProperty().addListener((a, b, c) -> {
                                    if(c == Worker.State.SUCCEEDED) {
                                        JSObject window = (JSObject) detail.getEngine().executeScript("window");
                                        window.setMember("javaConnector", connector);
                                    }
                                });

                            });
                        } catch (Exception e) {
                            Platform.runLater(() -> new Alert(Alert.AlertType.WARNING, "Lỗi mạng").show());
                        }
                        finally {
                            Platform.runLater(() -> Application.getInstance().hideWindow(WindowEnum.WAITING));
                        }
                        return null;
                    }
                };
                Application.getInstance().showWindow(WindowEnum.WAITING);
                new Thread(task).start();
            } else
                new Alert(Alert.AlertType.WARNING, "Không được để trống").show();
        });

        speakBtn.setOnAction(event -> {
            if(!wordToTranslate.getText().equals(""))
                TextToSpeech.textToSpeech(wordToTranslate.getText());
            else {
                new Alert(Alert.AlertType.WARNING, "Không được để trống").show();
            }
        });

        addBtn.setOnAction(event -> {
            if(!wordToTranslate.getText().equals("") && !translatedWord.getText().equals("")) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Đã thêm thành công");
                a.show();
                Data.getInstance().addWord(new Word(wordToTranslate.getText(), translatedWord.getText()));
                HomeController.getInstance().loadWordList();
            } else
                new Alert(Alert.AlertType.WARNING, "Không được để trống").show();
        });
    }
}


