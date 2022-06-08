import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class MyTrieSet implements TrieSet61BL{
    private Node root;
    private static class Node {
        private char ch;
        private boolean isKey;
        private HashMap<Character, Node> map;
        private Node() {
        }
        private Node(char c, boolean b) {
            this();
            ch = c;
            isKey = b;
        }
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean contains(String key) {
        if (key == null || key.length() < 1 || root == null) {
            return false;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                return false;
            }
            curr = curr.map.get(c);
            if (i == key.length()-1) {
                if (!curr.isKey) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        if (root == null) {
            root = new Node();
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            if (curr.map == null) {
                curr.map = new HashMap<>();
            }
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            return null;
        }
        Node curr = root;
        List<String> ret = new LinkedList<>();
        String newPrefix = "";
        //if prefix is not in the trie, then return null
        for (int i = 0, n = prefix.length(); i < n; i++) {
            char c = prefix.charAt(i);
            if (curr.map == null || !curr.map.containsKey(c)) {
                return null;
            }
            curr = curr.map.get(c);
            if (i < n-1) {
                newPrefix += String.valueOf(c);
            }
        }
        int index = 0;
        for (String s : keysWithPrefixHelper(curr)) {
            ret.add(index, newPrefix + s);
            index ++;
        }
        return ret;
    }

    //return the string list of all "word" under node n
    private List<String> keysWithPrefixHelper(Node n) {
        List<String> ret = new LinkedList<>();
        if (n.isKey) {
            ret.add(String.valueOf(n.ch));
            if (n.map == null) {
                return ret;
            }
        }
        for (char k : n.map.keySet()) {
            for (String s: keysWithPrefixHelper(n.map.get(k))) {
                ret.add(String.valueOf(n.ch) + s);
            }
        }
        return ret;
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        MyTrieSet t = new MyTrieSet();
        boolean b = t.contains("h");
        t.add("h");
        t.add("hello");
        t.add("hi");
        t.add("help");
        t.add("zebra");
        t.add("sam");
        t.add("same");
        t.add("samoe");
        b = t.contains("hel");
        b = t.contains("hi");
        List<String> s = t.keysWithPrefix("hel");
        s = t.keysWithPrefix("sam");
        s = t.keysWithPrefix("");
        s = t.keysWithPrefix("h");
        t.clear();
    }
}
