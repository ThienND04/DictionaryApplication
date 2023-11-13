package com.example.dictionary;

import java.util.*;

class TrieNode {
    private boolean endOfWord = false;

    public boolean getEndOfWord() {
        return this.endOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    private final Map<Character, TrieNode> children = new TreeMap<>();
}

public class Trie {
    private TrieNode root = new TrieNode();
    public void insert(String word) {
        TrieNode temp = this.root;
        for(int i = 0; i < word.length(); i++) {
            if(temp.getChildren().get(word.charAt(i)) == null) {
                temp.getChildren().put(word.charAt(i), new TrieNode());
            }
            temp = temp.getChildren().get(word.charAt(i));
        }
        temp.setEndOfWord(true);
    }

    public void clear() {
        root = new TrieNode();
    }

    public void remove(String word) {
        TrieNode temp = this.root;
        for(int i = 0; i < word.length(); i++) {
            if(temp.getChildren().get(word.charAt(i)) == null) {
                return;
            }
            temp = temp.getChildren().get(word.charAt(i));
        }
        temp.setEndOfWord(false);
    }

    public void insertAll(List<String> words) {
        for(String word : words) {
            this.insert(word);
        }
    }

    public void removeAll() {
        this.root = new TrieNode();
    }

    public ArrayList<String> getAllWordsStartWith(String prefix) {
        ArrayList<String> words = new ArrayList<>();
        TrieNode temp = this.root;
        for(int i = 0; i < prefix.length(); i++) {
            if(temp.getChildren().get(prefix.charAt(i)) == null) {
                return words;
            }
            temp = temp.getChildren().get(prefix.charAt(i));
        }
        this.dfs(temp, prefix, words);
        return words;
    }

    private void dfs(TrieNode node, String word, ArrayList<String> words) {
        if(node.getEndOfWord()) {
            words.add(word);
        }
        for(Character ch : node.getChildren().keySet()) {
            dfs(node.getChildren().get(ch), word + ch.toString(), words);
        }
    }
}
