package com.rohitsinha.hackerrank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Given N integers, count the total pairs of integers that have a difference of
 * K.
 * 
 * Input Format 1st line contains N & K (integers). 2nd line contains N numbers
 * of the set. All the N numbers are assured to be distinct.
 * 
 * Output Format One integer saying the number of pairs of numbers that have a
 * diff K.
 * 
 * Constraints: N <= 10^5 0 < K < 10^9 Each integer will be greater than 0 and
 * at least K away from 2^31-1
 * 
 * Sample Input #00:
 * 
 * 5 2 1 5 3 4 2 Sample Output #00:
 * 
 * 3 Sample Input #01:
 * 
 * 10 1 363374326 364147530 61825163 1073065718 1281246024 1399469912 428047635
 * 491595254 879792181 1069262793 Sample Output #01: 0
 * 
 * Link: https://www.hackerrank.com/challenges/pairs
 * 
 * @author Rohit Sinha Date: 03/27/2014
 * 
 */
public class Pairs {

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
	 * Function to print the number of pairs of elements in a non-decreasing
	 * array which have k difference in their value
	 * 
	 * @param array
	 *            : a non-decreasign array
	 * @param arraySize
	 *            : size of the array
	 * @param k
	 *            : the difference
	 * @return : total numbers of pairs which have k difference between them
	 */
	private static int countAtKDiff(int[] array, int arraySize, int k) {

		int count = 0; // initialize the counter

		for (int i = 0, j = 1; i < arraySize && j < arraySize;) {
			if (array[j] - array[i] == k) { // found a pair
				count++;
				i++;
				j++;
			} else if (array[j] - array[i] < k) { // difference is less than wanted so increment right pointer
				j++;
			} else { // difference is more than wanted so increment the left pointer 
				i++;
			}
		}

		return count;
	}

	public static void main(String[] args) {

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		try {
			String[] line1 = input.readLine().trim().split(" ");
			int listSize = Integer.parseInt(line1[0]);
			int k = Integer.parseInt(line1[1]);
			int[] list = getElements(input.readLine(), listSize);

			// sort this  array
			Arrays.sort(list);

			// print the count of pairs which are at k distance
			System.out.println(countAtKDiff(list, listSize, k));

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
