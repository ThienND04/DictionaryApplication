package com.example.dictionary.game;

import com.example.dictionary.word.Word;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import com.example.dictionary.user.UserManager;
import org.jsoup.Jsoup;

public class Game1 extends AGame{
    public static final int GAME_ID = 1;
    public static final int NUM_QUESTION = 5;
    public static final int MAX_FAULT = 3;
    public static final int MAX_HINT = 3;

    public void init() {
        map = new HashMap<>();
        UserManager.getInstance().getCurrentUser().getWords().values().forEach(word -> {
            map.put(getTextFromHTML(word.getWord()),
                    new Word(getTextFromHTML(word.getWord()), getTextFromHTML(word.getDef())));
        });
        listWord = new ArrayList<>(this.map.keySet());
        listDef = this.map.values().stream().map(Word::getDef).distinct().
                collect(Collectors.toCollection(ArrayList::new));
        currentQuestionIndex = 0;
        solved = 0;
        fault = 0;
        isSolved.clear();
        for(int i = 0; i < NUM_QUESTION; i ++) isSolved.add(false);
        setReady(listWord.size() >= NUM_QUESTION && listDef.size() >= NUM_QUESTION);
        if(! isReady()) {
            return;
        }
        generateRandomQuestions();
    }

    public boolean checkFinish() {
        return questionRemain() == 0 || fault >= MAX_FAULT;
    }

    public void submit() {
        if(getAnswerIndex() == getSelectedAns()) {
            solved++;
            isSolved.set(currentQuestionIndex, true);
        }
        else {
            fault ++;
        }
    }

    public void toNextQuestion() {
        do {
            currentQuestionIndex ++;
            currentQuestionIndex %= NUM_QUESTION;
        }
        while (isSolved.get(currentQuestionIndex));
        selectAns(- 1);
    }

    public int questionRemain() {
        return NUM_QUESTION - solved;
    }

    public double getProgress() {
        return 1.0 * solved / NUM_QUESTION;
    }

    private void generateRandomQuestions() {
        Random rd = new Random();
        Collections.shuffle(listWord);
        Collections.shuffle(listDef);
        questions.clear();
        answers.clear();
        questionsSelections.clear();
        selectedAnswers.clear();
        for(int i = 0; i < NUM_QUESTION; i ++) {
            ArrayList<String> selections = new ArrayList<>();
            String question;
            String answer;

            if((int) (Math.random() * 1000) % 2 == 0) {
                // cho từ, chọn nghĩa đúng
                question = listWord.get(i % listWord.size());
                answer = map.get(question).getDef();
                Set<Integer> tmp = new HashSet<>();
                while(tmp.size() < 3) {
                    tmp.add(rd.nextInt(listDef.size()));
                }
                selections = tmp.stream().map(integer -> listDef.get(integer)).
                        collect(Collectors.toCollection(ArrayList::new));
                if (!selections.contains(answer)) {
                    selections.set(2, answer);
                }
            } else {
                // cho nghĩa, chọn từ đúng
                question = listDef.get(i % listDef.size());
                answer = map.keySet().stream().filter(w -> map.get(w).getDef().equals(question)).toList().get(0);
                Set<Integer> tmp = new HashSet<>();
                while(tmp.size() < 2) {
                    int ii = rd.nextInt(listWord.size());
                    if (! listWord.get(ii).equals(answer)) tmp.add(ii);
                }
                selections = tmp.stream().map(integer -> listWord.get(integer)).
                        collect(Collectors.toCollection(ArrayList::new));
                selections.add(answer);
            }
            Collections.shuffle(selections);
            questions.add(question);
            answers.add(answer);
            questionsSelections.add(selections);
            selectedAnswers.add(-1);
        }
    }

    private Map<String, Word> map;
    private ArrayList<String> listWord;
    private ArrayList<String> listDef;
    private final ArrayList<String> questions = new ArrayList<>();
    private final ArrayList<String> answers = new ArrayList<>();
    private final ArrayList<ArrayList<String>> questionsSelections = new ArrayList<>();
    private final ArrayList<Integer> selectedAnswers = new ArrayList<>();
    private final ArrayList<Boolean> isSolved = new ArrayList<>();
    private int solved = 0;
    private int fault = 0;
    private int currentQuestionIndex = 0;
    public int getSolved() {
        return solved;
    }
    public int getFault() {
        return fault;
    }
    public String getQuestion() {
        return questions.get(currentQuestionIndex);
    }
    public String getAnswer() {
        return answers.get(currentQuestionIndex);
    }
    public int getAnswerIndex() {
        return questionsSelections.get(currentQuestionIndex).indexOf(getAnswer());
    }
    public ArrayList<String> getSelections() {
        return questionsSelections.get(currentQuestionIndex);
    }
    public void selectAns(int selectedAns) {
        selectedAnswers.set(currentQuestionIndex, selectedAns);
    }
    public int getSelectedAns() {
        return selectedAnswers.get(currentQuestionIndex);
    }
    private String getTextFromHTML(String html) {
        return Jsoup.parse(html).text();
    }
}
