
public class Search {
	
	public int linearSearch(double[] array, double key, int begin, int end){ 
		int index = -1;
		// change index to valid index, IF applicable
		for (int i = begin; i <= end; i++) {
			if (array[i] == key) {
				index = i;
			}
		}
	    return index;
	      
	}      
	public int binarySearch(double[] sortedArray, double key, int begin, int end){ 
		int index = -1;
		
		int midpoint = (begin + end) / 2; 
		if (end < begin){
			return index;
		}
		// change index to valid index, IF applicable
		if (sortedArray[midpoint] == key) {
			return midpoint;
	    }
		if (sortedArray[midpoint] > key){
			return binarySearch(sortedArray, key, begin ,midpoint -1);
		}
		if (sortedArray[midpoint] < key){
			return binarySearch(sortedArray, key, midpoint +1, end);
		}
		return index;
	}
	
	public int search(double[] array, double key, int begin, int end){
		return binarySearch(array, key, begin, end);
	}

}
