package com.example.dictionary.game;

import com.example.dictionary.controller.Game3Controller;
import com.example.dictionary.user.Data;
import com.example.dictionary.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Game3 extends AGame {

    public Game3() {

    }

    public ArrayList<String> generate() {
        return Data.getInstance().getRandomWords(Game3Controller.NUMBER_OF_QUESTIONS, 1);
    }
}
