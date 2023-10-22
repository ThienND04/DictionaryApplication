package com.example.dictionary.controller;

import com.example.dictionary.http.Response;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;


public class TranslateController {

    private static final String apiKey = "5ae7ef4d80msh360c2d2fc860412p1699e1jsnf5929929bced";

    @FXML
    ChoiceBox<String> source;
    @FXML
    ChoiceBox<String> target;
    @FXML
    Button translateBtn;
    @FXML
    Button addBtn;
    @FXML
    TextField wordToTranslate;
    @FXML
    TextArea translatedWord;

    @FXML
    public void initialize() {
        target.getItems().addAll("en", "vi");
        source.getItems().addAll("en", "vi");

        translateBtn.setOnAction(event -> {
            if (source.getValue() != null && target.getValue() != null && wordToTranslate.getText().equals("")) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
                        .header("content-type", "application/x-www-form-urlencoded")
                        .header("Accept-Encoding", "application/gzip")
                        .header("X-RapidAPI-Key", apiKey)
                        .header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                        .method("POST", HttpRequest.BodyPublishers.ofString(
                                String.format("q=%s&target=%s&source=%s", wordToTranslate.getText(), target.getValue(), source.getValue())
                        ))
                        .build();
                HttpResponse<String> response = null;
                try {
                    response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                Response rp = gson.fromJson(response.body(), Response.class);
                translatedWord.setText(rp.data.translations.get(0).translatedText);
            }
        });

        addBtn.setOnAction(event -> {
            if(!wordToTranslate.getText().equals("") && !translatedWord.getText().equals("")) {

            }
        });
    }
}



