/**
 * Pete Coutros
 * CMSC451
 * June 8, 2024
 * Project 1 - Benchmarking Bubble and Insertion Sort
 * 
 * This class is used to manipulate the data from the BenchmarkSorts.java text file outputs and puts it in a JTable to display to the user. The data from
 * the text files are first read in, parsed, and added to the appropriate lists. That data is then manipulated and added into the correct row/column of the
 * JTable. The final JTable is then displayed to the user via GUI.
 */

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Report {
	
	//Attributes
	private JFileChooser jfc;
	private int jfcResult;
	private JTable table;
	private JFrame frame;
	private JScrollPane scrollPane;
	private final static String[] COLUMN_NAMES = {"Size", "Avg Count", "Coef Count", "Avg Time", "Coef Time"};
	private final static List<int[]> COUNTS = new ArrayList<int[]>();
	private final static List<int[]> ELAPSED_TIMES = new ArrayList<int[]>();
	private final static List<Integer> SIZE_OF_N = new ArrayList<Integer>();
	
	/**
	 * Constructor to initialize the JFileChooser
	 */
	public Report() {
		jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
	}
	
	/**
	 * This method allows the user to select a file. After the file is selected, the data within it will be read and stored by readFile(). The JTable
	 * will then be created through analyzing the stored data in the call to createTable(). Finally the JTable will be displayed with the call to 
	 * displayTable(). 
	 */
	public void chooseFile() {
		jfcResult = jfc.showOpenDialog(null);
		if (jfcResult == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			try {
				readFile(file);
				createTable();
				displayTable();
			} catch (Exception e) {
				System.out.print(e.getMessage());
			}
		}
	}
	
	/**
	 * This method parses the data from the file and stores it in the appropriate lists. The first value of each line corresponds to the size of n.
	 * The next values are value pairs of counts and elapsedTime for the sorting algorithm. Each value in the file is delimited by a space. 
	 * 
	 * @param File file
	 * @throws Exception
	 */
	private void readFile(File file) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = reader.readLine()) != null) {		
			String[] contents = line.split(" ");			//Values are delimited by spaces in the text file. See BenchmarkSort.java

			//Create Arrays to hold the different count/time values on a single line. Size of the arrays are half of the values in contents
			int[] counts = new int[contents.length/2];			
			int[] elapsedTimes = new int[contents.length/2];	
			
			
			int dataSize = Integer.parseInt(contents[0]);				//First value of each line is the size of n
			for (int i = 0; i <= ((contents.length/2)-1); i++) {
				counts[i] = Integer.parseInt(contents[(i*2) + 1]);		//Skip the first value (size), then every other value is a count
				elapsedTimes[i] = Integer.parseInt(contents[(i*2) + 2]);	//Skip first two values (size and first count), then every other value is a time
			}
			
			//Add size and arrays for count/time to the appropriate lists. Used to store values on all lines vs just one line
			SIZE_OF_N.add(dataSize);
			COUNTS.add(counts);
			ELAPSED_TIMES.add(elapsedTimes);
		}
		reader.close();
	}
	
	/**
	 * This method creates the JTable and populates it with the manipulated data from the Lists. It calls getAvg() and getCoef() methods
	 * for the data manipulation
	 */
	private void createTable() {
		
		//Create 2D array to hold the data values
		String[][] tableData = new String[SIZE_OF_N.size()][COLUMN_NAMES.length];
		
		//For each value of n, calculate the average count, count coefficient of variance, average time, and time coefficient of variance
		//Add each to the 2D array under the appropriate row/column
		for(int i = 0; i < SIZE_OF_N.size(); i++) {
			int size = SIZE_OF_N.get(i);
			int[] counts = COUNTS.get(i);
			int[] elapsedTimes = ELAPSED_TIMES.get(i);
			
			double avgCount = getAvg(counts);
			double coefCount = getCoef(counts, avgCount);
			double avgTime = getAvg(elapsedTimes);
			double coefTime = getCoef(elapsedTimes, avgTime);
			
			tableData[i][0] = String.valueOf(size);
			tableData[i][1] = roundToTwoDecimalPlaces(avgCount);
			tableData[i][2] = roundToTwoDecimalPlaces(coefCount) + "%";
			tableData[i][3] = roundToTwoDecimalPlaces(avgTime);
			tableData[i][4] = roundToTwoDecimalPlaces(coefTime) + "%";
		}
		
		table = new JTable(tableData, COLUMN_NAMES);
	}
	
	/**
	 * This method rounds doubles to two decimal places and returns a string representation
	 * 
	 * @param double value
	 * @return String rounded to two decimal places
	 */
	private String roundToTwoDecimalPlaces(double value) {
		return String.format("%.2f", value);
	}
	
	/**
	 * This method calculates the average value from an array of values
	 * 
	 * @param int[] values
	 * @return double result
	 */
	private double getAvg(int[] values) {
		double result = 0;
		for (int i = 0; i < values.length; i++) {
			result += values[i];
		}
		return result /= values.length;
	}
	
	/**
	 * This method calculates the coefficient of variance form an array of values and their average. It does so by calculating the
	 * sum of the square of deviations (value - average), then calculating the standardDeviation by taking the prior result and dividing
	 * by the number of values and then taking the square root of that. Finally, the coefficient of variance is calculated by dividing 
	 * the prior result by the average and then multiplying by 100%
	 * 
	 * Standard Deviation = sqrt((sum(x-mean)^2)/N)
	 * Coefficient of Variance = (Standard Deviation/mean) * 100
	 * 
	 * @param int[] values
	 * @param double average
	 * @return double coefficient of variance
	 */
	private double getCoef(int[] values, double average) {
		double sumOfSquareOfDeviations = 0;
		for (int i = 0; i < values.length; i++) {
			sumOfSquareOfDeviations += Math.pow((values[i] - average), 2);
		}
		double standardDeviation = Math.sqrt(sumOfSquareOfDeviations/values.length);
		return standardDeviation/average * 100;
	}
	
	/**
	 * This method displays the JTable by placing it in a JScrollPane and then placing that JScrollPane in a JFrame
	 */
	private void displayTable() {
		frame = new JFrame("Benchmarking Report");
		scrollPane = new JScrollPane(table);
		frame.add(scrollPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Driver method to instantiate a Report object and call the public function chooseFile(). The user will then select their file and the resulting JTable from 
	 * the manipulated data will be displayed. 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            Report report = new Report();
	            report.chooseFile();
	        }
	    });
	}
}
