import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class SlowPQ {
	int [] array;
	int size;
	
	public SlowPQ() {
		// Builds an array that is 100000 slots big
		array = new int[100000];
	}
	
	public void add( int e ) {	
		// Adds element is next available spot
		array[size] = e;
		//Increment size
		size++;
				
	}
	//Removes the smallest value
	public int removeMin() { 
		//Where to start looking
		int elementToCheck = array[0];
		//smallest element
		int smallestElement = 0;
		int count = 0;
		
		//looks for smallest element in array
		for (int i = 0; i < size; i++) {
			if(array[i] < elementToCheck) {
				//smallest array is help onto if found
				smallestElement = array[i];
				elementToCheck = smallestElement; 
				count = i;
			}
			
		}
		//shifts everything over by 1
		for(int i = count; i < size; i++ ) {
			array[i] = array[i + 1];
		}
		
		size--;
		//System.out.println("Element that is removed: " + elementToCheck);
		return elementToCheck;
		}
	
	public void display() { //displays the array
		System.out.print("[");
		
		for(int i = 0; i < size; i++) {
			System.out.print(" "+array[i]+",");
		}
		
		System.out.println(" ]\n");
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner s = new Scanner(new File("/Users/emanuel/Desktop/GitHubProjects/Heap/values.txt"));
		//small scale test
		SlowPQ arrayList = new SlowPQ(); 
		SlowPQ arrayListAll = new SlowPQ();
		arrayList.add(2);
		arrayList.add(132);
		arrayList.add(234);
		arrayList.add(66);
		arrayList.add(3);
		arrayList.add(87);
		arrayList.add(343);
		arrayList.add(24);
		arrayList.add(12);
		arrayList.add(4);
		arrayList.add(6);
		arrayList.add(3);
		arrayList.add(7);
		arrayList.add(33);
		arrayList.display();
		arrayList.removeMin();
		System.out.println("2 is removed");
		arrayList.display();
		arrayList.removeMin();
		System.out.println("3 is removed");
		arrayList.display();
		
		// set up timer
		long startTime = System.nanoTime();
		while (s.hasNext()) {
			int value;
			value = Integer.parseInt(s.next());
			arrayListAll.add(value);
			//System.out.println(value);
		}  
		//close time
		long endTime = System.nanoTime();
		// close time - start time = time
		long time = endTime - startTime;
		
		// set up timer
		long startTime2 = System.nanoTime();
		while(arrayListAll.size >= 0) {
			arrayListAll.removeMin();
		}
		long endTime2 = System.nanoTime();
		//close timer
		long time2 = endTime2 - startTime2; 
		// close time - start time = time
		 
		System.out.println(time + " nano seconds to add all of the integers");
		System.out.println(time2 + " nano seconds to remove all of the integers");
	}
}
