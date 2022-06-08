
import java.util.Iterator;
import java.util.LinkedList;


public class HashMap<K, V> implements Map61BL<K, V> {
    LinkedList<Entry<K,V>>[] bucket; //? refers to Entry
    private int posInList;
    private int size = 0;
    private double loadFactor;
    private int initialCapacity;
    public HashMap(){
        bucket = new LinkedList[16];
        loadFactor = 0.75;
        initialCapacity = 16;
    }

    HashMap(int initialCapacity) {
        bucket = new LinkedList[initialCapacity];
        loadFactor = 0.75;
        this.initialCapacity = initialCapacity;
    }

    HashMap(int initialCapacity, double loadFactor) {
        bucket = new LinkedList[initialCapacity];
        this.loadFactor = loadFactor;
        this.initialCapacity = initialCapacity;
    }

    public int capacity() {
        return bucket.length;
    }

    @Override
    public void clear() {
        bucket = new LinkedList[initialCapacity];
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        LinkedList<Entry<K,V>> l = bucket[hash(key)];
        if (l == null) {
            return false;
        }
        for (Entry<K,V> entry : l) {
            if (entry.key == null) {
                continue;
            }
            if (entry.key.equals(key)) {
                posInList = l.indexOf(entry);
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        if (containsKey(key)) {
            return (V) bucket[hash(key)].get(posInList).value;
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        if (containsKey(key)) {
            bucket[hash(key)].get(posInList).value = value;
        } else {
            if ((double) (size + 1)/ (double) bucket.length > loadFactor) {
                resize();
            }
            Entry<K,V> newEntry = new Entry<>(key, value);
            if (bucket[hash(key)] == null) {
                bucket[hash(key)] = new LinkedList<>();
            }
            bucket[hash(key)].addLast(newEntry);
            size++;
        }
    }

    @Override
    public V remove(K key) {
        if (containsKey(key)) {
            V temp = (V) bucket[hash(key)].get(posInList).value;
            bucket[hash(key)].set(posInList, new Entry<>(null, null));
            size--;
            return temp;
        }
        return null;
    }

    @Override
    public boolean remove(K key, V value) {
        if (containsKey(key) && bucket[hash(key)].get(posInList).value.equals(value)) {
            bucket[hash(key)].set(posInList, new Entry<>(null, null));
            size--;
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public int hash(K key) {
        int module = key.hashCode();
        return Math.floorMod(module, bucket.length);
    }

    public void resize() {
        LinkedList<Entry<K,V>>[] temp = bucket;
        size = 0;
        bucket = new LinkedList[bucket.length * 2];
        for (LinkedList<Entry<K,V>> l:temp) {
            if (l == null) {
                continue;
            }
            for (Entry<K,V> e:l) {
                put((K) e.key, (V) e.value);
            }
        }
    }

    private static class Entry<K, V> {

        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(HashMap.Entry<K,V> other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry) other).key)
                    && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

    }

    public static void main(String[] args) {
        HashMap<String, Integer> studentIDs = new HashMap<String, Integer>();
        studentIDs.put("zoe", 12345);
        studentIDs.put("jay", 345);
        boolean b = studentIDs.containsKey("Jay");
    }
}

