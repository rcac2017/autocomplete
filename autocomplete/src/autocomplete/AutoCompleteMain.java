package autocomplete;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main method of Autocomplete package - asks user for input prefix and returns 
 * a number of words that match that prefix in a file.
 * 
 * @author rcac2017
 *
 */
public class AutoCompleteMain {

	public static void main(String [] args) {
		int numWanted = Integer.valueOf(args[0]); //number of matches requested
		String file = args[1]; //name of file
		ArrayList<Term> list = new ArrayList<Term>();
		
		try { //load file, creat temp objects and put in arraylist
			Scanner input = new Scanner(new File(file));
			input.nextInt(); //read first line, not used so not stored
			while (input.hasNext()) { //read data, create term, add to list
				long weight = input.nextLong();
				String query = input.next();
				Term temp = new Term(query, weight);
				list.add(temp);
			}	
			input.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Scanner in = new Scanner(System.in);
		boolean done = false;
		while (!done) { //loop forever, ask user for prefix inputs
			System.out.print("Enter a new prefix: ");
			String prefix = in.nextLine(); //store prefix
			
			 //find all matches and report number to user
			Autocomplete auto = new Autocomplete(list); 
			List<Term> matches = auto.allMatches(prefix);
			int numMatches = matches.size();
			System.out.println("There are " + numMatches + " matches.");
		
			//print results depending on requested number of matches
			if (numMatches == 0) { //don't print anything if no matches
				continue;
			}else if (numMatches > numWanted){ //print numWanted number of matches if too many
				System.out.println("The " + numWanted + " largest are:");
				for (int i = 0; i < numWanted; i++) {
					System.out.println(matches.get(i));
				}
			}else { //print all matches
				System.out.println("The matching items are: ");
				for (int i = 0; i < numMatches; i++) {
					System.out.println(matches.get(i));
				}
			}
		}
		in.close();
	}
}
