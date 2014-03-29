package com.rohitsinha.hackerrank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Your algorithms have become so good at predicting the market that you now
 * know what the share price of Wooden Orange Toothpicks Inc. (WOT) will be for
 * the next N days.
 * 
 * Each day, you can either buy one share of WOT, sell any number of shares of
 * WOT that you own or not make any transaction at all. What is the maximum
 * profit you can obtain with an optimum trading strategy?
 * 
 * Input
 * 
 * The first line contains the number of test cases T. T test cases follow:
 * 
 * The first line of each test case contains a number N. The next line contains
 * N integers, denoting the predicted price of WOT shares for the next N days.
 * 
 * Output
 * 
 * Output T lines, containing the maximum profit which can be obtained for the
 * corresponding test case.
 * 
 * Constraints
 * 
 * 1 <= T <= 10 1 <= N <= 50000
 * 
 * All share prices are between 1 and 100000
 * 
 * Sample Input
 * 
 * 3 3 5 3 2 3 1 2 100 4 1 3 1 2 Sample Output
 * 
 * 0 197 3 Explanation
 * 
 * For the first case, you cannot obtain any profit because the share price
 * never rises. For the second case, you can buy one share on the first two
 * days, and sell both of them on the third day. For the third case, you can buy
 * one share on day 1, sell one on day 2, buy one share on day 3, and sell one
 * share on day 4.
 * 
 * Problem Link : https://www.hackerrank.com/challenges/stockmax
 * 
 * @author Rohit Sinha
 * Date: 03/29/2014
 * 
 */
public class StockMaximize {

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
	 * @return : an long array which contains size number of integers
	 */
	private static long[] getElements(String elements, int size) {

		String[] elementsArray = elements.trim().split(" ");

		if (elementsArray.length != size)
			throw new IllegalArgumentException("Number of elements in the list does not match the size provided");

		long[] numbers = new long[size];
		int index = 0;

		// extract integers
		for (String ele : elementsArray) {
			try {
				numbers[index++] = Long.parseLong(ele);
			} catch (NumberFormatException nfe) {
				System.out.println("Caught NumberFormatException: Unable to parse to long");
				nfe.getStackTrace();
			}

		}
		return numbers;
	}

	/**
	 * Function to pre-process the stock price array and find the days on which
	 * we should sell shares to get maximum profit This pre-processing helps us
	 * to solve this problem in O(n) time
	 * 
	 * @param stockPrices
	 *            : array of stock prices for different days
	 * @param days
	 *            : the number of days for which we have stock prices which is
	 *            the size of the above array
	 * @return : a array of boolean which has true marked for selling days
	 */
	private static boolean[] findSellingDays(long[] stockPrices, int days) {

		boolean[] sellOn = new boolean[days]; // an array to keep track on days on which we can sell
		Arrays.fill(sellOn, false); // fill with false

		long localMax = Long.MIN_VALUE; // initialize the local max with minimum value possible

		// traverse the array from back and if there mark selling days for local maximum
		for (int i = days - 1; i >= 0; i--) {
			if (localMax < stockPrices[i]) { // found better local maximum
				localMax = stockPrices[i]; // update local maximum
				sellOn[i] = true; // sell all existing shares on this day
			}
		}
		return sellOn;
	}

	/**
	 * Function to calculate maximum profit from a given array of stock prices
	 * 
	 * @param stockPrices
	 *            : an array containing the stock prices for different days
	 * @param days
	 *            : total number of days for which we know the stock price i.e.
	 *            the size of the above array
	 * @return : the maximum profit which can be made by trading on these stock
	 *         prices
	 */
	private static long calcMaxProfit(long[] stockPrices, int days) {

		// pre-process the stock price array and find the days on which we should 
		// sell the shares for maximum profit
		boolean[] sellOn = findSellingDays(stockPrices, days);

		long totalCost = 0, totalProfit = 0;
		int numShares = 0;

		// go through all the array containing stock prices and do the transaction
		// if this is a buying day  i.e. sellOn[i] is false buy that share else if sellOn[i]
		// if true and we have something to sell then sell it else just stay put
		for (int i = 0; i < days; i++) {
			if (sellOn[i] == false) { // buy shares
				totalCost += stockPrices[i];
				numShares++;
			} else if (totalCost != 0) { // not a buying day and if we have something to sell then sell it
				totalProfit += numShares * stockPrices[i] - totalCost; // calculate the profit
				// we sold everything so reset these variables
				totalCost = 0;
				numShares = 0;
			}
		}
		return totalProfit;
	}

	public static void main(String[] args) {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		try {
			int testCases = Integer.parseInt(input.readLine().trim());

			if (testCases <= 0) {
				throw new IllegalArgumentException("The number of test cases must be greater than 0");
			}

			while (testCases > 0) {
				int days = Integer.parseInt(input.readLine().trim());
				if (days <= 0) {
					throw new IllegalArgumentException("The number of test cases must be greater than 0");
				}
				long[] stockPrices = getElements(input.readLine(), days);

				long maxProfit = calcMaxProfit(stockPrices, days);
				System.out.println(maxProfit);
				testCases--;
			}
			input.close();
		} catch (NumberFormatException nfe) {
			System.out.println("Caught NumberFormatException: Unable to parse inputs from stdin");
			nfe.getStackTrace();
		} catch (IOException ioe) {
			System.out.println("Caught IOException: Unable to read inputs from stdin");
			ioe.getStackTrace();
		}
	}
}
