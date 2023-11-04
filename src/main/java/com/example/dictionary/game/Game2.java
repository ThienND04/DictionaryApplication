package com.example.dictionary.game;

import com.example.dictionary.DataList;
import com.example.dictionary.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


public class Game2 {
    private final Map<String, Word> map;
    private ArrayList<String> list;

    public Game2() {
        map = DataList.getInstance().getData();
        list = new ArrayList<String>(this.map.keySet());
    }
    public ArrayList<String> generate() {
        Collections.shuffle(list);
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < 6; i++) {
            res.add(list.get(i));
            res.add(map.get(list.get(i)).getDef());
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
