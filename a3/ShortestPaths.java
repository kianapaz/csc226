/* ShortestPaths.java
   CSC 226 - Fall 2019
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
   java ShortestPaths
   
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
   java ShortestPaths file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
   <number of vertices>
   <adjacency matrix row 1>
   ...
   <adjacency matrix row n>
   
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
   
   An input file can contain an unlimited number of graphs; each will be processed separately.
   
   NOTE: For the purpose of marking, we consider the runtime (time complexity)
         of your implementation to be based only on the work done starting from
	 the ShortestPaths() method. That is, do not not be concerned with the fact that
	 the current main method reads in a file that encodes graphs via an
	 adjacency matrix (which takes time O(n^2) for a graph of n vertices).
   
   
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Iterator;

import java.util.Vector;
import java.util.ArrayList;
/*
 * Kiana Pazdernik
 * V00896924
 * November 5, 2019
 * CSC 226 Assignment 3
 * Outline given by Csc 226 Assignmnet 3
*/


//Do not change the name of the ShortestPaths class
public class ShortestPaths{

    // number of vertices
    public static int n; 

	// Initializing a PQ to hold the vertices in order
	public static IndexMinPQ<Integer> pq;
	// ArrayList to hold the paths from the start
	public static ArrayList<Integer> path;
	// Vector to hold the total distances (weights) of every path
	public static Vector<Integer> dist = new Vector<Integer>();
	
	
    /* ShortestPaths(adj) 
       Given an adjacency list for an undirected, weighted graph, calculates and stores the
       shortest paths to all the vertices from the source vertex.
       
       The number of vertices is adj.length
       For vertex i:
         adj[i].length is the number of edges
         adj[i][j] is an int[2] that stores the j'th edge for vertex i, where:
           the edge has endpoints i and adj[i][j][0]
           the edge weight is adj[i][j][1] and assumed to be a positive integer
       
       All weights will be positive.
    */

	// Function that returns the shortest path using Dijkstra's Algorithm
	// Given by Csc 226
    static void ShortestPaths(int[][][] adj, int source){
		n = adj.length;

		// Intialize a Priority Queue to hold the vertex and distances
		pq = new IndexMinPQ<Integer>(n);

		// Insert the source vertex with the starting distance of zero
		pq.insert(source, 0);

		// For the length of the vertices in the adjacency list
		for(int i = 0; i < n; i++){
			// Initialize every value as infinity (or max value)
			dist.add(i, Integer.MAX_VALUE);
		}

		// Then add the source vertex witht he starting distance of 0
		dist.add(source, 0);

		// Initalize an int array that will hold the path pf the vertices
		int paths[] = new int[n];

		// Initialize the first vertex with -1, since there aren't any previosu vertices 
		paths[0] = -1;

		// While iterating through the PQ
		while(!pq.isEmpty()){
			
			// Retrieve the most minimum vertex
			int u = pq.delMin();

			// For every neighbour the vertex has
			for(int i = 0; i < adj[u].length; i++){

				// Initialize a vertex that is one of the neighbours
				int v = adj[u][i][0];
				// Initialize a weight of the neighbour
				int weight = adj[u][i][1];
				
				// Checks to see if it is shorter to go through u from v
				if(dist.get(v) > (dist.get(u) + weight)){
					// Then change the weight of v
					// And go through u
					 dist.setElementAt(dist.get(u) + weight, v);
					 // Add the vertex u as a previous vertex of v
					 paths[v] = u;

					// If the PQ already contains this vertex
					if(pq.contains(v)){
						// Then the distance needs to be updated from v
						pq.changeKey(v, dist.get(v));
					}else{
						// Otherwise insert the vertex to the PQ
						pq.insert(v, dist.get(v));
					}
				}
			}
	
		}
		
		// Initialize a the global variable path 
		// To be used to print the paths
		path = new ArrayList<Integer>(n);

		// For the length of vertices 
		for(int i = 0; i < n; i++){
			// Add every vertex to the path ArrayList
			path.add(paths[i]);
		}

    }

	// Recursive function that iterates through the ArrayList 
	// To print the previous vertices which results in the path
	private static void paths(ArrayList<Integer> prev, int i){

		// Checks if the given integer is zero (the source)
		// And if it's back a the source, the path has been printed
		if(i < 0){
			return;
		}
		// Otherwise recursively call paths to print every vertex to the source
		paths(prev, prev.get(i));
		// If the next vertex is not the source
		if(i > 0){
			// Print an arrow pointing to the vertex then the vertex
			System.out.print( " --> " + i );
		}else{
			// Otherwise its the last vertex in paths 
			System.out.print(i);
		}
		
	}
   
    // Function given by Csc 226
    static void PrintPaths(int source){
	
		// For the length of the ArrayList path
		for(int i = 0; i < path.size(); i++){
			// Print the source 
			System.out.print("The path from " + source + " to " + i + " is: ");
			// Then call paths to print the paths
			paths(path, i);
			// Then print the total distance 
			System.out.println(" and the total distance is : " + dist.get(i));
			
		}
    }
    
    
    /* main()
       Contains code to test the ShortestPaths function. You may modify the
       testing code if needed, but nothing in this function will be considered
       during marking, and the testing process used for marking will not
       execute any of the code below.
    */
    public static void main(String[] args) throws FileNotFoundException{
		Scanner s;
		if (args.length > 0){
			//If a file argument was provided on the command line, read from the file
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}
		else{
			//Otherwise, read from standard input
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][][] adj = new int[n][][];
			
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				LinkedList<int[]> edgeList = new LinkedList<int[]>(); 
				for (int j = 0; j < n && s.hasNextInt(); j++){
					int weight = s.nextInt();
					if(weight > 0) {
						edgeList.add(new int[]{j, weight});
					}
					valuesRead++;
				}
				adj[i] = new int[edgeList.size()][2];
				Iterator it = edgeList.iterator();
				for(int k = 0; k < edgeList.size(); k++) {
					adj[i][k] = (int[]) it.next();
				}
			}
			if (valuesRead < n * n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			
			// output the adjacency list representation of the graph
			/*for(int i = 0; i < n; i++) {
				System.out.print(i + ": ");
				for(int j = 0; j < adj[i].length; j++) {
					System.out.print("(" + adj[i][j][0] + ", " + adj[i][j][1] + ") ");
				}
				System.out.print("\n");
			}*/
			
			long startTime = System.currentTimeMillis();
			
			ShortestPaths(adj, 0);
			PrintPaths(0);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			//System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
		}
		graphNum--;
		System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
    }
}
