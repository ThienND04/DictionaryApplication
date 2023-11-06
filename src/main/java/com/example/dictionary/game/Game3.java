package com.example.dictionary.game;

import com.example.dictionary.Data;
import com.example.dictionary.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Game3 {
    private final Map<String, Word> map;
    private final ArrayList<String> list;

    public Game3() {
        map = Data.getInstance().getData();
        list = new ArrayList<>(this.map.keySet());
    }

    public ArrayList<String> generate() {
        Collections.shuffle(list);
        ArrayList<String> res = new ArrayList<>();
        res.add(list.get(0));
        res.add(map.get(list.get(0)).getDef());
        return res;
    }
}
