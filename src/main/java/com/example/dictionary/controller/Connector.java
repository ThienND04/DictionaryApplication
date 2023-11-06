package com.example.dictionary.controller;

import com.example.dictionary.api.TextToSpeech;
import com.example.dictionary.api.Translate;

public class Connector {
    public void find(String word) {
        TranslateController.getInstance().find(word);
    }
    public String trans(String word) {
        try {
            return Translate.translate(word, "en", "vi");
        } catch (Exception e) {
            return "";
        }
    }
    public void speak(String word) {
        TextToSpeech.textToSpeech(word);
    }
}
