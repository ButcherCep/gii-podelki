package ru.sbp.gazii.podelki.arrays.tasks;

import java.util.stream.IntStream;

//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/564/
public class Zadacha_2 {
    public static void main(String[] args) {
        int[] prices = new int[]{7,1,5,3,6,4};
        System.out.println(maxProfit(prices));
    }
    public static int maxProfit(int[] prices) {
        int count = 0;
        for (int i=1; i<prices.length; i++) {
            if (prices[i] > prices[i-1]) {
                count = count + prices[i] - prices[i-1];
            }
        }
        return count;
    }
    public static int calculateMaxProfit(int[] prices) {
        return IntStream.range(1, prices.length)
                .filter(i -> prices[i] > prices[i - 1])
                .map(i -> prices[i] - prices[i - 1])
                .sum();
    }

}
