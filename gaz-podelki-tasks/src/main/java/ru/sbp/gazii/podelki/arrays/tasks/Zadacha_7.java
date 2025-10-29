package ru.sbp.gazii.podelki.arrays.tasks;

import java.util.Arrays;

//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/559/
public class Zadacha_7 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        Arrays.stream(solution
                        .plusOne(new int[]{1, 9, 9, 9}))
                .boxed()
                .mapToInt(Integer::intValue)
                .forEach(System.out::println);
    }
}

class Solution {
    public int[] plusOne(int[] digits) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < digits.length; i++) {
            str.append(digits[i]);
        }
        return Arrays.stream(String
                        .valueOf(Integer.parseInt(str.toString()) + 1)
                        .split(""))
                .mapToInt(Integer::valueOf)
                .toArray();
    }
}

