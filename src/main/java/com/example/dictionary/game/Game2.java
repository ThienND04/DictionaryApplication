package com.example.dictionary.game;

import com.example.dictionary.user.Data;
import com.example.dictionary.Word;
import com.example.dictionary.controller.Game2Controller;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class Game2 extends AGame{
    private ArrayList<Word> data;

    public ArrayList<String> generate() {
        ArrayList<String> res = new ArrayList<>();
        data = Data.getInstance().getRandomWords(Game2Controller.NUMBER_OF_QUESTIONS);
        data.forEach(
            word -> {
                word.setWord(getTextFromHTML(word.getWord()));
                word.setDef(getTextFromHTML(word.getDef()));
                res.add(word.getWord());
                res.add(word.getDef());
            }
        );
        Collections.shuffle(res);
        return res;
    }

    public boolean checkAnswer(String str1, String str2) {
        return data.contains(new Word(str1, str2))
                || data.contains(new Word(str2, str1));
    }

    private String getTextFromHTML(String html) {
        return Jsoup.parse(html).text();
    }

}
