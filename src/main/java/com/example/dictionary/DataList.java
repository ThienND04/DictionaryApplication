package com.example.dictionary;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DataList {
    private DataList() {
        readData();
    }
    private static final String DATA_FILE_PATH = "data/E_V.txt";
    private static final String MY_LIST_FILE_PATH = "data/my_list.txt";
    private static final Pattern WORD_PATTERN = Pattern.compile("(.*)(<html.*</html>)");
    private final Map<String, Word> data = new HashMap<>();
    private final Trie trie = new Trie();
    private final Map<String, Word> myListData = new HashMap<>();

    private final Trie myListTrie = new Trie();

    public void addWord(Word word) {
        if (word != null) {
            this.myListData.put(word.getWord(), word);
            this.myListTrie.insert(word.getWord());
            this.writeListData();
        }
    }

    public void removeWord(String word) {
        if (word != null) {
            this.myListTrie.remove(word.trim());
            this.myListData.remove(word);
            writeListData();
        }
    }

    /**
     * Read data with format in a file, which located in DATA_FILE_PATH.
     */
    public void readData() {
        try {
            FileReader fr = new FileReader(DATA_FILE_PATH);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = WORD_PATTERN.matcher(line.trim());
                matcher.find();
                String word = matcher.group(1);
                String definition = matcher.group(2);
                Word wordObj = new Word(word, definition);
                data.put(word, wordObj);
            }

            trie.insertAll(new ArrayList<>(data.keySet()));
            br.close();
            fr.close();

            fr = new FileReader(MY_LIST_FILE_PATH);
            br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                Matcher matcher = WORD_PATTERN.matcher(line.trim());
                matcher.find();
                String word = matcher.group(1);
                String definition = matcher.group(2);
                Word wordObj = new Word(word, definition);
                myListData.put(word, wordObj);
                myListTrie.insert(word);
            }
            br.close();
            fr.close();
            System.out.println("Loaded data.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write myList data to a file, which located in MY_LIST_FILE_PATH.
     */
    public void writeListData() {
        try {
            FileWriter fw = new FileWriter(MY_LIST_FILE_PATH);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Word word: myListData.values()) {
                bw.write(word.getWord() + word.getDef());
                bw.newLine();
            }

            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Word> getData() {
        return data;
    }

    public Trie getTrie() {
        return trie;
    }

    public Map<String, Word> getMyListData() {
        return myListData;
    }

    public Trie getMyListTrie() {
        return myListTrie;
    }

    private static final DataList instance = new DataList();

    public static DataList getInstance() {
        return instance;
    }
}
