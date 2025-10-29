package ru.sbp.gazii.podelki.arrays.tasks;

//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/549/
public class Zadacha_5 {
    public static void main(String[] args) {
        int[] mass = new int[]{4, 1, 2, 1, 2};
        System.out.println(singleNumber(mass));
    }

    public static int singleNumber(int[] nums) {
        int result = 0;
        for (int i = 0; i <= nums.length; i++) {
            result = result ^ nums[i];
        }
        return result;
    }
}
