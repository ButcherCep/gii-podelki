package ru.sbp.gazii.podelki.arrays.tasks;

import java.util.Arrays;
import java.util.stream.Collectors;

//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/578/
public class Zadacha_4 {
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,1};
//        System.out.println(containsDuplicate(arr));
        System.out.println(containsDuplicateStream(arr));
    }
    public static boolean containsDuplicateStream(int[] nums) {
        return nums.length!= Arrays.stream(nums).boxed().collect(Collectors.toSet()).size();
    }
}
