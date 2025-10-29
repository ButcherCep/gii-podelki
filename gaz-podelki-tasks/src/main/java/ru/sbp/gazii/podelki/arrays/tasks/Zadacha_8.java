package ru.sbp.gazii.podelki.arrays.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/567/
public class Zadacha_8 {
    public static void main(String[] args) {
        Solution8 solution8 = new Solution8();
        solution8.moveZeroes(new int[]{0, 1, 0, 3, 12}); //1,3,12,0,0
    }

}

class Solution8 {
    public void moveZeroes(int[] nums) {
        int lengthNewArr = Arrays.stream(nums)
                .filter(a -> a != 0)
                .toArray().length;
        List<Integer> arr = Arrays.stream(nums)
                .filter(a -> a != 0)
                .boxed()
                .toList();
        int countofnull = nums.length - lengthNewArr;
        List<Integer> lastarr = new ArrayList<>();
        lastarr.addAll(arr);
        while (countofnull != 0) {
            lastarr.add(0);
            countofnull = countofnull - 1;
        }
        lastarr.stream().forEach(System.out::println);

    }
}
