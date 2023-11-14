package com.example.dictionary.game;

import com.example.dictionary.user.Data;
import com.example.dictionary.Word;

import java.util.*;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;

public class Game1 extends AGame{
    public final int NUM_QUESTION = 10;
    private Map<String, Word> map;
    private ArrayList<String> listWord;
    private ArrayList<String> listDef;
    private final ArrayList<String> questions = new ArrayList<>();
    private final ArrayList<String> answers = new ArrayList<>();
    private final ArrayList<ArrayList<String>> questionsSelections = new ArrayList<>();
    private final ArrayList<Integer> selectedAnswers = new ArrayList<>();

    private int currentQuestionIndex = 0;

    public void init() {
        map = new HashMap<>();
        Data.getInstance().getData().values().forEach(word -> {
            map.put(getTextFromHTML(word.getWord()),
                    new Word(getTextFromHTML(word.getWord()), getTextFromHTML(word.getDef())));
        });
        listWord = new ArrayList<>(this.map.keySet());
        listDef = this.map.values().stream().map(Word::getDef).distinct().
                collect(Collectors.toCollection(ArrayList::new));
        currentQuestionIndex = 0;

        setReady(listWord.size() >= 3 && listDef.size() >= 3);
        if(! isReady()) {
            return;
        }

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
                selections.addAll(listDef.subList(0, 3));
                if (!selections.contains(answer)) {
                    selections.set(2, answer);
                }
            } else {
                // cho nghĩa, chọn từ đúng
                question = listDef.get(i % listDef.size());
                answer = map.keySet().stream().filter(w -> map.get(w).getDef().equals(question)).toList().getFirst();
                selections.add(answer);
                final String finalAns = answer;
                selections.addAll(
                        listWord.stream().filter(
                                w -> !map.get(w).getDef().equals(finalAns)
                        ).toList().subList(0, 2));
            }

            Collections.shuffle(selections);
            questions.add(question);
            answers.add(answer);
            questionsSelections.add(selections);
            selectedAnswers.add(-1);
        }
    }

    private String getTextFromHTML(String html) {
        return Jsoup.parse(html).text();
    }

    public void playAgain() {
        init();
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

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void toNextQuestion() {
        if(currentQuestionIndex + 1 < NUM_QUESTION) {
            currentQuestionIndex++;
        }
    }

    public void toPrevQuestion() {
        if(currentQuestionIndex > 0) currentQuestionIndex --;
    }
    public boolean isLastQuestion() {
        return currentQuestionIndex == NUM_QUESTION - 1;
    }
}
