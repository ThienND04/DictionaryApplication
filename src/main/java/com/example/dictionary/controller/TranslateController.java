package com.example.dictionary.controller;

import com.example.dictionary.Data;
import com.example.dictionary.Application;
import com.example.dictionary.Word;
import com.example.dictionary.api.TextToSpeech;
import com.example.dictionary.api.Translate;
import com.example.dictionary.stage.WindowEnum;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.util.ArrayList;

public class TranslateController {
    private static final Connector connector = new Connector();
    @FXML
    private Button editBtn;
    @FXML
    private ListView<String> suggestions;
    @FXML
    private Button translateBtn;
    @FXML
    private Button addBtn;
    @FXML
    private TextField wordToTranslate;
    @FXML
    private WebView meaningView;
    @FXML
    private WebView detail;
    @FXML
    private Button speakBtn;
    @FXML
    private ChoiceBox<String> type;

    public void find(String word) {
        wordToTranslate.setText(word);
        translateBtn.fire();
    }

    public static TranslateController getInstance() {
        return instance;
    }

    private static TranslateController instance;

    private static final ArrayList<String> langs = new ArrayList<>();

    static {
        langs.add("ANH-VIỆT");
        langs.add("VIỆT-ANH");
    }

    private String meaning = "";

    @FXML
    public void initialize() {
        instance = this;

        type.setItems(FXCollections.observableArrayList(langs));
        type.setValue(langs.get(0));

        wordToTranslate.textProperty().addListener((a, b, c) -> {
            if (c.trim().length() > 0) {
                try {
                    Task<Void> task = new Task<>() {
                        @Override
                        protected Void call() {
                            ArrayList<String> res = Translate.getSuggestions(c.trim(), langs.indexOf(type.getValue()));
                            Platform.runLater(() -> {
                                suggestions.getItems().clear();
                                suggestions.getItems().addAll(res);
                            });
                            return null;
                        }
                    };
                    new Thread(task).start();
                } catch (Exception ignored) {

                }
            } else {
                suggestions.getItems().clear();
            }
        });


        suggestions.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
            if (c != null)
                find(c);
        });

        translateBtn.setOnAction(event -> {
            String word = wordToTranslate.getText();

            if (!word.equals("")) {
                Task<Void> task = new Task<>() {
                    @Override
                    protected Void call() {
                        try {
                            String translatedWord;
                            String detailContent;

                            if (type.getValue().equals(langs.get(0))) {
                                translatedWord = Translate.translate(word, "en", "vi");
                                detailContent = Translate.getDetail(word);
                            } else {
                                translatedWord = Translate.translate(word, "vi", "en");
                                detailContent = Translate.getDetail(translatedWord);
                            }

                            meaning = translatedWord;

                            Platform.runLater(() -> {
                                meaningView.getEngine().loadContent(translatedWord);
                                detail.getEngine().loadContent(detailContent);

                                detail.getEngine().getLoadWorker().stateProperty().addListener((a, b, c) -> {
                                    if (c == Worker.State.SUCCEEDED) {
                                        JSObject window = (JSObject) detail.getEngine().executeScript("window");
                                        window.setMember("javaConnector", connector);
                                    }
                                });

                            });

                        } catch (Exception e) {
                            Platform.runLater(() -> new Alert(Alert.AlertType.WARNING, "Lỗi mạng").show());
                        } finally {
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
            if (!wordToTranslate.getText().equals(""))
                TextToSpeech.textToSpeech(wordToTranslate.getText());
            else {
                new Alert(Alert.AlertType.WARNING, "Không được để trống").show();
            }
        });

        addBtn.setOnAction(event -> {
            if (!wordToTranslate.getText().equals("") && !meaning.equals("")) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Đã thêm thành công");
                a.show();
                Data.getInstance().addWord(new Word(wordToTranslate.getText(), "<html>" + meaning + "</html>"));
                HomeController.getInstance().loadWordList();
            } else
                new Alert(Alert.AlertType.WARNING, "Không được để trống").show();
        });

        editBtn.setOnAction(event -> {
            if (!wordToTranslate.getText().trim().equals("") && !meaning.trim().equals("")) {
                EditorController.getInstance().loadData(wordToTranslate.getText(), meaning, 1);
                Application.getInstance().showWindow(WindowEnum.EDITOR);
            } else {
                new Alert(Alert.AlertType.WARNING, "Không được để trống").show();
            }

        });
    }

    public void handleEdited(String newWord, String newDefinition) {
        wordToTranslate.setText(newWord);
        meaningView.getEngine().loadContent(newDefinition);
        meaning = newDefinition;
    }

}



