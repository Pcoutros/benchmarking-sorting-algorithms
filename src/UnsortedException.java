/**
 * Pete Coutros
 * CMSC451
 * June 8, 2024
 * Project 1 - Benchmarking Bubble and Insertion Sort
 * 
 * This class is a custom exception class that is a child of Exception. It is used to denote an unsorted array
 */
@SuppressWarnings("serial")
public class UnsortedException extends Exception {
	
	public UnsortedException(String msg) {
		super(msg);
	}

}
