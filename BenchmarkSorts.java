/**
 * Pete Coutros
 * CMSC451
 * June 8, 2024
 * Project 1 - Benchmarking Bubble and Insertion Sort
 * 
 * This class is used to benchmark the sorting algorithms on the randomly generated data sets. It outputs the critical
 * operation counts and elapsed time of each sort() method call of the algorithms into corresponding text files. There are 40 
 * different data sets for each size of n (there are 12 different sizes of n). The output files will have 12 lines corresponding
 * to the 12 different sizes of n, where the first value of the line is the size of n and then the 40 following pairs are the
 * count and time for each of the 40 different data sets of size n. 
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BenchmarkSorts {
	
	//Attributes
	private static final int[] N = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200}; 
	private static final int DATA_SETS = 40;
	private static final List<int[][]> data = new ArrayList<int[][]>();
	
	/**
	 * Constructor to create BenchmarkSorts object. The generateData() method is called upon instantiation
	 */
	public BenchmarkSorts() {
		generateData();
	}

	/**
	 * This method creates a 2D array where the rows are the number of data sets and the columns are the
	 * size of the data set. It also populates the 2D array with random numbers from 0 to 9999. The various
	 * and populated 2D arrays are then added to a List representing the entirety of the data. 
	 */
	private void generateData() {
		
		Random random = new Random();
		
		for (int n : N) {
			int[][] dataset = new int[DATA_SETS][n];
			for (int i = 0; i < DATA_SETS; i++) {
				for (int j = 0; j < n; j++) {
					dataset[i][j] = random.nextInt(10000);
				}
			}
			data.add(dataset);
		}
	}
	
	/**
	 * This method calls the sort() method of the underlying sorting algorithm. It first clones the data to be sorted so
	 * the original data can be reused for the next sorting algorithm. The cloned array is then sorted and the corresponding 
	 * critical operation count and elapsed time are output to a txt file. The first number in each line is the size of the 
	 * data and the following pairs are the count and time. Each data set size is output on a new line.
	 * 
	 * @param AbstractSort sortingAlgorithm
	 * @param String filename
	 */
	public void initiateSort(AbstractSort sortingAlgorithm, String filename) {
		try {
			checkIfFileExists(filename);
			for (int[][] dataset : data) {
				appendLabelToFile(filename, dataset[0].length); 	//Length of 2nd dimension of array corresponds to size of n
				for (int[] array : dataset) {
					int[] arrayClone = array.clone();
					sortingAlgorithm.sort(arrayClone);				//Call to sort() is not assigned to array variable bc we don't need the sorted array
					appendDataToFile(sortingAlgorithm, filename);	//Add critical operation counts and elapsed times for each data set of size n
				}
				appendLineBreakToFile(filename);					//Add line break to separate the different sizes of n 
			}
		} catch (UnsortedException use) {
			System.out.println(use.getMessage());
		}
	}
	
	/**
	 * This method checks if a file already exists. If so it deletes it. This is used so that subsequent runs of the 
	 * sorting algorithms are not appended to the text files. 
	 * 
	 * @param String filename
	 */
	private void checkIfFileExists(String filename) {
		File file = new File(filename);
		if (file.exists()) {
			file.delete();
		}
	}
	
	/**
	 * This method is used to append a line break to the file for each new size of the data set. 
	 * 
	 * @param String filename
	 */
	private void appendLineBreakToFile(String filename) {
		try {
			FileWriter fileWriter = new FileWriter(filename, true);
			fileWriter.append("\n");
			fileWriter.close();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}	
	}
	
	/**
	 * This method is used to append the dataset size to the beginning of each line of the text files.
	 * 
	 * @param String filename
	 * @param int datasetSize
	 */
	private void appendLabelToFile(String filename, int datasetSize) {
		try {
			FileWriter fileWriter = new FileWriter(filename, true);
			fileWriter.append(datasetSize + " ");
			fileWriter.close();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}	
	}
	
	/**
	 * This method is used to append the critical operation count and elapsed time to the text files.
	 * 
	 * @param AbstractSort sortingAlgorithm
	 * @param String filename
	 */
	private void appendDataToFile(AbstractSort sortingAlgorithm, String filename) {
		try {
			FileWriter fileWriter = new FileWriter(filename, true);
			fileWriter.append(sortingAlgorithm.getCount() + " " + sortingAlgorithm.getTime() + " ");
			fileWriter.close();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	
	/**
	 * This method is used to warm up the JVM prior to benchmarking. It does so by simulating the actual data to be 
	 * sorted without outputting the results. The data set sizes are the same as the actual data 
	 * @param AbstractSort sortingAlgorithm
	 */
	public void warmUp(AbstractSort sortingAlgorithm) {
		for (int n : N) {
			for (int i = 0; i < 40; i++) {
				int[] warmUpData = generateWarmUpData(n);
				try {
					sortingAlgorithm.sort(warmUpData);
				} catch (UnsortedException use) {
					System.out.println(use.getMessage());
				}
			}
		}
	}
	
	/**
	 * This method generates the random warm up data arrays of size n to be used in the warm up of the JVM.
	 * 
	 * @param int size
	 * @return int[] warmUpData
	 */
	private int[] generateWarmUpData(int size) {
		Random random = new Random();
		int[] warmUpData = new int[size];
		for(int i = 0; i < size; i++) {
			warmUpData[i] = random.nextInt(10000);
		}
		return warmUpData;
	}

	/**
	 * Driver code of the program that creates a new Benchmark instance, generates the data, creates new AbstractSort instances
	 * for each of the sorting algorithms, initiates the JVM warm ups for the algorithms, and indirectly calls the sort() methods
	 * of the algorithms to sort the randomly generated data. 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		BenchmarkSorts bm = new BenchmarkSorts();
		AbstractSort bubble = new BubbleSort();
		AbstractSort insertion = new InsertionSort();
		bm.warmUp(bubble);
		bm.warmUp(insertion);
		bm.initiateSort(bubble, "bubblesort.txt");
		bm.initiateSort(insertion, "insertionsort.txt");

	}

}
