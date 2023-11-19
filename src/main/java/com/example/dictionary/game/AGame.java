package com.example.dictionary.game;

import java.io.*;
import java.util.Map;

public abstract class AGame implements Serializable {
    private transient boolean isReady = true;

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}
