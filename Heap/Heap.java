import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class Heap {

	int [] heap;
	int size;
	
	public Heap() {
		// Builds an array that is 100000 slots big
		heap = new int[100000];
		
	}
    private void moveUpArray() {
    	int k = size;
        
        while (k > 0) {
        	//checks to see if the parent is grater then the child
            int parentPosition = (k-1)/2;
            int leftorRight = heap[k];
            int parent = heap[parentPosition];
            int tempItem;
            if (leftorRight <= parent) {
                // switchs the parent and the child
            	tempItem = heap[k];
                heap[parentPosition] =  tempItem;
                heap[k] = parent; 
                // move up one level
                k = parentPosition;
            } else {
                break;
            }
        }
    }

	public void add(int e) {
		// adds element to end of heap
		heap[size] = e;
		//puts element in correct location
		moveUpArray();
		size++;
	}
	
    private void moveDownArray() {
       //sets up what all the variables that will be used in method
    	int k = 0;
        int parent = (k-1)/2;
        int left = 2*k+1;
        int right = 2*k+2;
        int containsLeft = heap[left]; 
        int containsRight = heap[right];
        int containsParent = heap[parent];
        // looks to see if left child is there
        while (left < size) {
            int min = left;
            if (right < size) { // there is a right child
                if (containsRight < containsLeft) {
                    min++;
                }
            }
            
           int containsMin = heap[min]; 
            if (containsParent > containsMin) {
                // switches the parent and the child
                heap[min] = containsParent;
                heap[parent] = containsMin;
                k = min;
                parent = min;
                left = 2*k+1;
                right = 2*k+2;
                //looks to see if children are still inside array
                if (left > size || right > size) {
                	break;
                }
                //re-calculates the values of left, right, and parent
                containsLeft = heap[left]; 
                containsRight = heap[right];
                containsParent = heap[parent];
            } else {
                break;
            }
        }
    }

	public long removeMin() { 
		//holds element to remove
		long minElement = heap[0];
		if (size == 0) {
			 return minElement;
		}
		// moves last element to the front of the list
		heap[0] = heap[size-1];
		//sets the last spot back to zero
		heap[size - 1] = 0;
		size--;
		//puts element that was moved to front in correct location
		moveDownArray();
		//System.out.println("Element that is removed: " + minElement);
		return minElement;	
		}
	
	public void display() { //displays the heap array
		System.out.print("[");
		
		for(int i = 0; i < size; i++) {
			System.out.print(" "+heap[i]+",");
		}
		
		System.out.println(" ]\n");
		
	}
	
	public static void main(String[] args)  throws FileNotFoundException  {
			Scanner s = new Scanner(new File("/Users/emanuel/Desktop/GitHubProjects/Heap/values.txt"));
			//Tests to see if remove and add work
			Heap heap = new Heap();
			Heap heapAll = new Heap ();
			heap.add(1);
			heap.add(52);
			heap.add(23);
			heap.add(34);
			heap.add(4);
			heap.add(3);
			heap.add(153);
			heap.add(30);
			heap.add(13);
			heap.add(942);
			heap.add(133);
			heap.add(143);
			heap.add(302);
			heap.display();
			heap.removeMin(); 
			heap.display();
			heap.removeMin(); 
			heap.display();
			heap.removeMin();
			heap.display();
			heap.removeMin();
			heap.display();
			heap.removeMin();
			heap.display();
			heap.removeMin();
			heap.display();
			heap.removeMin();
			heap.display();
			heap.removeMin();
			heap.display();
			heap.removeMin();
			heap.display();
			heap.removeMin();
			heap.display();
			heap.removeMin();
			heap.display();
			heap.removeMin();
			heap.display();
			heap.removeMin();
			heap.display();
			
			//adds all the integers
			long startTime = System.nanoTime();
			while (s.hasNext()) {
				int value;
				value = Integer.parseInt(s.next());
				heapAll.add(value);
				//System.out.println(value);
			}  
			long endTime = System.nanoTime();
			long time = endTime - startTime;
			
			//removes all that was add
			long startTime2 = System.nanoTime();
			while(heapAll.size > 0) {
				heapAll.removeMin();
				//heapAll.display();
				//System.out.println(i++);
			}
			long endTime2 = System.nanoTime();
			//close timer
			long time2 = endTime2 - startTime2; 
			// close time - start time = time
			
			System.out.println(time + " nano seconds to add all of the integers");
			System.out.println(time2 + " nano seconds to remove all of the integers");
			
	}
}
