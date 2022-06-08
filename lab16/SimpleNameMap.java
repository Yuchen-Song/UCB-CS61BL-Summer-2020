
import java.util.LinkedList;

public class SimpleNameMap {

    /* TODO: Instance variables here */

    LinkedList<Entry>[] buckets;
    double loadFactor;

    public SimpleNameMap() {
        buckets = new LinkedList[26];
        loadFactor = 0.75;

    LinkedList<Entry>[] bucket; //? refers to Entry
    private int posInList;
    private int size = 0;
    private final double loadFactor = 0.75;

    public SimpleNameMap() {
        bucket = new LinkedList[10];

    }

    /* Returns the number of items contained in this map. */
    public int size() {
<<<<<<< HEAD
        int sum = 0;
        for(LinkedList bucket : buckets){
            sum += bucket.size();
        }
        return sum;
=======
        return size;
>>>>>>> c4049aa73e6a547491df3d5f35db5cac8a3bcd61
    }

    /* Returns true if the map contains the KEY. */
    public boolean containsKey(String key) {
        int index = Math.floorMod(hashCode(key), buckets.length);
        Entry ent = new Entry(key, null);
        if(buckets[index] == null){
            return false;
        }
        for(Entry e : buckets[index]){
            if(e.keyEquals(ent)){
        LinkedList<Entry> l = bucket[hash(key)];
        if (l == null) {
            return false;
        }
        for (Entry entry : l) {
            if (entry.key.equals(key)) {
                posInList = l.indexOf(entry);
                return true;
            }
        }
        return false;
    }

    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    public String get(String key) {
        int index = Math.floorMod(hashCode(key), buckets.length);
        Entry ent = new Entry(key, null);
        for(Entry e : buckets[index]){
            if(e.keyEquals(ent)){
                return e.value;
            }

        if (containsKey(key)) {
            return bucket[hash(key)].get(posInList).value;

        }
        return null;
    }

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       SimpleNameMap, replace the current corresponding value with VALUE. */
    public void put(String key, String value) {
        int index = Math.floorMod(hashCode(key), buckets.length);
        Entry e = new Entry(key, value);
        resize();
        buckets[index].add(e);

        if (containsKey(key)) {
            bucket[hash(key)].get(posInList).value = value;
        } else {
            if ((double)size/ (double) bucket.length >= loadFactor) {
                resize();
            }
            Entry newEntry = new Entry(key, value);
            if (bucket[hash(key)] == null) {
                bucket[hash(key)] = new LinkedList<>();
            }
            bucket[hash(key)].addLast(newEntry);
            size++;
        }
            }

    /* Removes a single entry, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */
    public String remove(String key) {
        int index = Math.floorMod(hashCode(key), buckets.length);
        Entry ent = new Entry(key, null);
        for(Entry e : buckets[index]){
            if(e.keyEquals(ent)){
               buckets[index].remove(e);
               return e.value;
            }
        if (containsKey(key)) {
            String temp = bucket[hash(key)].get(posInList).value;
            Entry e = bucket[hash(key)].get(posInList);
            e = new Entry(null, null);
            size--;
            return temp;
        }
        return null;
    }

    public int hashCode(String key){
        return key.hashCode();
    }

    public void resize(){
        if(loadFactor < size() / buckets.length){
            LinkedList<Entry>[] newBuckets = new LinkedList[buckets.length * 2];
            for(int i = 0; i < buckets.length; i++){
                newBuckets[i] = buckets[i];
            }
        }
    }

    //return the index of a given key at the SNM
    public int hash(String key) {
        int module = key.hashCode();
        return Math.floorMod(module, bucket.length);
    }

    public void resize() {
        LinkedList<Entry>[] temp = bucket;
        size = 0;
        bucket = new LinkedList[bucket.length * 2];
        for (LinkedList<Entry> l:temp) {
            if (l == null) {
                continue;
            }
            for (Entry e:l) {
                put(e.key, e.value);
            }
        }
    }

    private static class Entry {

        private String key;
        private String value;

        Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
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
        SimpleNameMap s = new SimpleNameMap();
        int i = s.size();
        s.put("Yuchen", "Ji");
        s.put("Daniel", "Yun");
        s.put("Wei","Li");
        s.put("Yichen", "Lv");
        s.put("Kichen", "Lv");
        s.put("Aichen", "Lv");
        s.put("Y", "Song");
        s.put("Yu", "Song");
        s.put("Yuc", "Song");
        s.put("Yuchen", "Song");
        int l = s.bucket.length;
        String str = s.get("Yi");
        s.put("Yuchen", "Song");
        str = s.get("Yuchen");
        str = s.remove("Wei");
        i = s.size();
    }
}