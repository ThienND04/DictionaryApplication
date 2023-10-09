import com.example.dictionary.Trie;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList("abc", "bcd"));
        trie1.insertAll(arrayList1);
        Trie trie2 = new Trie();
        ArrayList<String> arrayList2 = new ArrayList<String>(Arrays.asList("abc", "bcd"));
        trie2.insertAll(arrayList2);
        Assert.assertEquals(trie1, trie2);
    }

    @Test
    public void equalsTest3() {
        Trie trie1 = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList("bcd", "abc", "bcd"));
        trie1.insertAll(arrayList1);
        Trie trie2 = new Trie();
        ArrayList<String> arrayList2 = new ArrayList<String>(Arrays.asList("abc", "bcd"));
        trie2.insertAll(arrayList2);
        Assert.assertEquals(trie1, trie2);
    }

    @Test
    public void equalsTest4() {
        Trie trie1 = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList("bcd", "abc", "ab"));
        trie1.insertAll(arrayList1);
        Trie trie2 = new Trie();
        ArrayList<String> arrayList2 = new ArrayList<String>(Arrays.asList("abc", "bcd"));
        trie2.insertAll(arrayList2);
        Assert.assertNotEquals(trie1, trie2);
    }

    @Test
    public void allWordsStartWithTest1() {
        Trie trie = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList("bcd", "abc", "ab"));
        trie.insertAll(arrayList1);
        String prefix = "";
        ArrayList<String> actual = trie.allWordsStartWith(prefix);
        Collections.sort(actual);
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("bcd", "abc", "ab"));
        Collections.sort(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void allWordsStartWithTest2() {
        Trie trie = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList(""));
        trie.insertAll(arrayList1);
        String prefix = "";
        ArrayList<String> actual = trie.allWordsStartWith(prefix);
        Collections.sort(actual);
        ArrayList<String> expected = new ArrayList<>(List.of());
        Collections.sort(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void allWordsStartWithTest3() {
        Trie trie = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList("abc", "abcde", "abcdef", "acef"));
        trie.insertAll(arrayList1);
        String prefix = "ab";
        ArrayList<String> actual = trie.allWordsStartWith(prefix);
        Collections.sort(actual);
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("abc", "abcde", "abcdef"));
        Collections.sort(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void allWordsStartWithTest4() {
        Trie trie = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList("abc", "abcde", "abcdef", "acef", "trie", "tricky", "lec"));
        trie.insertAll(arrayList1);
        String prefix = "t";
        ArrayList<String> actual = trie.allWordsStartWith(prefix);
        Collections.sort(actual);
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("trie", "tricky"));
        Collections.sort(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void allWordsStartWithTest5() {
        Trie trie = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList("abc", "abcde", "abcdef", "acef", "trie", "tricky", "lec"));
        trie.insertAll(arrayList1);
        String prefix = "";
        ArrayList<String> actual = trie.allWordsStartWith(prefix);
        Collections.sort(actual);
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("abc", "abcde", "abcdef", "acef", "trie", "tricky", "lec"));
        Collections.sort(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void insertAllTest1() {
        Trie trie1 = new Trie();
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList("a", "tdcec", "abcd", "a", "abc", "tde"));
        trie1.insertAll(arrayList);
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("tdcec", "abcd", "a", "abc", "tde"));
        Collections.sort(expected);
        ArrayList<String> actual = trie1.allWordsStartWith("");
        Collections.sort(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void removeTest1() {
        Trie trie1 = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList("bcd", "abc", "ab"));
        trie1.insertAll(arrayList1);
        trie1.remove("ab");
        System.out.println(trie1.allWordsStartWith(""));
        Trie trie2 = new Trie();
        ArrayList<String> arrayList2 = new ArrayList<String>(Arrays.asList("abc", "bcd"));
        trie2.insertAll(arrayList2);
        Assert.assertEquals(trie1, trie2);
    }

    @Test
    public void removeTest2() {
        Trie trie1 = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList("bcd", "abc", "ab"));
        trie1.insertAll(arrayList1);
        trie1.remove("a");
        Trie trie2 = new Trie();
        ArrayList<String> arrayList2 = new ArrayList<String>(Arrays.asList("abc", "bcd", "ab"));
        trie2.insertAll(arrayList2);
        Assert.assertEquals(trie1, trie2);
    }

    @Test
    public void removeTest3() {
        Trie trie1 = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList("xyzt", "abcd", "acs"));
        trie1.insertAll(arrayList1);
        trie1.remove("");
        Trie trie2 = new Trie();
        ArrayList<String> arrayList2 = new ArrayList<String>(Arrays.asList("xyzt", "abcd", "acs"));
        trie2.insertAll(arrayList2);
        Assert.assertEquals(trie1, trie2);
    }

    @Test
    public void removeTest4() {
        Trie trie1 = new Trie();
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList("xyzt", "abcd", "acs"));
        trie1.insertAll(arrayList1);
        trie1.remove("acdcuvhf");
        Trie trie2 = new Trie();
        ArrayList<String> arrayList2 = new ArrayList<String>(Arrays.asList("xyzt", "abcd", "acs"));
        trie2.insertAll(arrayList2);
        Assert.assertEquals(trie1, trie2);
    }
}
