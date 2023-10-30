package com.example.dictionary.game;

import com.example.dictionary.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Game1 extends AGame {
    private static final Game1 instance = new Game1();
    public static Game1 getInstance() {return instance;}
    private static final String DATA_FILE_PATH = "data/game1.txt";
    private static final String SPLITTING_CHARACTERS = " \\| ";
    private static final int SCORE_PER_QUESTION = 10;
    private final List<Question> questions = new ArrayList<>();
    private int currentQuestion;

    public Game1() {
        readData();
        randomizedQuestion();
        currentQuestion = 0;
    }

    public void playAgain() {
        for(Question qs: questions) {
            qs.setSelectedAnswer(-1);
        }
        randomizedQuestion();
        currentQuestion = 0;
    }

    public void randomizedQuestion() {
        Collections.shuffle(questions, new Random((int)(Math.random() * 1000000)));
    }

    public Question getCurrentQuestion () throws RuntimeException {
        if(currentQuestion < questions.size()) {
            return questions.get(currentQuestion);
        }
        else throw new RuntimeException("No remain question.");
    }

    public void prevQuestion () {
        if(currentQuestion > 0) {
            currentQuestion --;
        }
    }

    public void nextQuestion () {
        if(currentQuestion + 1 < questions.size()) {
            currentQuestion ++;
        }
    }

    @Override
    public void updateScore() {
        for(Question question: questions) {
            if(question.isTrue()) {
                this.score += SCORE_PER_QUESTION;
            }
        }
    }

    public void readData() {
        try {
            FileReader fr = new FileReader(DATA_FILE_PATH);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(SPLITTING_CHARACTERS);
                questions.add(new Question(parts));
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Question {
        static public final int NUM_ANS = 3;
        private String question;
        private int expectedAnswer;
        private int selectedAnswer;
        private List<String> answers;

        public Question(String question, int expectedAnswer, List<String> answers) throws RuntimeException {
            this.question = question;
            this.expectedAnswer = expectedAnswer;
            this.answers = answers;
            this.selectedAnswer = -1;

            if (answers.size() != NUM_ANS) {
                throw new RuntimeException(String.format("Number of answer do not equals %d!", NUM_ANS));
            }
        }

        public Question(String[] parts) throws RuntimeException {
            if(parts.length != NUM_ANS + 2) {
                throw new RuntimeException("Question format is not good.");
            }
            this.question = parts[0];
            this.expectedAnswer = Integer.parseInt(parts[1]);
            this.answers = Arrays.asList(Arrays.copyOfRange(parts, 2, parts.length));
            this.selectedAnswer = -1;
        }

        public void setSelectedAnswer(int selectedAnswer) {
            this.selectedAnswer = selectedAnswer;
        }

        public boolean isTrue() {
            return selectedAnswer == expectedAnswer;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public int getExpectedAnswer() {
            return expectedAnswer;
        }

        public void setExpectedAnswer(int expectedAnswer) {
            this.expectedAnswer = expectedAnswer;
        }

        public int getSelectedAnswer() {
            return selectedAnswer;
        }

        public List<String> getAnswers() {
            return answers;
        }

        public void setAnswers(List<String> answers) {
            this.answers = answers;
        }
    }
}
