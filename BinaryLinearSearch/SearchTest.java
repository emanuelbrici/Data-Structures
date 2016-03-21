
public class SearchTest {
	
	double[] array = { 1, 3, 4, 5, 6, 10, 14, 3, 8, 9, 2};
	double[] sortedArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 14, 15, 25};
	double key = 6;
	int begin = 0; 
	
	
	
	public boolean testLinearSearch(Search srch){
		if(srch.linearSearch(array, key, begin, array.length-1) == 4 ){
			return true;
		}
		if(srch.linearSearch(array, key, begin, array.length-1) != 4){
			return false;
		}
		if (srch.linearSearch(array,key,begin,array.length-1) == -1){
			return false;
		} else {
			return true; 
		}
	}
	
	public boolean testBinarySearch(Search srch){
		int pass = 0;
		if(srch.binarySearch(sortedArray, key, begin, sortedArray.length-1) == 5 ){
			pass++;
		}
		if (srch.binarySearch(sortedArray, 11, begin,sortedArray.length-1) == -1){
			pass++;
		}
		if (srch.binarySearch(sortedArray, 1, begin,sortedArray.length-1) == 0){
			pass++;
		}
		if(pass == 3) {
			return true;
		}
		 return false;
	}
	
	public String identifySearch(Search srch){
		if (srch.search(array, 2.0, begin, array.length - 1) == -1 ){
			return "Binary Search";
		}
		else {
			return "Linear Search";
		}
	}
	
	public static void main(String [] args){
		Search s = new Search();
		SearchTest st = new SearchTest(); 
		
		System.out.println("Testing linear Search...ok?" 
						+ st.testLinearSearch(s)); 
		System.out.println("Testing Binary Search...ok?" 
				+ st.testBinarySearch(s)); 
		System.out.println("Mystery Search is ..." 
				+ st.identifySearch(s)); 
	}
}
