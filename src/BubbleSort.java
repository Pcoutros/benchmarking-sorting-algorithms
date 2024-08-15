/**
 * Pete Coutros
 * June 8, 2024
 * 
 * This class contains the implementation for a bubble sort algorithm. The sort() method was
 * copied from https://www.geeksforgeeks.org/bubble-sort/ and modified to meet the project
 * requirements.
 */

public class BubbleSort extends AbstractSort {
	
	/**
	 * Method to sort array using bubble sort
	 * 
	 * This implementation was copied from:
	 * https://www.geeksforgeeks.org/bubble-sort/
	 * and modified to record the start time, count the critical operations, 
	 * record the elapsed time, and return the array per project requirements
	 * 
	 * The array is returned to meet the "inout" requirement of the UML diagram
	 * however the returned array is not needed in the BenchmarkSorts.java
	 * @return int[] arr
	 * @throws UnsortedException 
	 */
    	public int[] sort(int arr[]) throws UnsortedException {
    	
	    	//Begin timer and initialize counter to 0
	    	startSort();
	    	
	        int i, j, temp;
	        int n = arr.length;
	        boolean swapped;
	        for (i = 0; i < n - 1; i++) {
	            swapped = false;
	            for (j = 0; j < n - i - 1; j++) {
	                if (arr[j] > arr[j + 1]) {
	                    
	                    // Swap arr[j] and arr[j+1]
	                    temp = arr[j];
	                    arr[j] = arr[j + 1];
	                    arr[j + 1] = temp;
	                    swapped = true;
	                    
	                    //The swap is the critical operation
	                    incrementCount();
	                }
	            }
	
	            // If no two elements were swapped by inner loop, then break
	            if (swapped == false)
	                break;
	        }
	        
	        //Record elapsed time
	        endSort();
	        
	        //Check to see if array is sorted
	        verifySort(arr);        
	        return arr;
	}
}
