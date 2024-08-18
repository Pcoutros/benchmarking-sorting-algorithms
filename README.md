# Benchmarking Bubble and Insertion Sort: Analyzing Critical Operations and Time Complexity

## Description

  This project benchmarks the performance of Bubble Sort and Insertion Sort on datasets of randomly generated values. It involves creating 12 different dataset sizes (ranging from 100 to 1200), with 40 datasets for each size. Both sorting algorithms work on the exact same datasets to ensure data consistency. The JVM warm-up process is implemented by running preliminary sorting operations to ensure that the JIT compiler optimizes the code (compiled vs interpreted), leading to more consistent and accurate benchmarking results. The critical operation counts and elapsed times for the sorting algorithms are written to and stored in designated text files. These files are then used to generate a report displaying the average critical operation count, average elapsed time, coefficient of variation for counts, and coefficient of variation for elapsed time across the datasets. This report is displayed through a Java Swing GUI, utilizing a `JTable` for clear presentation.
  
## Concepts

The concepts covered within this project are as follows:
1) Calculating the theoretical best, average, and worst-case time complexity scenarios for Bubble and Insertion Sort
2) Comparng the results with Big-θ analysis
3) Identifying the critical operation in each sorting mechanism
4) Generating random datasets of various sizes
5) Implementing JVM warm-up for accurate benchmarking
6) Measuring and benchmarking elapsed time for each sorting algorithm
7) Text file writing and reading for storing and retrieving benchmark data
8) Creating a Java Swing GUI specifically the use of `JTable` to display results
9) Utilizing Abstract classes and inheritance in Java

## How to Use

1) Clone the repository
2) Open terminal and change directories to the desired destination
3) Type: `git clone <repository_url>`
4) Change directory into the repository name
5) Compile the BenchmarkSorts.java file using: `javac BenchmarkSorts.java`
6) Run the benchmarking program using: `java BenchmarkSorts`
7) Compile the Report.java file using: `javac Report.java`
8) Run the report using: `java Report`
9) Select the desired sorting algorithm text file to be examined (bubblesort.txt or insertionsort.txt)
