package edu.wit.cs.comp2350;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/** Sorts geographic points in-place in an array, ordering
 * by surface distance to a specific point 
 * 
 * Wentworth Institute of Technology
 * COMP 2350
 * Assignment 3
 * 
 */

public class A3 {

	//TODO: document this method
	/**
	 * implement quicksort as it is presented the sudocode book: 
	 * when partitioning, pick the most right element element in each subarray as the pivot
	 * place its at correct position in sorted array
	 * places all smaller (than pivot) to left of pivot and all greater elements to right of pivot
	 * @param destinations (input object array)
	 */
	public static void quickSort(Coord[] destinations) {
		//TODO: implement this method
		int n = destinations.length;                            // find the number of input elements

		// quickSort passing a Coord type array, 0, n-1(most right index)
		quickSort(destinations, 0, n - 1);
	}

	static int partition(Coord[] coord, int low, int high) {

		Coord pivot = coord[high];                              // set most right element as the pivot
		int i = (low - 1);                                      // Index of smaller element and indicates the right position of pivot found so far

		for (int j = low; j <= high - 1; j++) {
			// compare the every distance inside of every Coord. If current element is smaller than the pivot,
		    // swap the postation of the Coord depends on the value of the distance
			if (coord[j].getDist() < pivot.getDist()) {
				i++;                                            // Increment index of smaller element
				swap(coord, i, j);
			}
		}
		swap(coord, i + 1, high);
		return (i + 1);
	}

	// low for Starting index, high for Ending index
	static void quickSort(Coord[] coord, int low, int high) {
		
		if (low < high) {
			int indexOfPartition = partition(coord, low, high);   // indexOfPartition is partitioning index

			// Separately sort elements before, partition and after partition
			quickSort(coord, low, indexOfPartition - 1);
			quickSort(coord, indexOfPartition + 1, high);
		}
	}

	// A utility function to swap two elements
	static void swap(Coord[] coord, int i, int j) {
		Coord temp = coord[i];
		coord[i] = coord[j];
		coord[j] = temp;
	}

	
	//TODO: document this method
	/**
	 * In the partitionRam function, Choose the random index's value as pivot
	 * 1. place its at correct position in sorted array
	 * 2. places all smaller (than pivot) to left of pivot and all greater elements to right of pivot
	 * @param destinations
	 */
	public static void randomizedQuickSort(Coord[] destinations) {
		//TODO: implement this method
		int n = destinations.length;
		
		partitionRam(destinations, 0, n - 1);
	}

	static void partitionRam(Coord[] coord, int low, int high) {

		Random rand = new Random();                              // use the Java Random class to generate a valid index in the subarray
		int pivot = rand.nextInt(high - low) + low;              // find the random index of pivot

		// pivot is chosen randomly
		Coord temp1 = coord[pivot];
		coord[pivot] = coord[high];
		coord[high] = temp1;

		sort(coord, low, high);	
	}

	static void sort(Coord[] coord, int low, int high) {         // low is Starting index, high is Ending index
		while (low < high) {
			int indexOfPartition = partition(coord, low, high);

			// Recursively sort elements before, partition and after partition
			if (indexOfPartition - low < high - indexOfPartition) {
				sort(coord, low, indexOfPartition - 1);
				low = indexOfPartition + 1;
			} else {
				sort(coord, indexOfPartition + 1, high);
				high = indexOfPartition - 1;
			}
		}
	}

	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	/**
	 * Implementation note: This implementation is a stable, adaptive, iterative mergesort
	 *  that requires far fewer than n lg(n) comparisons when the input array is partially
	 *  sorted, while offering the performance of a traditional mergesort when the input
	 *  array is randomly ordered. If the input array is nearly sorted, the implementation
	 *  requires approximately n comparisons. Temporary storage requirements vary from a
	 *  small constant for nearly sorted input arrays to n/2 object references for randomly
	 *  ordered input arrays.
	 */
	public static void systemSort(Coord[] destinations) {
		Arrays.sort(destinations, (a, b) -> Double.compare(a.getDist(), b.getDist()));
	}

	// Insertion sort eventually sorts an array
	public static void insertionSort(Coord[] a) {

		for (int i = 1; i < a.length; i++) {
			Coord tmpC = a[i];
			int j;
			for (j = i-1; j >= 0 && tmpC.getDist() < a[j].getDist(); j--)
				a[j+1] = a[j];
			a[j+1] = tmpC;
		}
	}

	private static Coord getOrigin(Scanner s) {
		double lat = s.nextDouble();
		double lon = s.nextDouble();

		Coord ret = new Coord(lat, lon);
		return ret;
	}

	private static Coord[] getDests(Scanner s, Coord start) {
		ArrayList<Coord> a = new ArrayList<>();

		while (s.hasNextDouble())
			a.add(new Coord(s.nextDouble(), s.nextDouble(), start));

		Coord[] ret = new Coord[a.size()];
		a.toArray(ret);

		return ret;
	}

	private static void printCoords(Coord start, Coord[] a) {

		System.out.println(start.toColorString("#000000"));
		
		int interp = 0;
		if (a.length > 0) {
			interp = 255 / a.length;
		}
		
		for (int i = 0; i < a.length; ++i) {
			String s = String.format("#FF%02XFF", 255 - (interp * i));
			System.out.println(a[i].toColorString(s));
		}

		System.out.println();
		System.out.println("Paste these results into https://mobisoftinfotech.com/tools/plot-multiple-points-on-map/ if you want to visualize the coordinates.");
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.printf("Enter the sorting algorithm to use [i]nsertion sort, [q]uicksort, [r]andomized quicksort, or [s]ystem quicksort): ");
		char algo = s.next().charAt(0);

		System.out.printf("Enter your starting coordinate in \"latitude longitude\" format as doubles: (e.g. 42.33612 -71.094016): ");
		Coord start = getOrigin(s);

		System.out.printf("Enter your end coordinates one at a time in \"latitude longitude\" format as doubles: (e.g. 38.897386 -77.037400). End your input with a non-double character:%n");
		Coord[] destinations = getDests(s, start);

		s.close();
		
		switch (algo) {
		case 'i':
			insertionSort(destinations);			
			break;
		case 'q':
			quickSort(destinations);
			break;
		case 'r':
			randomizedQuickSort(destinations);
			break;
		case 's':
			systemSort(destinations);
			break;
		default:
			System.out.println("Invalid search algorithm");
			System.exit(0);
			break;
		}

		printCoords(start, destinations);

	}

}
