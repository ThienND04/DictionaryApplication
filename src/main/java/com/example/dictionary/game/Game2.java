package com.example.dictionary.game;

import com.example.dictionary.Data;
import com.example.dictionary.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


public class Game2 {
    private final Map<String, Word> map;
    private final ArrayList<String> list;

    public Game2() {
        map = Data.getInstance().getData();
        list = new ArrayList<>(this.map.keySet());
    }

    public ArrayList<String> generate(int n) {
        Collections.shuffle(list);
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < n; i++) {
            res.add(list.get(i % list.size()));
            res.add(map.get(list.get(i % list.size())).getDef());
        }
        Collections.shuffle(res);
        return res;
    }

    private final double maxScore = 0;

    public boolean checkAnswer(String str1, String str2) {
        boolean t = (map.containsKey(str1) && map.get(str1).getDef().equals(str2)) ||
                (map.containsKey(str2) && map.get(str2).getDef().equals(str1));
        return t;
    }

}
