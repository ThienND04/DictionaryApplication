package com.example.dictionary;

import java.io.*;
import java.util.*;

public final class DataList {
    private static final DataList instance = new DataList();

    public static DataList getInstance() {
        return instance;
    }

    private DataList() {

    }

    private static final String DATA_FILE_PATH = "data/E_V.txt";
    private static final String MY_LIST_FILE_PATH = "data/my_list.txt";
    private static final String SPLITTING_CHARACTERS = "<html>";
    private final Map<String, Word> data = new HashMap<>();
    private final Trie wordsTrie = new Trie();

    public Map<String, Word> getData() {
        return this.data;
    }

    public Trie getWordsTrie() {
        return this.wordsTrie;
    }

    private final Trie myList = new Trie();

    public Trie getMyList() {
        return this.myList;
    }

    public void addWordToList(String word) {
        if (word != null) {
            this.myList.insert(word.trim());
            this.writeListData();
        }
    }

    public void removeWordFromList(String word) {
        if (word != null) {
            this.myList.remove(word.trim());
            this.writeListData();
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
                String[] parts = line.split(SPLITTING_CHARACTERS);
                String word = parts[0];
                String definition = SPLITTING_CHARACTERS + parts[1];
                Word wordObj = new Word(word, definition);
                this.data.put(word, wordObj);
            }
            this.wordsTrie.insertAll(new ArrayList<>(data.keySet()));
            br.close();
            fr.close();

            fr = new FileReader(MY_LIST_FILE_PATH);
            br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                this.myList.insert(line.trim());
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
            ArrayList<String> allWords = myList.allWordsStartWith("");
            for (String word : allWords) {
                bw.write(word);
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
