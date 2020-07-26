package autocomplete;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Term implements Comparable<Term> {
	private String key;
	private long weight;
	

	/**
	 * Initializes a term with the given query string and weight.
	 * 
	 * @param query
	 *            word to be stored
	 * @param weight
	 *            associated frequency
	 */
	public Term(String query, long weight) {
		key = query;
		this.weight = weight;
	}

	/**
	 * @return instance variable key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * @return instance variable weight
	 */
	public long getWeight() {
		return weight;
	}
	
	/**
	 * return prefix of length n of string
	 * 
	 * @param str input string
	 * @return str first n characters of string, or whole string of length > n
	 */
	private static String firstN(String str, int n) {          
		 if(str.length() < n) { //return whole string if length less than n
		     return str;
		 }else { //return substring of length n
		     return str.substring(0,n);
		 }
	}
		 
	/**
	 * @return comparator ordering elts by descending weight
	 */
	public static Comparator<Term> byReverseWeightOrder() {
		return (Term t1, Term t2) -> //compare weights of t1,t2, larger weight goes first
				Long.valueOf(t2.getWeight()).compareTo(Long.valueOf(t1.getWeight()));
	}	

	/**
	 * @param r
	 *            Number of initial characters to use in comparing words
	 * @return comparator using lexicographic order, but using only the first r
	 *         letters of each word
	 */
	public static Comparator<Term> byPrefixOrder(int r) {
		return (Term t1, Term t2) -> //compare prefixes of length r in t1,t2
				firstN(t1.getKey(),r).compareTo(firstN(t2.getKey(),r));
	}

	/**
	 * @param that
	 *            Term to be compared
	 * @return -1, 0, or 1 depending on whether the word for THIS is
	 *		   lexicographically smaller, same or larger than THAT
	 */
	public int compareTo(Term that) {
		return (this.key).compareTo(that.getKey()); //natural ordering
	}

	/**
	 * @return a string representation of this term in the following format: the
	 *         weight, followed by 2 tabs, followed by the word.
	 **/
	public String toString() {
		return String.valueOf(weight) + "\t\t " + key;    
	}

	public static void main(String [] args) {
		//create 4 term objects and add them to an arraylist
		ArrayList<Term> list = new ArrayList<Term>();
		Term t1 = new Term("aaaz", 4);
		Term t2 = new Term("aaay", 3);
		Term t3 = new Term("aaax", 2);
		Term t4 = new Term("aaaw", 1);
		list.add(t2);
		list.add(t1);
		list.add(t4);
		list.add(t3);
		System.out.println(list); //print initial order of list
		Collections.sort(list, Term.byReverseWeightOrder());
		System.out.println(list); //print list after sorting by weight
		Collections.sort(list, byPrefixOrder(4));
		System.out.println(list); //print list after sorting by prefix 	
	}
}
