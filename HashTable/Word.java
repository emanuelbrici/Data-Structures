
public class Word {
	
	// After doing so research online i now know why extanding String wont work
	// It is because the String class is a Final class.
	// It is a final class and you are not allowed 
	// to extend from a final class.
	
	public static int BUCKET_SIZE = 5000;
	private String wordToHash; 
	/**
	 * Creates a Word object representing the specified String
	 * 
	 * @param w the String version of this word.
	 */
	public Word(String w) {
		this.wordToHash = w; 
	}
	
	/**
	 * Returns a hashcode for this Word -- an integer whose value is based on the 
	 * word's instance data. Words that are .equals() *must* have the same hashcode.
	 * However, the converse need not hold -- that is, it *is*  acceptable for 
	 * two words that are not .equals() to have the same hashcode.
	 */
	/*
	public int hashCode() {
		char ch[];
		ch = wordToHash.toCharArray();
		int length = wordToHash.length();
		
		int i;
		int sum;
		for (i = 0, sum = 0; i < length; i++){
			sum += ch[i];
		}
		return Math.abs(sum % BUCKET_SIZE);		
	}
	*/
	public int hashCode() {
		char ch[];
		ch = wordToHash.toCharArray();
		int length = wordToHash.length();
		
		int i;
		int sum;
		for (i = 0, sum = 7; i < length; i++){
			sum *= ch[i];
		}
		return Math.abs(sum % BUCKET_SIZE);		
	}
	
	/**
	 * Returns true if and only if this Word object represents the same
	 * sequence of characters as the specified object. Here, you can assume
	 * that the object being passed in will be a Word. 
	 */
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof Word)) {
			return false;
		}
		Word w = (Word) o; 
		char thisW[] = this.wordToHash.toCharArray();
		char thatW[] = w.wordToHash.toCharArray();
		// Check to see if the two strings are the same length 
		if (!(this.wordToHash.length() == w.wordToHash.length())) {
			return false;
		}
		for (int i = 0; i < this.wordToHash.length(); i++) {
			if (thisW[i] == thatW[i]) {
				//do nothing
			} else { 
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method returns the string representation of the object.
	 * A correct implementation will return the String representation of the
	 * word that is actually being stored. ie., if you had a word object representing
	 * 'hi', it should return 'hi'
	 */
	public String toString() {
		return wordToHash;
	}
}
