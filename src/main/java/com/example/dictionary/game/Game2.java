package com.example.dictionary.game;

import com.example.dictionary.word.Word;
import com.example.dictionary.controller.Game2Controller;
import com.example.dictionary.user.UserManager;
import org.jsoup.Jsoup;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    private static final String PATH = "data/games/2.txt";
    private static final String SPLITTING_CHARACTERS = " ";
    public static Map<Integer, Double> playersBestTime = new HashMap<>();
    public static void readData() {
        readData(playersBestTime, PATH, SPLITTING_CHARACTERS);
    }
    public static void writeData() {
        writeData(PATH, playersBestTime, SPLITTING_CHARACTERS);
    }
    public static double getBestTime() {
        return getBestTime(UserManager.getInstance().getCurrentUser().getId());
    }
    public static double getBestTime(int id) {
        return playersBestTime.getOrDefault(id, Double.MAX_VALUE);
    }
    public static void setBestTime(double time) {
        playersBestTime.put(UserManager.getInstance().getCurrentUser().getId(), time);
    }
    static {
        readData();
    }
}
