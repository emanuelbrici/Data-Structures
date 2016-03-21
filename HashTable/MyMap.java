import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class MyMap<K,V> implements Iterable<MyMap.MyEntry<K,V>> {
        
	int collision; // maintain the current count of collisions here.
	LinkedList<MyEntry<K, V>>[] bucketArray;
	int size;
	/**
	 * Create a MyMap instance with the specified number of 
	 * buckets.
	 * 
	 * @param buckets the number of buckets to make in this map
	 */
	public MyMap(int buckets) {
		// makes my array list and then adds linked list to each index
		this.bucketArray = (LinkedList<MyEntry<K,V>>[]) new LinkedList[buckets]; 
		for(int i = 0; i < buckets; i++) {
			this.bucketArray[i] = (LinkedList<MyEntry<K,V>>) new LinkedList();
		}
	}
	/**
	 * Puts an entry into the map.  If the key already exists, 
	 *  it's value is updated with the new value and the previous
	 *  value is returned.
	 *
	 * @param key the object used as a key to retrieve the value
	 * @param value the object stored in association with the key
	 * 
	 * @return the previously stored value or null if the key is new
	 */
	public V put(K key, V value) {
		MyEntry<K, V> e = new MyEntry(key, value);
		// gets the hash for the key
		int hCode = key.hashCode();
        // Adds the key in the hash table
		this.bucketArray[hCode].add(e);
        this.size++;
		return null;
	}
	
	/**
	 * Retrieves the value associated with the specified key.  If 
	 * it exists, the value stored with the key is returned, if no 
	 * value has been associated with the key, null is returned.
	 * 
	 * @param key the key object whose value we wish to retrieve
	 * @return the associated value, or null
	 */
	public V get(K key) {
		LinkedList<MyEntry<K,V>> l;
		l = this.bucketArray[key.hashCode()];
       // checks to see if there is a next in the linked list
		for(Iterator iter = l.iterator(); iter.hasNext(); ){
            MyEntry<K,V> entry = (MyEntry<K,V>) iter.next();
            if(entry.key.equals(key)) {
               // returns the value
            	return entry.value;
            }
        }
        return null;
	}
	
	/**
	 *
	 * I've implemented this method, however, you must correctly 
	 * maintain the collisions member variable.
	 *
	 * @return the current count of collisions thus far.
	 */
	public int currentCollisions() {
		int counter = 0;
		int length = this.bucketArray.length;
		// Goes throught the array to see if any are empty
		for (int i = 0; i < length; i++) {
			if (this.bucketArray[i] != null) {
				counter++;
			}
		}
		// adds up all total collision and then subtracts the ones it added twice
		collision = collision - counter;
		return collision;
	}
	/**
	 * Looks through the entire bucket where the specified key
	 * would be found and counts the number of keys in this bucket
	 * that are not equal to the current key, yet still have the
	 * same hash code.
	 * 
	 * @param key
	 * @return a count of collisions
	 */
	public int countCollisions(K key) {
		LinkedList<MyEntry<K,V>> l;
		l = this.bucketArray[key.hashCode()];
		int counter = 0;
        // checks to see if there is a next
		if(l.iterator().hasNext()){
            counter++;
        }
        // sets counter back to 0 if one item in list
		if(counter == 1) {
            counter = 0;
        }
		// subtracts the first item that was added from collisions
		return counter - 1;
	}
	/**
	 * Removes the value associated with the specifed key, if it exists.
	 * @param key the key used to find the value to remove.
	 * @return the value if the key was found, or null otherwise.
	 */
	public V remove(K key) {
        LinkedList<MyEntry<K,V>> l = this.bucketArray[key.hashCode()];
        // looks through the linked list to see if it has the entry
        for(Iterator iter = l.iterator(); iter.hasNext(); ){
            MyEntry<K,V> entry = (MyEntry<K,V>) iter.next();
            // if entry matches key then removes
            if(entry.key.equals(key)) {
                iter.remove();
                size--;
                return entry.value;

            }
        }
		return null;
	}
	/**
	 * Returns the number of entries in this map
	 * @return the number of entries.
	 */
	public int size() {
		int size = 0;
		return size;
	}
	
	/**
	 * Creates and returns a new Iterator object that 
	 * iterates over the keys currently in the map. The iterator 
	 * should fail fast, and does not need to implement the remove
	 * method.
	 * 
	 * @return a new Iterator object  
	 */
	public Iterator<MyEntry<K,V>> iterator() {
		return null;
	}
	
	public static class MyEntry<K,V> {
		K key;
		V value;
		
		public MyEntry(K k, V v) {
			key = k;
			value = v;
		}
	}
	public static void main(String[] args) throws FileNotFoundException {
	        // Create a scanner to read in the words from the bigtext file
	        // you may need to play around with where the text file lives so 
	        // that this works...
            System.out.println(new File("bigtext.txt").getAbsolutePath());
	        Scanner s = new Scanner(new File("src/bigtext.txt"));

	        // create a map that associates Words with Integers.
	        // the map should have 1000 slots in its LinkedList array
	        MyMap<Word, Integer> map = new MyMap<Word, Integer>(Word.BUCKET_SIZE);


	        // now read from that text file.  There are a bit more than 1 million words.
	        String str;
	        Integer value;
	        System.out.println("Reading");
	        int i = 0;
	        while (s.hasNext()) {
	            str = s.next();
	            value = map.get(new Word(str));  // do we have the word in our map? get an Integer or null
	            if (value == null) {
	                // if not put the word in with the associated value 1
	                map.put(new Word(str), 1);  
	            } else {
	                // if there was a value, add one to it
                    map.remove(new Word(str));
                    map.collision++;
	                map.put(new Word(str), value + 1);
	            }
	            i++;
	            if (i % Word.BUCKET_SIZE == 0)
	                System.out.println("Read " + i + " words");

	        }
            map.toString();
	        System.out.println("Counting");

	        System.out.println("Total Collisions: " + map.currentCollisions());
	        /*
	         *
	         * // If you do implement the iterator, try this...
	         * int collisions = 0;
	         *
	         * 
	         * Iterator <Entry<Word,Integer>> iter = map.iterator(); while
	         * (iter.hasNext()){ collisions+= map.countCollisions(iter.next().key);
	         * } System.out.println("Total collisions is "+ collisions);
	        */


	    }



}
