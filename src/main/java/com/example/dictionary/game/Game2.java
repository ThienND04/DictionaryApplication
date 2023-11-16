package com.example.dictionary.game;

import com.example.dictionary.word.Word;
import com.example.dictionary.controller.Game2Controller;
import com.example.dictionary.user.UserManager;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Collections;

public class Game2 extends AGame{
    private ArrayList<Word> data;

    public ArrayList<String> generate() {
        ArrayList<String> res = new ArrayList<>();
        data = UserManager.getInstance().getCurrentUser().getRandomWords(Game2Controller.NUMBER_OF_QUESTIONS, word -> true);
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
