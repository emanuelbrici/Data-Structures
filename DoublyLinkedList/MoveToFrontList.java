//Emanuel Brici
//LinkedList
//CS 122

public class MoveToFrontList {

	private StringCountElement head; // the head reference
	private StringCountElement tail; // the tail reference
	private int size; // the size of the list (number of valid items)
	/**
	 * _Part 1: Implement this constructor._
	 * 
	 * Creates a new, initially empty MoveToFontList. This list should be a
	 * linked data structure.
	 */
	public MoveToFrontList() {
		//Makes Head and Tail String count element
		head = new StringCountElement();
		tail = new StringCountElement();
		head.prev = null;       // Creation on sentinel for head and tail
		head.key = "b3gin";    // Keys are set to b3gin and 3nd to know they are not real data
		tail.next = null;      // head and tail are referenced to each other
		tail.key = "3nd";
		tail.prev = head;
		head.next = tail;
	}

	/**
	 * This method increments the count associated with the specified string
	 * key. If no corresponding key currently exists in the list, a new list
	 * element is created for that key with the count of 1. When this method
	 * returns, the key will have rank 0 (i.e., the list element associated with
	 * the key will be at the front of the list)
	 * 
	 * @param key
	 *            the string whose count should be incremented
	 * @return the new count associated with the key
	 */
	public int incrementCount(String key) {
		StringCountElement s = find(key);
		if (s != null) {
			// found the key, splice it out and increment the count
			spliceOut(s);
			s.count++;
		} else {
			// need to create a new element
			s = new StringCountElement();
			s.key = key;
			s.count = 1;
		}
		// move it to the front
		spliceIn(s, 0);
		return s.count;
	}

	/**
	 * 
	 * @return the number of items in the list
	 */
	public int size() {
		return size;
	}

	/**
	 * _Part 2: Implement this method._
	 * 
	 * Find the list element associated with the specified string. That is, find
	 * the StringCountElement that a key equal to the one specified
	 * 
	 * @param key
	 *            the key to look for
	 * @return a StringCountElement in the list with the specified key or null
	 *         if no such element exists.
	 */
	public StringCountElement find(String key) {
		// Tmp is made and is set to head 
		StringCountElement tmp = new StringCountElement();
		tmp = head; 	
		// Check element if tmp.next (head.next) doesn't equal null
		while (tmp.next != null) {  
			if(key.equalsIgnoreCase(tmp.next.key)){
				return tmp.next;
			}
			//moves tmp to next element in line
			tmp = tmp.next;
		}
		
		return null;
	}

	/**
	 * _Part 3: Implement this method._
	 * 
	 * Compute the rank of the specified key. Rank is similar to position, so
	 * the first element in the list will have rank 0, the second element will
	 * have rank 1 and so on. However, an item that does not exist in the list
	 * also has a well defined rank, which is equal to the size of the list. So,
	 * the rank of any item in an empty list is 0.
	 * 
	 * @param key
	 *            the key to look for
	 * @return the rank of that item in the rank 0...size() inclusive.
	 */
	public int rank(String key) {
		int rank = 0;
		//tmp is assigned to head
		StringCountElement tmp = head;
		//moves through list looking for key and 
		//increments rank by one if it moves to the next element
		while (tmp.next != null && rank < size){
			if(key.equalsIgnoreCase(tmp.next.key)){
				return rank;
			}
			rank++;
			tmp = tmp.next;
		}
		return size;     
	}

	/**
	 * _Part 4: Implement this method._
	 * 
	 * Splice an element into the list at a position such that it will obtain
	 * the desired rank. The element should either be new, or have been spliced
	 * out of the list prior to being spliced in. That is, it should be the case
	 * that: s.next == null && s.prev == null
	 * 
	 * @param s
	 *            the element to be spliced in to the list
	 * @param desiredRank
	 *            the desired rank of the element
	 */
	public void spliceIn(StringCountElement s, int desiredRank) {
		// increment size in here
		size++;
		// creation of tmp and tmptail
		StringCountElement tmp = new StringCountElement();
		StringCountElement tmpTail = new StringCountElement();
		tmpTail = tail;
		tmp = head;
		int counter = 0;
		// Deal with empty list. only have sentinels
		//spliceIn
		if (tmp.next.key.equalsIgnoreCase("3nd")) {
			tmp.next = s;
			s.prev = tmp;
			s.next = tmpTail;
			tmpTail.prev = s;
			return; 
		}
		// list is not empty in this case	
		while (tmp.next != null) {
			tmp = tmp.next;
			if (desiredRank <= counter){
				
				if (desiredRank == counter) {
					//SpliceIn at the front of the list
					if(desiredRank == 0){
						break;
					}
					tmpTail = tmp;
					tmp = tmpTail.prev;
					
					tmp.next = s;   
					s.prev = tmp;
					s.next = tmpTail;
					tmpTail.prev = s;
					return;
					
				} 		
			}
			counter++;
		}
		//SpliceIn at different ranks in the list
		tmpTail = tmp; // sets the element in front of s equal to head
		tmp = tmpTail.prev; // makes head which is after s equal to tail
		
		tmp.next = s;   // insert element s
		s.prev = tmp;
		s.next = tmpTail;
		tmpTail.prev = s;
	}
		
		
		
	

	/**
	 * _Part 5: Implement this method._
	 * 
	 * Splice an element out of the list. When the element is spliced out, its
	 * next and prev references should be set to null so that it can safely be
	 * splicedIn later. Splicing an element out of the list should simply remove
	 * that element while maintaining the integrity of the list.
	 * 
	 * @param s
	 *            the element to be spliced out of the list
	 */
	public void spliceOut(StringCountElement s) {
		if (head.next.key.equalsIgnoreCase("3nd")){
			return; // nothing in the list 
		}
		// one element in the list or multiple elements in list
		// spliceOut desired element
		if(!head.next.key.equalsIgnoreCase("3nd")  && !tail.prev.key.equalsIgnoreCase("b3gin")){
			s.prev.next = s.next;
			s.next.prev = s.prev;
			s.next = null;
			s.prev = null;
		}
		// size gets smaller when removing item
		size--;
	}

}
