package com.example.dictionary.user;

import com.example.dictionary.Application;
import com.example.dictionary.word.Trie;
import com.example.dictionary.word.Word;
import com.example.dictionary.controller.*;
import javafx.scene.image.Image;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class User implements Serializable {
    private int theme = 0;

    public int getTheme() {
        return theme;
    }

    public static final String SPLITTING_CHARACTERS = "<::>";
    public static final String IMAGE_PATH = "data/images/";
    public static final String DEFAULT_IMAGE = "default.jpg";
    public static final String WORDS_PATH = "data/words/";
    public static int lastUserId;
    private boolean isReceiveCoin = false;

    public void setTheme(int theme) {
        this.theme = theme;
        Application.getInstance().changeTheme(theme);
    }

    public ArrayList<Integer> getTasksCount() {
        return tasksCount;
    }

    public ArrayList<Boolean> getTasksTrack() {
        return tasksTrack;
    }

    private final ArrayList<Integer> tasksCount = new ArrayList<>();
    private final ArrayList<Boolean> tasksTrack = new ArrayList<>();
    private int hint = 0;

    public void setHint(int hint) {
        this.hint = hint;
        UserController.getInstance().initUserDetail();
    }

    public boolean isReceiveCoin() {
        return isReceiveCoin;
    }

    public void setReceiveCoin() {
        isReceiveCoin = true;
        Controller.handleChangeStreak();
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
        UserController.getInstance().initUserDetail();
    }
    private int coin = 0;
    private String username;
    private String password;
    private final int id;
    private transient Image image;
    private final Set<LocalDate> loginDays = new HashSet<>();
    private int countOfAddWords;

    public int getCountOfAddWords() {
        return countOfAddWords;
    }

    public int getCountOfSearchWords() {
        return countOfSearchWords;
    }


    public void handleAddWord() {
        countOfAddWords++;
        tasksCount.set(2, tasksCount.get(2) + 1);
        Controller.handleChangeStatics();
    }

    public void handleSearchWord() {
        tasksCount.set(0, tasksCount.get(0) + 1);
        countOfSearchWords++;
        Controller.handleChangeStatics();
    }

    public void handleFinishGame() {
        tasksCount.set(1, tasksCount.get(1) + 1);
        Controller.handleChangeStatics();
    }

    private int countOfSearchWords;

    public Set<LocalDate> getLoginDays() {
        return loginDays;
    }

    public Image getImage() {
        return image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    private transient ArrayList<String> allWords;
    private transient Map<String, Word> words;
    private transient Trie trie;

    public void addWord(Word word) {
        if (word != null) {
            words.put(word.getWord(), word);
            trie.insert(word.getWord());
            handleAddWord();
        }
    }

    public void removeWord(String word) {
        if (word != null) {
            words.remove(word.trim());
            trie.remove(word.trim());
        }
    }

    private void readData() {
        words = new HashMap<>();
        trie = new Trie();
        allWords = new ArrayList<>();
        try {
            FileReader fr = new FileReader(WORDS_PATH + id + ".txt");
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
                words.put(word.trim(), new Word(word, definition));
                trie.insert(word.trim());
            }
            allWords.addAll(words.keySet());
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Word> getWords() {
        return words;
    }

    public Trie getTrie() {
        return trie;
    }

    public void writeData() {
        try {
            FileWriter fw = new FileWriter(WORDS_PATH + id + ".txt");
            BufferedWriter bw = new BufferedWriter(fw);
            words.forEach((key, value) -> {
                try {
                    bw.write(key + SPLITTING_CHARACTERS + value.getDef() + SPLITTING_CHARACTERS);
                    bw.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Word> getRandomWords(int n, Predicate<? super Word> predicate) {
        Random random = new Random();
        ArrayList<Word> res = new ArrayList<>();
        Set<Integer> st = new HashSet<>();
        ArrayList<Word> wordsFiltered = new ArrayList<>(words.values().stream().filter(predicate).collect(Collectors.toList()));

        if (n > wordsFiltered.size()) return res;
        while (st.size() < n) {
            st.add(random.nextInt(wordsFiltered.size()));
        }
        for (Integer t : st) {
            res.add(words.get(allWords.get(t)));
        }
        return res;
    }

    private void readImage() {
        try {
            File file;
            if (new File(IMAGE_PATH + id + ".jpg").exists()) {
                file = new File(IMAGE_PATH + id + ".jpg");
            } else {
                file = new File(IMAGE_PATH + DEFAULT_IMAGE);
            }
            FileInputStream in = new FileInputStream(file);
            image = new Image(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImage(String path) {
        try {
            Files.copy(Paths.get(path), Paths.get(IMAGE_PATH + id + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        image = new Image(path);
        Controller.handleChangeImage();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.id = ++lastUserId;

        try {
            new File(WORDS_PATH + id + ".txt").createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        tasksCount.add(0);
        tasksCount.add(0);
        tasksCount.add(0);

        tasksTrack.add(false);
        tasksTrack.add(false);
        tasksTrack.add(false);

        login();
    }

    public void login() {
        if (!loginDays.contains(LocalDate.now())) {
            isReceiveCoin = false;
            for(int i = 0;i < tasksCount.size(); i++) {
                tasksCount.set(i, 0);
            }
            for(int i = 0;i < tasksTrack.size(); i++) {
                tasksTrack.set(i, false);
            }
        }
        loginDays.add(LocalDate.now());
        readData();
        readImage();
        Application.getInstance().changeTheme(theme);
        Controller.handleChangeUser();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void remove() {
        try {
            new File(WORDS_PATH + id + ".txt").delete();
            new File(IMAGE_PATH + id + ".jpg").delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", id=" + id + '}';
    }

    public int getHint() {
        return hint;
    }
}
