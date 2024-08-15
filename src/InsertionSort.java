/**
 * Pete Coutros
 * CMSC451
 * June 8, 2024
 * Project 1 - Benchmarking Bubble and Insertion Sort
 * 
 * This class contains the implementation for an insertion sort algorithm. The sort() method was
 * copied from https://www.geeksforgeeks.org/insertion-sort/ and modified to meet the project
 * requirements.
 */
public class InsertionSort extends AbstractSort {
	
	/**
	 * Method to sort array using insertion sort
	 * 
	 * This implementation was copied from:
	 * https://www.geeksforgeeks.org/insertion-sort/
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
	    	
	        int n = arr.length;
	        for (int i = 1; i < n; ++i) {
	            int key = arr[i];
	            int j = i - 1;
	
	            /* Move elements of arr[0..i-1], that are
	               greater than key, to one position ahead
	               of their current position */
	            while (j >= 0 && arr[j] > key) {
	            	
	                arr[j + 1] = arr[j];
	                j = j - 1;
	                
	            	//The swap is the critical operation of the function
	            	incrementCount();
	            }
	            arr[j + 1] = key;
	        }
	        
	        //Record elapsed time
	        endSort();
	        
	        //Check to see if array is sorted
	        verifySort(arr);
	        
	        return arr;
	}
}
