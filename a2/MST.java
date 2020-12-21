/* MST.java
   CSC 226 - Fall 2019
   Problem Set 2 - Template for Minimum Spanning Tree algorithm
   
   The assignment is to implement the mst() method below, using Kruskal's algorithm
   equipped with the Weighted Quick-Union version of Union-Find. The mst() method computes
   a minimum spanning tree of the provided graph and returns the total weight
   of the tree. To receive full marks, the implementation must run in time O(m log m)
   on a graph with n vertices and m edges.
   
   This template includes some testing code to help verify the implementation.
   Input graphs can be provided with standard input or read from a file.
   
   To provide test inputs with standard input, run the program with
       java MST
   To terminate the input, use Ctrl-D (which signals EOF).
   
   To read test inputs from a file (e.g. graphs.txt), run the program with
       java MST graphs.txt
   
   The input format for both methods is the same. Input consists
   of a series of graphs in the following format:
   
       <number of vertices>
       <adjacency matrix row 1>
       ...
       <adjacency matrix row n>
   	
   For example, a path on 3 vertices where one edge has weight 1 and the other
   edge has weight 2 would be represented by the following
   
   3
   0 1 0
   1 0 2
   0 2 0
   	
   An input file can contain an unlimited number of graphs; each will be processed separately.
   
   NOTE: For the purpose of marking, we consider the runtime (time complexity)
         of your implementation to be based only on the work done starting from
	 the mst() method. That is, do not not be concerned with the fact that
	 the current main method reads in a file that encodes graphs via an
	 adjacency matrix (which takes time O(n^2) for a graph of n vertices).
*/

/* 
 * Kiana Pazdernik
 * V00896924
 * October 24 
 * Outline given by Csc 226 
 */
import java.util.Scanner;
import java.io.File;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.PriorityQueue;


public class MST {

    // Creating an Edge class that will hold the points and weight
	// from the Adjacency Matrix
	// Implements Comparable to ensure the edges will be in increasing order
    public static class Edge implements Comparable<Edge>{ 
        int from;
		int to;
		int weight; 

		// Compare Function to ensure the weight is in increasing order
        public int compareTo(Edge compareEdge){ 
            return this.weight-compareEdge.weight; 
        } 
    }
    
    // A subset function to hold the id of the each vertex
	// And the rank, the id will hold the root once the MST is done
    private static class subset{
		int id;
		int rank;
	} 
  
	// Union takes a subset and a point a and point b that will be connected
	public static void Union(subset subsets[], int a, int b){
		// Find the root id of the point a and b
		int aRoot = find(subsets, a); 
        int bRoot = find(subsets, b); 
        
        // If the root of a is less than the root of b
        if(subsets[aRoot].rank < subsets[bRoot].rank){
			// Change the root of a to be equal to the root of b
			subsets[aRoot].id = bRoot; 

		// Otherwise if the root of a is greatter than the root of b
		}else if(subsets[aRoot].rank > subsets[bRoot].rank){
			// Change the root of b to the root of a
			subsets[bRoot].id = aRoot; 
		
		}else{ 
			// Otherwise both of them are equal
			// Make a the root of b 
            subsets[aRoot].id = bRoot;
			// Increment the rank of a to ensure a is now greater than b 
            subsets[bRoot].rank++; 
        }
	}
	
	// Finding the the points and weight of a root
	// Using a subset and an index
	public static int find(subset subsets[], int i){

		// Find the root and make the root the id
		// If i is not already it's root
		if(subsets[i].id != i){
			// Recursively call find until the id is the root
			subsets[i].id = find(subsets, subsets[i].id);
		} 
		// Return the correct id
	    return subsets[i].id; 
	}
	
	
    /* mst(adj)
    Given an adjacency matrix adj for an undirected, weighted graph, return the total weight
    of all edges in a minimum spanning tree.

    The number of vertices is adj.length
    For vertex i:
      adj[i].length is the number of edges
      adj[i][j] is an int[2] that stores the j'th edge for vertex i, where:
        the edge has endpoints i and adj[i][j][0]
        the edge weight is adj[i][j][1] and assumed to be a positive integer
 */
    static int mst(int[][][] adj) {

		// The number of vertices in the adjacency matrix
        int n = adj.length;

        // Creating a subset that will hold the id of the vertex and the rank
		// Loops through the amount of vertices and creates a subset with a parent
        subset subsets[] = new subset[n];
        for (int i = 0; i < n; i++) {
			// Initializing a subset for each edge
            subsets[i] = new subset();

			// Then adding the ID which is itself
			subsets[i].id = i;
			// Adding a rank of zero since the weight is unknown
            subsets[i].rank = 0;
        }

		// Using a Priority Queue, store the edges into the PQ
        PriorityQueue<Edge> edges = new PriorityQueue<Edge>();
		// Looping through the adjacency matrix to be added to PQ
        for(int i = 0; i < n; i++){
            for(int j = 0; j < adj[i].length; j++){
				// Create a temp edge to hold the points and weight
                Edge temp = new Edge();
                temp.from = i;
                temp.to = adj[i][j][0];
                temp.weight = adj[i][j][1];
				// Add to PQ
                edges.add(temp);
            }
        }
		
		// Creates an Edge mst to hold the final MST 
		Edge mst[] = new Edge[n]; 

        // Initialize each edge for every number of vertices
        for (int i = 0; i < n; i++){
            mst[i] = new Edge(); 
        }

		// Initialize a counter that increments to the next edge for the Edge Graph mst
		int count = 0;
		// While the count is less than the number of vertices (minus one)
        while(count < n-1){

			// Create a new edge to hold the head of the priority queue, and remove it from the pq
            Edge next_edge = new Edge(); 
            next_edge = edges.poll();

			// Finding the point "to" and the "from" uisng the find method
            int a = find(subsets, next_edge.from); 
            int b = find(subsets, next_edge.to);

            // Ensure that the point will not create a cycle
			// Then union the  two points
            if( a != b){
				// Move to the next  edge
                mst[count++] = next_edge;
				// Connect the two points
                Union(subsets, a, b);
            }
            
            
        }
	
        /* Add the weight of each edge in the minimum spanning tree
        to totalWeight, which will store the total weight of the tree.
        */
        int totalWeight = 0;
        
		// Because the MST is stored in an Edge Graph
		// Adding all the weight's of the Edge graph will return the total Weight
        for(int i = 0; i < count; i++){
            totalWeight += mst[i].weight;

        }
        return totalWeight;
    }	


    public static void main(String[] args) {
		/* Code to test your implementation */
		/* You may modify this, but nothing in this function will be marked */

		int graphNum = 0;
		Scanner s;

		if (args.length > 0) {
			//If a file argument was provided on the command line, read from the file
			try {
				s = new Scanner(new File(args[0])); 
			}
			catch(java.io.FileNotFoundException e) {
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}
		else {
			//Otherwise, read from standard input
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
			
		//Read graphs until EOF is encountered (or an error occurs)
		while(true) {
			graphNum++;
			if(!s.hasNextInt()) {
				break;
			}
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();

			int[][][] adj = new int[n][][];
			
			
			
			
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++) {
				LinkedList<int[]> edgeList = new LinkedList<int[]>(); 
				for (int j = 0; j < n && s.hasNextInt(); j++) {
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
			if (valuesRead < n * n) {
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}

			// // output the adjacency list representation of the graph
			// for(int i = 0; i < n; i++) {
			// 	System.out.print(i + ": ");
			// 	for(int j = 0; j < adj[i].length; j++) {
			// 	    System.out.print("(" + adj[i][j][0] + ", " + adj[i][j][1] + ") ");
			// 	}
			// 	System.out.print("\n");
			// }

			int totalWeight = mst(adj);
			System.out.printf("Graph %d: Total weight of MST is %d\n",graphNum,totalWeight);

					
		}
    }

    
}
