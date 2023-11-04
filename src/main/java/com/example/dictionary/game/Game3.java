package com.example.dictionary.game;

import com.example.dictionary.DataList;
import com.example.dictionary.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Game3 {
    private final Map<String, Word> map;
    private final ArrayList<String> list;

    public Game3() {
        map = DataList.getInstance().getData();
        list = new ArrayList<String>(this.map.keySet());
    }

    public ArrayList<String> generate() {
        Collections.shuffle(list);
        ArrayList<String> res = new ArrayList<String>();
        res.add(list.get(0));
        res.add(map.get(list.get(0)).getDef());
        return res;
    }

    boolean checkAnswer(String str1, String str2) {
        return map.containsKey(str1) && map.get(str1).equals(str2);
    }
}
