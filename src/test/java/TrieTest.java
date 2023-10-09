import com.example.dictionary.Trie;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TrieTest {
    @Test
    public void equalsTest1() {
        Trie trie1 = new Trie();
        Trie trie2 = new Trie();
        Assert.assertEquals(trie1, trie2);
    }

    @Test
    public void equalsTest2() {
        Trie trie1 = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList(new String[]{"abc", "bcd"}));
        trie1.insertAll(arrayList1);
        Trie trie2 = new Trie();
        ArrayList<String> arrayList2 = new ArrayList<String>(Arrays.asList(new String[]{"abc", "bcd"}));
        trie2.insertAll(arrayList2);
        Assert.assertEquals(trie1, trie2);
    }

    @Test
    public void equalsTest3() {
        Trie trie1 = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList(new String[]{"bcd", "abc", "bcd"}));
        trie1.insertAll(arrayList1);
        Trie trie2 = new Trie();
        ArrayList<String> arrayList2 = new ArrayList<String>(Arrays.asList(new String[]{"abc", "bcd"}));
        trie2.insertAll(arrayList2);
        Assert.assertEquals(trie1, trie2);
    }

    @Test
    public void equalsTest4() {
        Trie trie1 = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList(new String[]{"bcd", "abc", "ab"}));
        trie1.insertAll(arrayList1);
        Trie trie2 = new Trie();
        ArrayList<String> arrayList2 = new ArrayList<String>(Arrays.asList(new String[]{"abc", "bcd"}));
        trie2.insertAll(arrayList2);
        Assert.assertNotEquals(trie1, trie2);
    }

    @Test
    public void allWordsStartWithTest1() {
        Trie trie = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList(new String[]{"bcd", "abc", "ab"}));
        trie.insertAll(arrayList1);
        String prefix = "";
        ArrayList<String> actual = trie.allWordsStartWith(prefix);
        Collections.sort(actual);
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(new String[]{"bcd", "abc", "ab"}));
        Collections.sort(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void allWordsStartWithTest2() {
        Trie trie = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList(new String[]{""}));
        trie.insertAll(arrayList1);
        String prefix = "";
        ArrayList<String> actual = trie.allWordsStartWith(prefix);
        Collections.sort(actual);
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(new String[]{}));
        Collections.sort(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void allWordsStartWithTest3() {
        Trie trie = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList(new String[]{"abc", "abcde", "abcdef", "acef"}));
        trie.insertAll(arrayList1);
        String prefix = "ab";
        ArrayList<String> actual = trie.allWordsStartWith(prefix);
        Collections.sort(actual);
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(new String[]{"abc", "abcde", "abcdef"}));
        Collections.sort(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void allWordsStartWithTest4() {
        Trie trie = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList(new String[]{"abc", "abcde", "abcdef", "acef", "trie", "tricky", "lec"}));
        trie.insertAll(arrayList1);
        String prefix = "t";
        ArrayList<String> actual = trie.allWordsStartWith(prefix);
        Collections.sort(actual);
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(new String[]{"trie", "tricky"}));
        Collections.sort(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void allWordsStartWithTest5() {
        Trie trie = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList(new String[]{"abc", "abcde", "abcdef", "acef", "trie", "tricky", "lec"}));
        trie.insertAll(arrayList1);
        String prefix = "";
        ArrayList<String> actual = trie.allWordsStartWith(prefix);
        Collections.sort(actual);
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(new String[]{"abc", "abcde", "abcdef", "acef", "trie", "tricky", "lec"}));
        Collections.sort(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void insertAllTest1() {
        Trie trie1 = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList(new String[]{"a"}));
        trie1.insertAll(arrayList1);
        Trie trie2 = new Trie();
        trie2.getChildren().put('a', new Trie('a'));
        trie2.getChildren().get('a').setEndOfWord(true);
        trie2.getChildren().get('a').setCntWords(1);
        trie2.setCntWords(1);
        System.out.println(trie1);
        System.out.println(trie2);
        Assert.assertEquals(trie2, trie1);
    }

    @Test
    public void removeTest1() {
        Trie trie1 = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList(new String[]{"bcd", "abc", "ab"}));
        trie1.insertAll(arrayList1);
        trie1.remove("ab");
        System.out.println(trie1.allWordsStartWith(""));
        Trie trie2 = new Trie();
        ArrayList<String> arrayList2 = new ArrayList<String>(Arrays.asList(new String[]{"abc", "bcd"}));
        trie2.insertAll(arrayList2);
        System.out.println(trie2.getCntWords());
        Assert.assertEquals(trie1, trie2);
    }

    @Test
    public void removeTest2() {
        Trie trie1 = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList(new String[]{"bcd", "abc", "ab"}));
        trie1.insertAll(arrayList1);
        trie1.remove("a");
        Trie trie2 = new Trie();
        ArrayList<String> arrayList2 = new ArrayList<String>(Arrays.asList(new String[]{"abc", "bcd", "ab"}));
        trie2.insertAll(arrayList2);
        Assert.assertEquals(trie1, trie2);
    }

    @Test
    public void removeTest3() {
        Trie trie1 = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList(new String[]{"xyzt", "abcd", "acs"}));
        trie1.insertAll(arrayList1);
        trie1.remove("");
        Trie trie2 = new Trie();
        ArrayList<String> arrayList2 = new ArrayList<String>(Arrays.asList(new String[]{"xyzt", "abcd", "acs"}));
        trie2.insertAll(arrayList2);
        Assert.assertEquals(trie1, trie2);
    }

    @Test
    public void removeTest4() {
        Trie trie1 = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList(new String[]{"xyzt", "abcd", "acs"}));
        trie1.insertAll(arrayList1);
        trie1.remove("acdcuvhf");
        Trie trie2 = new Trie();
        ArrayList<String> arrayList2 = new ArrayList<String>(Arrays.asList(new String[]{"xyzt", "abcd", "acs"}));
        trie2.insertAll(arrayList2);
        Assert.assertEquals(trie1, trie2);
    }
}
