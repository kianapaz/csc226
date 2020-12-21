/**
 *
 * Kiana Pazdernik
 * V00896924
 * Outline given by CSC 226
 * October 1, 2019
 * CSC 226 - Fall 2019
 */
import java.util.*;
import java.io.*;

public class Quickselect {
    
    // Bases cases:
	// If the list is empty, return -1
	// If there is one element and k is too large, return -1
	// If there is one element, and k is in the limit, return that single element
    public static int Quickselect(int[] A, int k) {
        
		//This pseudocode is given in the CSc 226 Lecture Notes
		if(A.length == 1){
			return A[0];
		}
		// If k is larger than the array, return -1
		if( k > A.length){
			return -1;
		}
		
		// Call pickpivot to get the pivot 
		int pivot = PickPivot(A);
		int[] L, G;

		// Call Partition to partition the array around the pivot
		Object[] result= Partition(A, pivot);

		// From the object, get the Lower and Greater array from result
		L = (int[])result[0];
		G = (int[])result[1];
		
		// If the Lower length is less than k
		if(k <= L.length){
			// Call quickselect with the lower array
			return Quickselect(L, k);
		}else if (k == (L.length +1 )){
			// If k is the pivot
			//Return pivot
			return pivot;
		}else if(k > (L.length + 1)){
			// Otherwise if k is greater than the lower array
			// Call quickselect with the greater array
			return Quickselect(G, k - L.length - 1);
		}
		// Otherwise return -1 
        return -1;
    }
	
	// Choosing a pivot using median of medians
	// PickPivot() accepts an array of integers and return a pivot
	public static int PickPivot(int[] A){
		
		// Initialize a Pivot, a left index and right index
		int pivot;
		int left = 0;
		int right = A.length -1;
		// Call the method pivoting to compute the pivot
		pivot = pivoting(left, right, A);
		
		// Return the result of pivoting
		return pivot;
		
	}
	// Pivoting() accepts an index ofr an int array and computes the pivot for the int array
	public static int pivoting(int left, int right, int[] A){

		// Get the number of elements in the given indexes in theint array
		int n = right - left;
		// If there is 9 or less elements, return the median index
		if(n <= 9){
			// Sort the array and return the centre element
			Arrays.sort(A);
			int index = A[n/2];
			return index;
		}
		// Initialize an int to chunk the A array into subsets of 9 elements
		int chunk = 9;
		// Initialize a subset array to hold a subset of A
		int subset[] = new int[9];
		// Initialize an Arraylist to hold the medians ofa ll the subsets
		List<Integer> medians = new ArrayList<Integer>();

		// Iterate through the A array 
		for(int i = 0; i < A.length; i += chunk){
			// Get a subset of 9 elements from A
			// The last subset may have less than 9 elements
			subset = Arrays.copyOfRange(A, i, Math.min(A.length,i+chunk));

			// Get the median of the subset by calling getMedian
			int med = getMedian(subset, subset.length);

			// Add the median to medians
			medians.add (med);
		} 
		// COnvert the Medians Arraylist to an int array
		int[] median = convertToIntArr(medians);
		
		// Recursively call the pivoting function 
		// with the medians and the left index and the left index + the ceiling of the length of array/9
		return pivoting(left, left+((int) Math.ceil(n/9)), median);
		
	}

	// getMedian finds the median of an array given it's length
	public static int getMedian(int[] A, int n){
		// Sorts the array
		Arrays.sort(A);
		// If the length is even, return the element that is the median
		if(n % 2== 0){
			return (A[n/2] + A[n/2 - 1]) /2;
		}
		// Otherwise return the centre element 
		return A[n/2];
	}

	// Parttion takes an int array and pivot 
	// returns an object with an int array of values Greater and Lower
	public static Object[] Partition(int[] A, int pivot){
		
		// Initialize two arraylists 
		List<Integer> lower = new ArrayList<Integer>();
		List<Integer> greater = new ArrayList<Integer>();
		
		// Iterate through each element in the int array
		for(int i = 0; i < A.length; i++){
			// If the A[i] is less than the pivot, add to lower
			// Otherwise add to greater
			if(A[i] < pivot){
				lower.add(A[i]);
			}else if(A[i] > pivot){
				greater.add(A[i]);
			}
		}
		// Convert the two ArrayLists back to integer arrays by calling convertInteger
		int[] arrayL = convertToIntArr(lower);
		int[] arrayG = convertToIntArr(greater);
		
		// Create an object that contains both arrays
		Object[] arr = new Object[2];

		arr[0] = arrayL;
		arr[1] = arrayG;
		// Return the object witht eh lower and greater int arrays
		return arr;
	}
	// convertToIntArr takes an ArrayList of integers and converts it to an int array
	public static int[] convertToIntArr(List<Integer> integers)
	{
		// Initialize an int array of length of the Arraylist
		int[] arrayInt = new int[integers.size()];

		// For the length of the Arraylist, add all the elements into the int array
		for (int i=0; i < arrayInt.length; i++){
			arrayInt[i] = integers.get(i).intValue();
		}
		// Return int array
		return arrayInt;
	}

	// Main() given by Csc 226 
    public static void main(String[] args) {
        Scanner s;
        int[] array;
        int k;
        if(args.length > 0) {
	    	try{
				s = new Scanner(new File(args[0]));
				int n = s.nextInt();
				array = new int[n];
				for(int i = 0; i < n; i++){
					array[i] = s.nextInt();
				}
			} catch(java.io.FileNotFoundException e) {
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n", args[0]);
		}
		else {
			s = new Scanner(System.in);
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
			int temp = s.nextInt();
			ArrayList<Integer> a = new ArrayList<Integer>();
			while(temp >= 0) {
				a.add(temp);
				temp=s.nextInt();
			}
			array = new int[a.size()];
			for(int i = 0; i < a.size(); i++) {
				array[i]=a.get(i);
			}
			
			System.out.println("Enter k");
		}
		k = s.nextInt();
		System.out.println("The " + k + "th smallest number is the list is "
			+ Quickselect(array,k));	
	}
}
