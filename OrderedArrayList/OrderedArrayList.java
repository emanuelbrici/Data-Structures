
public class OrderedArrayList<T extends Comparable<T>> {

	/** This is an array of Objects of type T */
	private T[] array;


	@SuppressWarnings("unchecked")
	/**
	 * Construct an OrderedArrayList with 10 empty slots. Note the recipe for 
	 * creating an array with the generic type. You'll want to reuse this 
	 * recipe in the insert() method.  You'll also possibly want to tweak this 
	 * constructor a bit if you add other instance variables.
	 */
	public OrderedArrayList() {
		array = (T[]) new Comparable[10];

	}
	@SuppressWarnings("unchecked")
	//This function is used to grow the array by double
	public T[] growArray(T[] oldArray) {
		T[] newArray = (T[]) new Comparable[(oldArray.length - 1) * 2 ];  
		for (int i = 0; i < oldArray.length; i++) {
			newArray[i] = oldArray[i];
		}
		return newArray;
	}
	//@SuppressWarnings("unchecked")
	/**
	 * _Part 1: Implement this method._
	 * 
	 * Inserts a new item in the OrderedArrayList. This method should ensure
	 * that the list can hold the new item, and grow the backing array if
	 * necessary. If the backing array must grow to accommodate the new item, it
	 * should grow by a factor of 2. The new item should be placed in sorted
	 * order using insertion sort. Note that the new item should be placed
	 * *after* any other equivalent items that are already in the list.
	 * 
	 * @return the index at which the item was placed.
	 */
	public int insert(T item) {
		//Check to see if need to grow
		if (array[array.length - 1] != null ) {
			// grow
			array = growArray(array);
		}
		// Insert last
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				continue;
			} else {
				array[i] = item;
				break;
			}
		}
		//Then sort
		int index = 0;
		int j = 0;
		for (int i = 1; i < size(); i++) {
			T temp = array[i];
			j = i - 1;
			while(j >= 0 && array[j] != null && temp != null && array[j].compareTo(temp) >= 1) {
				array[j + 1] = array[j];
				j = j - 1;
			}
			array[j + 1] = temp;
			index = j +1;
		}
		
		return index;  
	}

	/**
	 * _Part 2: Implement this method._
	 * 
	 * @return the number of items in the list.
	 */
	public int size() {      // This method check all spots that are full and stops
		int size = 0;        // once a null spot is reached. Adds 1 if something is in index
		for (int i = 0; i < array.length; i++ ) {
			if (array[i] != null) {
				size++;
			}
		}
		return size;
			
		
	}

	/**
	 * _Part 3: Implement this method._
	 * 
	 * Gets an item from the ordered array list. You can assume that this method
	 * will only be called with valid values of index. Specifically, values that
	 * are in the range 0 - (size-1). To impress your friends and build your
	 * street cred, consider adding checks that the index supplied is in fact in
	 * bounds. If it is not, you can raise an IndexOutOfBoundsException.
	 * 
	 * @param index
	 *            the index to get an item from
	 * @return an item at the specified index
	 */
	public T get(int index) {  // This method goes into the function and 
		return array[index];	// gets what is at the index that is given	
	}

	/**
	 * _Part 3: Implement this method._
	 * 
	 * Counts the items in the ordered array list that are equal to the item at
	 * the specified index. Be sure to take advantage of the fact that the list
	 * is sorted here. You should not have to run through the entire list to
	 * make this count.
	 * 
	 * @param index
	 *            an index in the range 0..(size-1)
	 * @return the number of items in the list equal to the item returned by
	 *         get(index)
	 */
	public int countEquivalent(int index) {
		int count = 1;
		T value = array[index];
		if(value == null) {
			count = 0;
			return count;
		}
		
		// Check left of index
		for (int i = index - 1; i > 0; i--) {
			if (array[i] != null && array[i].equals(value)) {
				count++;
			} else if (array[i] != null && !array[i].equals(value)) {
				break;
			}
		}
		// Check right of index
		for(int i = index + 1; i < array.length; i++) {
			if(array[i] != null && array[i].equals(value)){
				count++;
			} else if (array[i] != null && !array[i].equals(value)) {
				break;
			}
		}
		return count;
	}

	/**
	 * _Part 4: Implement this method._
	 * 
	 * Finds the location of the first object that is equal to the specified
	 * object. Linear search is sufficient here, but feel free to leverage your
	 * binary search code too.
	 * 
	 * @param obj
	 *            an object to search for in the list
	 * @return the first index of an object equal to the one specified, or -1 if
	 *         no such object is found.
	 */
	public int find(T obj) {              //Goes Trough the array and checks each spot and
		for (int i = 0; i < array.length; i++){    // finds the object. If no object -1 is returned
			if (array[i] != null && array[i].equals(obj)) {
				return i;
			}
				
		}
		return -1;
	}

	/**
	 * _Part 5: Implement this method._
	 * 
	 * Removes all the objects equal to the specified object.
	 * 
	 * @param obj
	 *            an object equal to the one(s) you'd like to remove
	 * @return the number of objects removed
	 */
	public int remove(T obj) {     // removes any object that the user wishes to get rid 
		int counter = 0;           // of and places a null in its place
		for(int i = 0; i < array.length; i++){
			if (array[i] != null && array[i].equals(obj)) {
				array[i] = null;
				counter++;
			}
		}
		return counter;
	}
	
	/**
	 * This method is included for testing purposes.
	 * Typically, you would not want to expose a private instance variable
	 * as we are doing here. However, it does have value when the code is 
	 * going through a testing phase. Do not modify or remove this method.
	 * Some WebCAT tests may rely upon it.
	 */
	public Comparable<T>[] dbg_getBackingStore() { return array; }
}
