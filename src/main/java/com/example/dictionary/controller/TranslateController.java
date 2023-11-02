package com.example.dictionary.controller;

import com.example.dictionary.DataList;
import com.example.dictionary.Word;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class TranslateController {       
    @FXML
    ChoiceBox<String> target;
    @FXML
    ChoiceBox<String> source;
    @FXML
    Button translateBtn;
    @FXML
    Button addBtn;
    @FXML
    TextField wordToTranslate;
    @FXML
    TextArea translatedWord;

    private static HashMap<String, String> languages = new HashMap<String, String>();

    static {
        languages.put("English", "en");
        languages.put("Vietnamese", "vi");
        languages.put("French", "fr");
        languages.put("Japanese", "ja");
    }

    @FXML
    public void initialize() {                
        target.getItems().addAll(languages.keySet()); 
        source.getItems().addAll(languages.keySet());       

        translateBtn.setOnAction(event -> {
            if (source.getValue() != null && target.getValue() != null && !wordToTranslate.getText().equals("")) {                
                String translatedWord = this.translate();
                this.translatedWord.setText(translatedWord);                
            }
        });

        addBtn.setOnAction(event -> {
            if(!wordToTranslate.getText().equals("") && !translatedWord.getText().equals("")) {
                String word = wordToTranslate.getText();
                String wordDef = String.format("<html> <p> %s </p> </html>", translatedWord.getText());
                DataList.getInstance().addWord(new Word(word, wordDef));
                MyListController.getInstance().loadWordList();
                wordToTranslate.setText("");
                translatedWord.setText("");
            }
        });
    }    

    private String translate() {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(getURL()))                        
            .header("Accept-Encoding", "application/gzip")                    
            .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.body().split("\"")[1];
    }

    private String getURL() {
        StringBuilder url = new StringBuilder("https://translate.googleapis.com/translate_a/single?client=gtx&dt=t");
        url.append("&q=");
        url.append(URLEncoder.encode(this.wordToTranslate.getText(), StandardCharsets.UTF_8));     
        url.append("&sl=" + languages.get(this.source.getValue()));             
        url.append("&tl=" + languages.get(this.target.getValue()));    
        return url.toString();   
    }
}



