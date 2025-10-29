package ru.sbp.gazii.podelki.arrays.tasks;

import java.util.Arrays;
import java.util.HashMap;

//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/546/
public class Zadacha_9 {
    public static void main(String[] args) {
        Arrays.stream(twoSum(new int[]{2, 11, 7, 15}, 9))
                .forEach(System.out::println);
    }

    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i]; //9-(2,7,11,15)
            if (map.containsKey(complement)) {
                return new int[] {map.get(complement), i};
            }
            map.put(nums[i], i);
        }

        throw new IllegalArgumentException("No two sum solution");
    }
}
