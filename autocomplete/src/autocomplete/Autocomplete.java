package autocomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Autocomplete // implements AutocompleteInterface 
{
	private List<Term> list;
	
	/**
	 * Constructor
	 * @param list
	 */
	public Autocomplete(List<Term> list) {
		this.list = list;
	}
	
	/**
	 * @param prefix
	 *            string to be matched
	 * @return List of all matching terms in descending order by weight
	 */
	public List<Term> allMatches(String prefix){
		Collections.sort(list, Term.byPrefixOrder(prefix.length())); //sorts list by prefix order
		Term pref = new Term(prefix, 0); //create term that represents the prefix being searched
		int first = BinarySearchForAll.firstIndexOf(list, pref, Term.byPrefixOrder(prefix.length()));
		int last = BinarySearchForAll.lastIndexOf(list, pref, Term.byPrefixOrder(prefix.length()));
		List<Term> matches = new ArrayList<Term>();
		if (first != -1) { //if matches are found, return find sublist that contains those matches
			matches = list.subList(first, last + 1); 
		}
		Collections.sort(matches, Term.byReverseWeightOrder()); //sort sublist by wieght
		return matches;
	}
	
	public static void main(String [] args) {
		//create terms and put in list
		List<Term> list = new ArrayList<Term>();
		Term t1 = new Term("aazz", 4);
		Term t2 = new Term("aaay", 3);
		Term t3 = new Term("aaxx", 2);
		Term t4 = new Term("aaaw", 1);
		list.add(t2);
		list.add(t1);
		list.add(t4);
		list.add(t3);
		
		//print all terms that have prefix "aa"
		Autocomplete auto = new Autocomplete(list);
		System.out.println(auto.allMatches("aaa"));
		
	}
}
