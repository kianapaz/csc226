import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
/*
 * Kiana Pazdernik
 * V00896924
 * Novemeber 25, 2019
 * Outline given by CSC 226
 */
public class KMP{
    private String pattern;

	// Intialize the DFA as a  2d array to hold every state 
	private int[][] dfa;
    
    /* Given a pattern, KMP sets up the dfa by iterating over every character
	 */
    public KMP(String pattern){  

		// Assign the global variable pattern to the given pattern
		this.pattern = pattern;
		// Create an int fromt he length of pattern
		int M = pattern.length();

		// Set the dfa with the length of the every possible char and the pattern
		dfa = new int[256][M];

		// At the zero'th position the answer will be 1
		// Only if the first letter matches the first letter in pattern
		dfa[pattern.charAt(0)][0] = 1;

		// i is the state that alwasy starts at 0
		int i = 0; 
		
		// Computing the dfa[][j]
		// Looping through the length of the pattern length
		for(int j = 1; j < M; j++){
			// Looping through every possible character
			for(int k = 0; k < 256; k++){

				// Transitioning from the j'th state with char k
				// Using i as the state to get the elements from dfa
				dfa[k][j] = dfa[k][i];

			}
			// Dfa moves onto to the next state (i + 1) state 
			// If the char k is the same as the next char in pattern
			dfa[pattern.charAt(j)][j] = j+1;

			// Updating the state
			i = dfa[pattern.charAt(j)][i];

		}
		

    }

	/* Search accepts a txt string
	 * Then iterates through the string using the dfa to see if there is a match with the given pattern
	 * Returns the index of the pattern
	 */
	public int search(String txt){  
		
		// Create two integers to hold the pattern and the given text length
		int M = pattern.length();
		int N = txt.length();

		// Initialize integers to loop through the dfa
		int i , j;

		// Looping through the length of the txt length
		// While j loops throught hte length of the pattern 
		for(i = 0, j = 0; i < N && j < M; i++){
			
			// j becomes the value at the given spot in the dfa
			j = dfa[txt.charAt(i)][j];

		}
		// If j == the beginning length of the pattern
		// The pattern has been found
		if(j == M){
			// Return the index of where the pattern starts
			return i - M;
		}
		// Otherwise j is the end of the txt
		// And the pattern wasn't found
		return N;
    }
	
    
    public static void main(String[] args) throws FileNotFoundException{
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			}
			catch(java.io.FileNotFoundException e){
				System.out.println("Unable to open "+args[0]+ ".");
				return;
			}
			System.out.println("Opened file "+args[0] + ".");
			String text = "";
			while(s.hasNext()){
				text += s.next();
			}
			
			for(int i = 1; i < args.length; i++){
				KMP k = new KMP(args[i]);
				int index = k.search(text);
				if(index >= text.length()){
					System.out.println("The pattern \"" + args[i] + "\" was not found.");
				}
				else System.out.println("The string \"" + args[i] + "\" was found at index " + index + ".");
			}
		}
		else{
			System.out.println("usage: java SubstringSearch <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
		}
    }
}
