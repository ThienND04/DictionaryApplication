package com.example.dictionary.game;

public abstract class AGame {
    protected int score;

    public abstract void updateScore();

    public int getScore() {
        updateScore();
        return this.score;
    }
}
