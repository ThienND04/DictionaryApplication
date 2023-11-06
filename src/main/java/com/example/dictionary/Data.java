package com.example.dictionary;

import java.io.*;
import java.util.*;

public final class Data {
    private static final Data instance = new Data();

    public static Data getInstance() {
        return instance;
    }

    private Data() {
        this.readData();
        this.readSubData();
    }

    private static final String DATA_FILE_PATH = "data/kk.txt";
    private static final String SPLITTING_CHARACTERS = "<::>";

    private final Map<String, Word> data = new HashMap<>();
    private final Map<String, Word> subData = new HashMap<>();
    private final Trie trie = new Trie();
    private final Trie subTrie = new Trie();

    public Map<String, Word> getData() {
        return this.data;
    }

    public Trie getTrie() {
        return this.trie;
    }
    public Map<String, Word> getSubData() {
        return this.subData;
    }

    public Trie getSubTrie() {
        return this.subTrie;
    }

    public void addWord(Word word) {
        if (word != null) {
            this.data.put(word.getWord(), word);
            this.trie.insert(word.getWord());
            this.writeData();
        }
    }

    public void removeWord(String word) {
        if (word != null) {
            this.data.remove(word);
            this.trie.remove(word);
            this.writeData();
        }
    }

    /**
     * Read data with format in a file, which located in DATA_FILE_PATH.
     */
    private void readData() {
        try {
            FileReader fr = new FileReader(DATA_FILE_PATH);
            BufferedReader br = new BufferedReader(fr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            String[] list = builder.toString().split(SPLITTING_CHARACTERS);
            for(int i = 0; i+1 < list.length; i+=2) {
                String word = list[i];
                String definition = list[i+1];
                this.data.put(word.trim(), new Word(word, definition));
                this.trie.insert(word.trim());
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readSubData() {
        try {
            FileReader fr = new FileReader("data/E_V.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            String[] list = builder.toString().split("</html>");
            for(int i = 0; i < list.length; i++) {
                String[] temp = list[i].split("<html>");
                this.subData.put(temp[0].trim(), new Word(temp[0], temp[1]));
                this.subTrie.insert(temp[0].trim());
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
    public void writeData() {
        try {
            FileWriter fw = new FileWriter(DATA_FILE_PATH);
            BufferedWriter bw = new BufferedWriter(fw);
            Set<String> words = this.data.keySet();
            for (String word : words) {
                bw.write(word + SPLITTING_CHARACTERS + this.data.get(word).getDef() + SPLITTING_CHARACTERS);
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
