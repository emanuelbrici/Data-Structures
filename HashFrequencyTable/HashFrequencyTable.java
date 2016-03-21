/**
 * Created by emanuel on 10/26/14.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.io.PrintStream;
public class HashFrequencyTable<K> implements FrequencyTable<K>, Iterable<K> {


    private int totalEntries;
    private ArrayList<Entry> table;
    private float maxLoadFactor;


    public HashFrequencyTable(int initialCapacity, float maxLoadFactor) {
        this.maxLoadFactor = maxLoadFactor;
        int sz = nextPowerOfTwo(initialCapacity);
        table = new ArrayList<Entry>(sz);
        for (int i = 0; i < sz; i++)
            table.add(null);
         totalEntries = 0;
    }

    private static int nextPowerOfTwo(int n) {
        int e = 1;
        while ((1 << e) < n)
            e++;
        return 1 << e;
    }

    private class Entry {
        public K key;
        public int count;
        public Entry(K k) {
            key=k;
            count=1;
        }
    }


    private class TableIterator implements Iterator<K> {
        private int i;
        public TableIterator() {i = 0;}
        public boolean hasNext() {
            while (i < table.size() && table.get(i) == null)
                i++;
            return i < table.size();
        }
        public K next() {
            return table.get(i++).key;
        }
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
        }
    }

    public Iterator<K> iterator() {
        return new TableIterator();
    }

    private float loadFactor() {
        return (float) totalEntries / table.size();
    }
    private void rehashNewTable() {
       // System.out.println("______________________");
        //dump(System.out);
        //System.out.println("______________________\n\n\n");
        ArrayList<Entry> oldTable = table;
        int n = nextPowerOfTwo(oldTable.size()+1);         //increments the size of the table
        table = new ArrayList<Entry>(n);
        for (int i = 0; i < n; i++)                        //makes new table with updated size
            table.add(null);                               //nulls out all i
        totalEntries = 0;
        for (int i = 0; i < oldTable.size(); i++) {
            Entry e = oldTable.get(i);
            while(e != null && e.count > 1) {           //handles the case when count is more then 1
                click(e.key);                           //clicks into hashtable
                e.count -= 1;                           //then decrements count
            }
            if (e != null) {                            //clicks when count is 1
                click(e.key);
            }
        }
    }

    public void click(K key) {
        int i = 0;
        int h = key.hashCode() % table.size();      //gets hashcode of key
        if (h < 0) {                                //makes it positive if not
            h += table.size();
        }
        int k = h;                                  //k is now equal to the hashcode
        Entry e;
        while ((e = table.get(k)) != null) {        // checks to see if the key is already in the table or if somethings there
            if (key.equals(e.key)) {                //if in the table just increment counter
                e.key = key;
                e.count += 1;
                return;
            }
            k = (h + i * (i + 1)/2) % table.size() ;     // if not key do quadratic probing find new index
            if (k < 0) {
                k += table.size() ;
            }
            i++;
        }
        e = new Entry(key);                             // make new entry to be placed in hashTable
        table.set(k, e);
        totalEntries++;
        if (loadFactor() > this.maxLoadFactor) {        // check to see how full hashTable is
            rehashNewTable();                           //rehash if necessary
        }
    }

    public int count(K key) {
        int i = 0;
        int h = key.hashCode() % table.size();          //gets hashcode of key
        int k = h;                                      //k is now equal to the hashcode
        if (k < 0) k += table.size();                   //makes it positive if not
        Entry e;
        while ((e = table.get(k)) != null) {        // checks to see if the key is already in the table or if somethings there
            if (key.equals(e.key))                  //if in the table return counter of that key
                return e.count;


            k = ((h + (i * (i + 1)/2))) % table.size();   // if not key do quadratic probing find new index
            if (k < 0) k += table.size() ;
            i++;


        }

        return 0;                                          // when there is a miss
    }

    public void dump(PrintStream out) {
        int position = 0; // Index of word printed
            for(Iterator i = this.table.iterator(); i.hasNext();) {   // Iterator for the table we created, check if hasNext()
            Entry e = (Entry)i.next();
                if (e == null) {
                out.println(position+ ":" +" null");              //  when there is a null entry
            } else {
                out.println(position + ":" + " key='" + e.key + "'," +  " count=" + e.count);  // prints out key and counter
            }
            position++;
        }

    }

    public static void main(String[] args) {
        String hamlet =
                "To be or not to be that is the question " +
                        "Whether 'tis nobler in the mind to suffer " +
                        "The slings and arrows of outrageous fortune ";
        String words[] = hamlet.split("\\s+");
        HashFrequencyTable<String> table = new HashFrequencyTable<String>(10, 0.95F);
        for (int i = 0; i < words.length; i++)
            if (words[i].length() > 0)
                table.click(words[i]);
        table.dump(System.out);
    }

}
