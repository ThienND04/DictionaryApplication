package com.example.dictionary.game;

public abstract class AGame {
    private boolean isReady = true;

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}
