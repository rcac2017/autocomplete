package autocomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class BinarySearchForAll {
	// flag indicating whether a key occurs at all in the list
	public static final int NOT_FOUND = -1;
	
	/**
	 * recursively searches a list for the first or last occurrence of an element in a list
	 * and returns the index, or -1 if it doesn't exist
	 * 
	 * @param <Key> generic type
	 * @param aList list being search
	 * @param key element searching for
	 * @param comparator comparator by which list was sorted
	 * @param lo start index, inclusive
	 * @param hi end index, exclusive
	 * @param fOrL, true if searching for first occurrence, false if searching for last
	 * @return returns index of first or last occurrence of key in aList, or -1 if not found
	 */
	private static <Key> int recBinSearch(List<Key> aList, Key key,
			Comparator<Key> comparator, int lo, int hi, boolean fOrL) {
		if (lo < hi) { // checks lo and hi haven't crossed
			int mid = lo + (hi - lo)/2;
			if (comparator.compare(key, aList.get(mid)) < 0) { //if key should be on the left
				return recBinSearch(aList, key, comparator, lo, mid, fOrL);
			}else if (comparator.compare(key, aList.get(mid)) > 0) { //if key should be on the right
				return recBinSearch(aList, key, comparator, mid + 1, hi, fOrL);
			}else { //if key is found
				if (fOrL){ //if searching for first, go left until last key is found
					while (mid != 0 && comparator.compare(key, aList.get(mid-1)) == 0) {
						mid--; 
					}
				}else { //if searching for last, go right until last key is found
					while (mid != (aList.size()-1) && comparator.compare(key, aList.get(mid+1)) == 0) {
						mid++; 
					}
				}
				return mid;
			}
		}
		return NOT_FOUND; //return if key not found
	}

	/**
	 * Returns the index of the first element in aList that equals key
	 *
	 * @param aList
	 *            Ordered (via comparator) list of items to be searched
	 * @param key
	 *            item searching for
	 * @param comparator
	 *            Object with compare method corresponding to order on aList
	 * @return Index of first item in aList matching key or -1 if not in aList
	 **/
	public static <Key> int firstIndexOf(List<Key> aList, Key key,
			Comparator<Key> comparator) {
		return recBinSearch(aList, key, comparator, 0, aList.size(), true);
	}


	/**
	 * Returns the index of the last element in aList that equals key
	 * 
	 * @param aList
	 *            Ordered (via comparator) list of items to be searched
	 * @param key
	 *            item searching for
	 * @param comparator
	 *            Object with compare method corresponding to order on aList
	 * @return Location of last item of aList matching key or -1 if no such key.
	 **/
	public static <Key> int lastIndexOf(List<Key> aList, Key key,
			Comparator<Key> comparator) {
		return recBinSearch(aList, key, comparator, 0, aList.size(), false);
	}
	
	public static void main(String [] args) {
		//create term objects and add to list
		List<Term> list = new ArrayList<Term>();
		Term t1 = new Term("aazz", 4);
		Term t2 = new Term("aaay", 3);
		Term t3 = new Term("aaxx", 2);
		Term t4 = new Term("aaaw", 1);
		list.add(t2);
		list.add(t1);
		list.add(t4);
		list.add(t3);
		
		//create term that has the prefix to search for
		Term comp = new Term("a", 0);
		Collections.sort(list, Term.byPrefixOrder(1)); //sort list using appropriate comparator
		System.out.println(list);
		System.out.println(BinarySearchForAll.firstIndexOf(list, comp, Term.byPrefixOrder(1))); //print first index
		System.out.println(BinarySearchForAll.lastIndexOf(list, comp, Term.byPrefixOrder(1))); //print last index
	}
}
