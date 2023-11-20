package com.example.dictionary.word;


import java.io.*;
import java.time.LocalDate;
import java.util.*;

public final class Data {
    private static Data instance = new Data();

    public static Data getInstance() {
        return instance;
    }

    public Data() {
        readSubWords();
    }

    private final ArrayList<String> allSubWords = new ArrayList<>();
    private final Map<String, Word> subWords = new HashMap<>();
    private final Trie subTrie = new Trie();


    public Map<String, Word> getSubData() {
        return subWords;
    }

    public Trie getSubTrie() {
        return subTrie;
    }


    private void readSubWords() {
        try {
            FileReader fr = new FileReader("data/words/E_V.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            String[] list = builder.toString().split("</html>");
            for (int i = 0; i < list.length; i++) {
                String[] temp = list[i].split("<html>");
                subWords.put(temp[0].trim(), new Word(temp[0], temp[1]));
                subTrie.insert(temp[0].trim());
            }
            allSubWords.addAll(subWords.keySet());
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Word> getRandomWordsByDay(int n) {
        ArrayList<Word> res = new ArrayList<>();

        if (n <= 0 || allSubWords.size() < n)
            return res;
        LocalDate date = LocalDate.now();
        long seed = date.getYear() * 10000L + date.getMonthValue() * 100L + date.getDayOfMonth();
        Random random = new Random(seed);
        Set<Integer> st = new HashSet<>();

        while (st.size() < n) {
            int t = random.nextInt(allSubWords.size());
            st.add(t);
        }
        for (Integer t : st) {
            res.add(subWords.get(allSubWords.get(t)));
        }
        return res;
    }

    public ArrayList<Word> getRandomWordsByDay(int n, LocalDate date) {
        ArrayList<Word> res = new ArrayList<>();

        if (n <= 0 || allSubWords.size() < n)
            return res;
        long seed = date.getYear() * 10000L + date.getMonthValue() * 100L + date.getDayOfMonth();
        Random random = new Random(seed);
        Set<Integer> st = new HashSet<>();

        while (st.size() < n) {
            int t = random.nextInt(allSubWords.size());
            st.add(t);
        }
        for (Integer t : st) {
            res.add(subWords.get(allSubWords.get(t)));
        }
        return res;
    }

}
