package com.example.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Trie {
    TrieNode root = new TrieNode();

    public Trie() {
    }

    /**
     * Get an ArrayList of all word children of the trie and add to start.
     *
     * @param start start string of all children node
     *              (included current node)
     * @return an all word children of the trie ArrayList
     */
    private static ArrayList<String> getAllWordChildren(String start, TrieNode node) {
        ArrayList<String> ans = new ArrayList<>();
        if (node.isEndOfWord) {
            ans.add(start);
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            ans.addAll(getAllWordChildren(start + entry.getKey(), entry.getValue()));
        }
        return ans;
    }

    /**
     * Remove a word from node.
     *
     * @param word word need to remove.
     * @param node TrieNode is deleted in
     * @return True if success. Otherwise, false.
     */
    private static boolean remove(String word, TrieNode node) {
        if (word.isEmpty()) {
            if (node.isEndOfWord) {
                node.decreaseCntWords();
                node.isEndOfWord = false;
                return true;
            }
            return false;
        }

        if (!node.children.containsKey(word.charAt(0))) {
            return false;
        } else if (remove(word.substring(1), node.children.get(word.charAt(0)))) {
            if (node.children.get(word.charAt(0)).cntWords == 0) {
                node.children.remove(word.charAt(0));
            }
            node.decreaseCntWords();
            return true;
        }
        return false;
    }

    /**
     * Insert a word (has length > 0) to this Trie if this word is not in trie. Otherwise, can not insert.
     *
     * @param word Word need to insert
     * @return True if insert success. Otherwise, false.
     */
    public boolean insert(String word) {
        if (word.isEmpty() || this.contains(word)) {
            return false;
        }

        TrieNode tmp = root;

        for (char c : word.toCharArray()) {
            tmp.increaseCntWords();
            if (!tmp.children.containsKey(c)) {
                TrieNode newChild = new TrieNode();
                tmp.children.put(c, newChild);
            }
            tmp = tmp.children.get(c);
        }
        tmp.increaseCntWords();
        tmp.isEndOfWord = true;

        return true;
    }

    /**
     * Check trie contains a word.
     *
     * @param word a word need check
     * @return true if the word is in the trie. Otherwise, false.
     */
    public boolean contains(String word) {
        TrieNode tmp = root;

        for (char c : word.toCharArray()) {
            if (tmp.children.containsKey(c)) {
                tmp = tmp.children.get(c);
            } else {
                return false;
            }
        }

        return tmp.isEndOfWord;
    }

    /**
     * Remove a word from trie.
     *
     * @param word word need to remove.
     * @return True if success. Otherwise, false.
     */
    public boolean remove(String word) {
        return remove(word, root);
    }

    /**
     * Looks up all words start with prefix.
     *
     * @param prefix prefix of word which needs to find
     * @return an ArrayList<String> which contains all word to look for
     */
    public ArrayList<String> allWordsStartWith(String prefix) {
        TrieNode tmp = root;
        for (char c : prefix.toCharArray()) {
            if (tmp.children.containsKey(c)) {
                tmp = tmp.children.get(c);
            } else {
                return new ArrayList<>();
            }
        }
        return getAllWordChildren(prefix, tmp);
    }

    /**
     * Insert all element of words to the trie.
     *
     * @param words an ArrayList
     */
    public void insertAll(ArrayList<String> words) {
        for (String word : words) {
            insert(word);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Trie otherTrie) {
            return this.root.equals(otherTrie.root);
        }
        return false;
    }

    static class TrieNode {
        private final Map<Character, TrieNode> children = new HashMap<>();
        private int cntWords;
        private boolean isEndOfWord;

        public TrieNode() {
            this.cntWords = 0;
            this.isEndOfWord = false;
        }

        /**
         * Increase cntWords by 1.
         */
        public void increaseCntWords() {
            cntWords++;
        }

        /**
         * Decrease cntWords by 1.
         */
        public void decreaseCntWords() {
            if (cntWords > 0) cntWords--;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof TrieNode o) {
                return this.children.equals(o.children)
                        && this.cntWords == o.cntWords
                        && this.isEndOfWord == o.isEndOfWord;
            }
            return false;
        }
    }
}
