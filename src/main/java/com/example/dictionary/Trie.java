package com.example.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Trie {
    private final Map<Character, Trie> children = new HashMap<>();
    private Character nodeContent;
    private int cntWords;
    private boolean endOfWord;

    public Trie() {
        this.nodeContent = '\0';
        this.cntWords = 0;
        this.endOfWord = false;
    }

    public Trie(Character nodeContent) {
        this.nodeContent = nodeContent;
        this.endOfWord = false;
        this.cntWords = 0;
    }

    /**
     * Insert a word (has length > 0) to this Trie if this word is not in trie. Otherwise, can not insert.
     * @param word  Word need to insert
     * @return  True if insert success. Otherwise, false.
     */
    public boolean insert(String word) {
        if(word.isEmpty() || this.contains(word)) {
            return false;
        }

        Trie tmp = this;

        for(char c: word.toCharArray()) {
            tmp.increaseCntWords();
            if(! tmp.children.containsKey(c)) {
                Trie newChild = new Trie(c);
                tmp.children.put(c, newChild);
            }
            tmp = tmp.children.get(c);
        }
        tmp.increaseCntWords();
        tmp.setEndOfWord(true);

        return true;
    }

    /**
     * Check trie contains a word.
     * @param word a word need check
     * @return  true if the word is in the trie. Otherwise, false.
     */
    public boolean contains(String word) {
        Trie tmp = this;

        for(char c: word.toCharArray()) {
            if(tmp.children.containsKey(c)) {
                tmp = tmp.children.get(c);
            }
            else {
                return false;
            }
        }

        return tmp.isEndOfWord();
    }

    /**
     * Remove a word from trie.
     * @param word  word need to remove.
     * @return  True if success. Otherwise, false.
     */
    public boolean remove(String word) {
        if(word.isEmpty()) {
            if(this.isEndOfWord()) {
                this.decreaseCntWords();
                this.setEndOfWord(false);
                return true;
            }
            return false;
        }

        if(! children.containsKey(word.charAt(0))) {
            return false;
        }
        else if(children.get(word.charAt(0)).remove(word.substring(1))) {
            if(children.get(word.charAt(0)).getCntWords() == 0) {
                children.remove(word.charAt(0));
            }
            this.decreaseCntWords();
            return true;
        }
        return false;
    }

    /**
     * Looks up all words start with prefix.
     * @param prefix    prefix of word which needs to find
     * @return  an ArrayList<String> which contains all word to look for
     */
    public ArrayList<String> allWordsStartWith(String prefix) {
        Trie tmp = this;

        for(char c: prefix.toCharArray()) {
            if(tmp.children.containsKey(c)) {
                tmp = tmp.children.get(c);
            }
            else {
                return new ArrayList<>();
            }
        }
        if(prefix.length() <= 1) {
            return tmp.getAllWordChildren("");
        }
        return tmp.getAllWordChildren(prefix);
    }

    /**
     * Get an ArrayList of all word children of the trie and add to start.
     * @param start start string of all children node
     *              (included current node)
     * @return  an all word children of the trie ArrayList
     */
    private ArrayList<String> getAllWordChildren(String start) {
        ArrayList<String> ans = new ArrayList<>();
        if(this.isEndOfWord()) {
            ans.add(start);
        }

        for(Trie child: children.values()) {
            ans.addAll(child.getAllWordChildren(start + child.getNodeContent()));
        }
        return ans;
    }

    /**
     * Insert all element of words to the trie.
     * @param words an ArrayList
     */
    public void insertAll(ArrayList<String> words) {
        for(String word: words) {
            insert(word);
        }
    }

    /**
     * Increase cntWords by 1.
     */
    public void increaseCntWords() {
        cntWords ++;
    }

    /**
     * Decrease cntWords by 1.
     */
    public void decreaseCntWords() {
        if(cntWords > 0) cntWords --;
    }

    public boolean isEndOfWord() {
        return endOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

    public Character getNodeContent() {
        return nodeContent;
    }

    public void setNodeContent(Character nodeContent) {
        this.nodeContent = nodeContent;
    }

    public int getCntWords() {
        return cntWords;
    }

    public void setCntWords(int cntWords) {
        this.cntWords = cntWords;
    }

    public Map<Character, Trie> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Trie otherTrie) {
            return this.nodeContent.equals(otherTrie.nodeContent)
                    && this.cntWords == otherTrie.cntWords
                    && this.endOfWord == otherTrie.endOfWord
                    && this.children.equals(otherTrie.children);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Trie{" +
                "children=" + children +
                ", nodeContent=" + nodeContent +
                ", cntWords=" + cntWords +
                ", endOfWord=" + endOfWord +
                '}';
    }
}
