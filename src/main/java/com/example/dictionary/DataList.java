package com.example.dictionary;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DataList {
    private static final DataList instance = new DataList();

    public static DataList getInstance() {
        return instance;
    }

    private DataList() {

    }

    private static final String DATA_FILE_PATH = "data/E_V.txt";
    private static final String MY_LIST_FILE_PATH = "data/my_list.txt";
    private static final Pattern WORD_PATTERN = Pattern.compile("(.*)(<html.*</html>)");
    private final Map<String, Word> data = new HashMap<>();
    private final Map<String, Word> myListData = new HashMap<>();
    private final Trie wordsTrie = new Trie();

    public Map<String, Word> getData() {
        return this.data;
    }

    public Trie getWordsTrie() {
        return wordsTrie;
    }

    private final Trie myList = new Trie();

    public Trie getMyList() {
        return this.myList;
    }

    public Map<String, Word> getMyListData() {
        return myListData;
    }

    public void addWordToList(Word word) {
        if (word != null) {
            this.myList.insert(word.getWord());
            this.myListData.put(word.getWord(), word);
            writeListData();
        }
    }

    public void removeWordFromList(String word) {
        if (word != null) {
            this.myList.remove(word.trim());
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
            wordsTrie.insertAll(new ArrayList<>(data.keySet()));
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
                this.myList.insert(word);
            }
            br.close();
            fr.close();
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
}
