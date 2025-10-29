package ru.sbp.gazii.podelki.arrays.tasks;

import java.util.Arrays;

//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/727/
public class Zadacha_1 {
    public static void main(String[] args) {
        int[] nums = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        System.out.println(removeDuplicates2(nums));
    }

    private static int removeDuplicates(int[] nums) {
        return (int) Arrays.stream(nums).distinct().count();

    }

    private static int removeDuplicates2(int[] nums) {
        int a = 0; //счетчик
        for (int i = 0; i < nums.length; i++) {
            if (i + 1 != nums.length && nums[i] != nums[i + 1]) {
                a = a + 1;
            }
        }
        return a;
    }
}
