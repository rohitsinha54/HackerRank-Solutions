package com.rohitsinha.hackerrank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Sorting is often useful as the first step in many different tasks. The most
 * common task may to be make things easier to find later, but there are other
 * uses also.
 * 
 * Challenge Given a list of unsorted numbers, can you find the numbers that
 * have the smallest absolute difference between them? If there are multiple
 * pairs, find them all.
 * 
 * Input Format There will be two lines of input:
 * 
 * n - the size of the list array - the n numbers of the list Output Format
 * Output the pairs of numbers with the smallest difference. If there are
 * multiple pairs, output all of them in ascending order, all on the same line
 * (consecutively) with just a single space between each pair of numbers. If
 * there's a number which lies in two pair, print it two times (see sample case
 * #3 for explanation).
 * 
 * Constraints 10 <= n <= 200000 -(107) <= x <= (107), x is in array arrary[i] !=
 * array[j], 0 <= i, j < N and i != j
 * 
 * Sample Input #1
 * 
 * 10 -20 -3916237 -357920 -3620601 7374819 -7330761 30 6246457 -6461594 266854
 * Sample Output #1
 * 
 * -20 30 Explanation 30- -20 = 50, which is the smallest difference.
 * 
 * Sample Input #2
 * 
 * 12 -20 -3916237 -357920 -3620601 7374819 -7330761 30 6246457 -6461594 266854
 * -520 -470 Sample Output #2
 * 
 * -520 -470 -20 30 Explanation (-470)-(-520) = 30- (-20) = 50, which is the
 * smallest difference.
 * 
 * Sample Input #3
 * 
 * 4 5 4 3 2 Sample Output #3
 * 
 * 2 3 3 4 4 5 Explanation Here minimum difference will be 1. So valid pairs (2,
 * 3), (3, 4), (4, 5). So we have to print 2 one time, 3 and 4 two times and 5
 * one time.
 * 
 * Link: https://www.hackerrank.com/challenges/closest-numbers
 * 
 * @author Rohit Sinha
 * Date: 03/27/2014
 * 
 */
public class ClosestNumbers {

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
	 * @return : an int array which contains size number of integers
	 */
	private static long[] getElements(String elements, int size) {

		String[] elementsArray = elements.trim().split(" ");

		if (elementsArray.length != size)
			throw new IllegalArgumentException("Number of elements in the list does not match the size provided");

		long[] numbers = new long[size];
		int index = 0;

		// extract integers
		for (String ele : elementsArray) {
			numbers[index++] = Long.parseLong(ele);
		}
		return numbers;
	}

	/**
	 * Function to find minimum difference between two elements in the given
	 * array Reference:
	 * http://www.cs.mcgill.ca/~cs251/ClosestPair/ClosestPair1D.html
	 * 
	 * @param array
	 *            : array containing the elements
	 * @param start
	 *            : the starting index
	 * @param end
	 *            : the end index
	 * @return: minimum difference between two pair of elements from the start
	 *          to end indices
	 */
	private static long findMinDiff(long[] array, int start, int end) {

		if (start == end) // base case
			return Long.MAX_VALUE;
		else if (end - start == 1) // base case
			return (array[end] - array[start]);

		int mid = (start + end) / 2; // the middle index

		long leftDiff = Math.abs(findMinDiff(array, start, mid)); // calculate the min diff for left side
		long rightDiff = Math.abs(findMinDiff(array, mid + 1, end)); // calculate the min diff for right side

		long diffMid = Math.abs(array[mid + 1] - array[mid]); // for case when we have one element on each side

		return Math.min(diffMid, Math.min(leftDiff, rightDiff)); // return the min of all three differences
	}

	/**
	 * Function to print the pairs of number which are closest to each other in
	 * a sorted array. There can be more than one such pair.
	 * 
	 * @param array
	 *            : a non-decreasing array of numbers
	 * @param arraySize
	 *            : the size of the array
	 */
	private static void printClosestNumbers(long[] array, int arraySize) {

		long minDiff = findMinDiff(array, 0, arraySize - 1); // find the minimum difference

		for (int i = 0; i < arraySize - 1; i++) { // print all pair of numbers which have the difference between them 
													//equal to minimum diff
			if (Math.abs(array[i + 1] - array[i]) == minDiff) {
				System.out.print(array[i] + " " + array[i + 1] + " ");
			}
		}

	}

	public static void main(String[] args) {

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		try {
			int listSize = Integer.parseInt(input.readLine());
			long[] list = getElements(input.readLine(), listSize);

			// sort this  array
			Arrays.sort(list);

			// find Closest numbers in the array
			printClosestNumbers(list, listSize);

			input.close();
		} catch (NumberFormatException e) {
			System.err.println("Caught NumberFormatException: Error in coverting to interger");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Caught IOException: Error in reading input from stdin");
			e.printStackTrace();
		}
	}
}
