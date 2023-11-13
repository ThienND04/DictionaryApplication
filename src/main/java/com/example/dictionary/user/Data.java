package com.example.dictionary.user;

import com.example.dictionary.Trie;
import com.example.dictionary.Word;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public final class Data {
    private static final Data instance = new Data();

    public static Data getInstance() {
        return instance;
    }

    private Data() {
        readSubData();
    }

    private static final String SPLITTING_CHARACTERS = "<::>";
    private final ArrayList<String> allWords = new ArrayList<>();
    private final Map<String, Word> data = new HashMap<>();
    private final Map<String, Word> subData = new HashMap<>();
    private final Trie trie = new Trie();
    private final Trie subTrie = new Trie();
    private User user;

    public void setUser(User user) {
        this.user = user;
        allWords.clear();
        data.clear();
        trie.clear();
        readData();
        allWords.addAll(subData.keySet());
        allWords.addAll(data.keySet());
    }

    public User getUser() {
        return user;
    }

    public Map<String, Word> getData() {
        return data;
    }

    public Trie getTrie() {
        return trie;
    }

    public Map<String, Word> getSubData() {
        return subData;
    }

    public Trie getSubTrie() {
        return subTrie;
    }

    public void addWord(Word word) {
        if (word != null) {
            data.put(word.getWord(), word);
            trie.insert(word.getWord());
        }
    }

    public void removeWord(String word) {
        if (word != null) {
            data.remove(word.trim());
            trie.remove(word.trim());
        }
    }

    /**
     * Read data with format in a file, which located in DATA_FILE_PATH.
     */
    private void readData() {
        if(user == null)
            return;
        try {
            FileReader fr = new FileReader("data/user-" + user.getId() + ".txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            String[] list = builder.toString().split(SPLITTING_CHARACTERS);
            for (int i = 0; i + 1 < list.length; i += 2) {
                String word = list[i];
                String definition = list[i + 1];
                data.put(word.trim(), new Word(word, definition));
                trie.insert(word.trim());
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
            for (int i = 0; i < list.length; i++) {
                String[] temp = list[i].split("<html>");
                subData.put(temp[0].trim(), new Word(temp[0], temp[1]));
                subTrie.insert(temp[0].trim());
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
        if (user == null)
            return;
        try {
            FileWriter fw = new FileWriter("data/user-" + user.getId() + ".txt");
            BufferedWriter bw = new BufferedWriter(fw);
            Set<String> words = data.keySet();
            for (String word : words) {
                bw.write(word + SPLITTING_CHARACTERS + data.get(word).getDef() + SPLITTING_CHARACTERS);
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getRandomWords(int n, int type) {
        Random random = new Random();
        ArrayList<String> res = new ArrayList<>();
        if (n <= 0)
            return res;
        Set<Integer> st = new HashSet<>();
        if (type == 0) {
            if (n > allWords.size())
                return res;
            while (st.size() < n) {
                st.add(random.nextInt(allWords.size()));
                for (Integer t : st) {
                    String word = allWords.get(t);
                    res.add(word);
                    if (t < subData.size()) {
                        res.add(subData.get(word).getDef());
                    } else {
                        res.add(data.get(word).getDef());
                    }
                }
            }
        } else if (type == 1) {
            if (n > data.size())
                return res;
            while (st.size() < n) {
                st.add(random.nextInt(data.size()));
            }
            for (Integer t : st) {
                String word = allWords.get(subData.size() + t);
                res.add(word);
                res.add(data.get(word).getDef());
            }
        } else {
            if (n > subData.size())
                return res;
            while (st.size() < n) {
                st.add(random.nextInt(subData.size()));
            }
            for (Integer t : st) {
                String word = allWords.get(t);
                res.add(word);
                res.add(subData.get(word).getDef());
            }
        }
        return res;
    }

    public ArrayList<String> getRandomWordsByDay(int n) {
        ArrayList<String> res = new ArrayList<>();
        if(n <= 0 || allWords.size() < n)
            return res;

        LocalDate date = LocalDate.now();
        long seed = date.getYear() * 10000L + date.getMonthValue() * 100L + date.getDayOfMonth();
        Random random = new Random(seed);
        Set<Integer> st = new HashSet<>();

        while (st.size() < n) {
            int t = random.nextInt(data.size() + subData.size());
            st.add(t);
        }
        for (Integer t : st) {
            String word = allWords.get(t);
            res.add(word);
            if (t < subData.size()) {
                res.add(subData.get(word).getDef());
            } else {
                res.add(data.get(word).getDef());
            }
        }
        return res;
    }

}
