package com.example.dictionary.game;

import com.example.dictionary.user.Data;
import com.example.dictionary.Word;
import com.example.dictionary.controller.Game2Controller;

import java.util.ArrayList;
import java.util.Map;

public class Game2 extends AGame{
    public Game2() {

    }

    public ArrayList<String> generate() {
        return Data.getInstance().getRandomWords(Game2Controller.NUMBER_OF_QUESTIONS, 1);
    }

    public boolean checkAnswer(String str1, String str2) {
        Map<String, Word> map = Data.getInstance().getData();
        return (map.containsKey(str1) && map.get(str1).getDef().equals(str2)) ||
                (map.containsKey(str2) && map.get(str2).getDef().equals(str1));
    }

}
