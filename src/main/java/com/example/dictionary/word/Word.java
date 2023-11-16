package com.example.dictionary.word;

import java.util.Objects;

public class Word {
    private String word;
    private String def;

    public Word(String word, String def) {
        this.word = word.trim();
        this.def = def.trim();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word) && Objects.equals(def, word1.def);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, def);
    }
}
