package com.rohitsinha.hackerrank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * Sometimes you need to compare lists of number, but sorting each one normally
 * will take too much time. Instead you can use alternative methods to find the
 * differences between each list.
 * 
 * Challenge Numeros The Artist was arranging two identical lists A and B into
 * specific orders. The arrangements of the two arrays were random, Numeros was
 * very proud of his arrangements. Unfortunately, some numbers got left out of
 * List A. Can you find the missing numbers from A without messing up his order?
 * 
 * Details There are many duplicates in the lists, but you need to find the
 * extra numbers, i.e. B - A. Print the numbers in numerical order. Print each
 * missing number once, even if it is missing multiple times. The numbers are
 * all within a range of 100 from each other.
 * 
 * Input Format There will be four lines of input:
 * 
 * n - the size of the first list This is followed by n numbers that makes up
 * the first list. m - the size of the second list This is followed by m numbers
 * that makes up the second list.
 * 
 * Output Format Output all the numbers (in ascending order) that are in B but
 * not in A.
 * 
 * Constraints 1<= n,m <= 1000001 -10000 <= x <= 10000 , x  B Xmax - Xmin < 101
 * 
 * Sample Input
 * 
 * 10 203 204 205 206 207 208 203 204 205 206 13 203 204 204 205 206 207 205 208
 * 203 206 205 206 204 Sample Output
 * 
 * 204 205 206 Explanation Although 204 presented in both arrays, but 204's
 * frequency in A is smaller than that of B. Similarly 205 and 206 occur twice
 * in A but thrice in B. So, these three numbers constitute the output. The rest
 * of the numbers occur at least as many times in A as they do in B - so they
 * are not "missing numbers".
 * Link : https://www.hackerrank.com/challenges/missing-numbers
 * 
 * @author Rohit Sinha
 * Date: 03/27/2014
 * 
 */
public class MissingNumbers {

	/**
	 * Function which creates a an integer array from string consisting of
	 * integers separated by spaces. Throws a {@link IllegalArgumentException}
	 * if the number of integers present in the string is not equal to the size
	 * expected
	 * 
	 * @param elements
	 *            : String containing integers separated by a space
	 * @param size
	 *            : the expected number of integers in the string supplied
	 * @return : an int array which contains size number of intergers
	 */
	private static int[] getElements(String elements, int size) {

		String[] elementsArray = elements.trim().split(" ");

		if (elementsArray.length != size)
			throw new IllegalArgumentException("Number of elements in the list does not match the size provided");

		int[] numbers = new int[size];
		int index = 0;

		// extract integers
		for (String ele : elementsArray) {
			numbers[index++] = Integer.parseInt(ele);
		}
		return numbers;
	}

	/**
	 * Function to find list2 - list1
	 * 
	 * @param list1
	 *            : the first list of numbers
	 * @param list2
	 *            : the second list of numbers (the bigger list)
	 * @return : a {@link PriorityQueue} which list2 - list1 in sorted manner
	 */
	private static PriorityQueue<Integer> findMissingNumbers(int[] list1, int[] list2) {
		Hashtable<Integer, Integer> freqCounter = new Hashtable<Integer, Integer>();

		// process B
		for (int ele : list2) {
			if (freqCounter.containsKey(ele)) {
				freqCounter.put(ele, (freqCounter.get(ele)) + 1); // increment
																	// the
																	// Occurrence
			} else {
				freqCounter.put(ele, 1); // first occurrence
			}
		}

		// process A
		for (int ele : list1) {
			if (freqCounter.containsKey(ele)) {
				freqCounter.put(ele, (freqCounter.get(ele)) - 1); // decrement
																	// the value
			} else {
				// we found a number which was not in List2 and is in List1
				// this should not happen so report it
				throw new NoSuchElementException("Found a number in B which is not in A");
			}
		}

		// now every number which has positive occurrence gives B - A
		// create a priority with keys which has positive occurrence
		PriorityQueue<Integer> result = new PriorityQueue<Integer>();
		for (Map.Entry<Integer, Integer> entry : freqCounter.entrySet()) {
			if (entry.getValue() > 0) {
				result.offer(entry.getKey());
			}
		}
		return result;
	}

	public static void main(String[] args) {

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		try {
			int list1Size = Integer.parseInt(input.readLine());
			int[] list1 = getElements(input.readLine(), list1Size);
			int list2Size = Integer.parseInt(input.readLine());
			int[] list2 = getElements(input.readLine(), list2Size);

			PriorityQueue<Integer> result = findMissingNumbers(list1, list2);
			int resultSize = result.size();
			for (int i = 0; i < resultSize; i++) {
				System.out.print(result.remove());
				if (i < resultSize - 1)
					System.out.print(" ");
			}
			input.close();

		} catch (IOException e) {
			System.err.println("Caught IOException: Error while reading input from stdin");
			e.printStackTrace();
		}
	}
}
