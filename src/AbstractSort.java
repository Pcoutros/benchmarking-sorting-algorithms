/**
 * Pete Coutros
 * June 8, 2024
 * 
 * This is an abstract class to be extended by both sorting algorithms. It contains an abstract
 * sort() method that will need to be overridden. This class is also used to record the start 
 * time of the sort, count the critical operations of the sort, and record the elapsed time of
 * the sort.
 */
public abstract class AbstractSort {
	
	//Attributes
	private int count;
	private long startTime;
	private long elapsedTime;
	
	/**
	 * Abstract method used to sort an array. Throws an exception if array is not sorted
	 * 
	 * @param int[] array
	 * @return int[] array
	 * @throws UnsortedException 
	 */
	public abstract int[] sort(int[] array) throws UnsortedException;
	
	/**
	 * This method records the start time of the sort and initializes the counter to 0
	 * 
	 * It should be called before the sorting begins
	 */
	protected void startSort() {
		count = 0;
		startTime = System.nanoTime();
	}
	
	/**
	 * This method calculates the elapsed time of the sort
	 * 
	 * It should be called after the sort ends
	 */
	protected void endSort() {
		elapsedTime = System.nanoTime() - startTime;
	}
	
	/**
	 * This method increments the critical operation counter
	 * 
	 * It should be called whenever the critical operation that has been selected is executed
	 */
	protected void incrementCount() {
		count++;
	}
	
	/**
	 * This method returns the value of the counter
	 * 
	 * @return int counter
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * This method returns the elapsed time
	 * 
	 * @return long elapsedTime
	 */
	public long getTime() {
		return elapsedTime;
	}
	
	/**
	 * This method verifies that an array is sorted by comparing an element to the next element 
	 * in the array. If the first element is larger than the next, a new UnsortedException is thrown.
	 * 
	 * @param int[] array
	 * @throws UnsortedException
	 */
	protected void verifySort(int[] array) throws UnsortedException {
		for (int i = 0; i < array.length - 1; i++) {
			if (array[i] > array[i+1]) {
				throw new UnsortedException("Array is not sorted");
			}
		}
	}

}
