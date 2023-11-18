package com.example.dictionary.game;

import com.example.dictionary.word.Word;
import com.example.dictionary.user.UserManager;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Game3 extends AGame {
    private Map<String, Word> map;
    private ArrayList<Word> list;
    public static final int NUM_QUESTION = 5;
    private int solvedQuestion = 0;
    private int currentQuestionI = 0;

    public Game3() {

    }

    public void newGame() {
        map = UserManager.getInstance().getCurrentUser().getWords();
        list = new ArrayList<Word>(map.values().stream().distinct().toList());
        solvedQuestion = 0;
        currentQuestionI = 0;
        Collections.shuffle(list);
    }

    @Override
    public boolean isReady() {
        return !list.isEmpty();
    }

    public String getMeaning() {
        return getTextFromHTML(list.get(currentQuestionI).getDef());
    }

    public String getGuessWord() {
        return list.get(currentQuestionI).getWord();
    }

    public boolean isGuessedWord(String playerGuess) {
        return getGuessWord().equals(playerGuess);
    }

    public void nextQuestion() {
        currentQuestionI ++;
        if(currentQuestionI == list.size()) {
            Collections.shuffle(list);
            currentQuestionI = 0;
        }
    }

    public double getProgress() {
        return 1.0 * solvedQuestion / NUM_QUESTION;
    }

    public boolean isFinished() {
        return solvedQuestion == NUM_QUESTION;
    }

    public void increaseSolvedQuestion() {
        solvedQuestion ++;
    }
    private String getTextFromHTML(String html) {
        return Jsoup.parse(html).text();
    }

    private static final String PATH = "data/games/3.txt";
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