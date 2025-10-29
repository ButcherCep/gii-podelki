package ru.sbp.gazii.podelki.arrays.tasks;

import java.util.Arrays;
import java.util.stream.Stream;

//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/646/
public class Zadacha_3 {
    public static void main(String[] args) {
        int[] numes = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; //5,6,7,1,2,3,4
        int k = 3;
        rotate(numes, k);
//        rotatestream(numes, k);
    }

    public static void rotate(int[] nums, int k) {
        int[] newarr = Arrays.copyOf(nums, nums.length);
        Arrays.stream(newarr).skip(nums.length - k);
        Arrays.stream(nums).limit(nums.length - k);
        Stream.concat(Arrays.stream(newarr).skip(nums.length - k).boxed()
                , Arrays.stream(nums).limit(nums.length - k).boxed()).forEach(System.out::println);
    }

    public static void rotatestream(int[] nums, int k) {
        int[] newArr = Arrays.copyOfRange(nums, nums.length - k, nums.length);
        int[] newArr2 = Arrays.copyOfRange(nums, 0, nums.length - k);
        int[] arrAll = new int[nums.length];
        System.arraycopy(newArr, 0, arrAll, 0, newArr.length);
        System.arraycopy(newArr2, 0, arrAll, newArr.length, newArr2.length);
        Arrays.stream(arrAll).forEach(System.out::print);

    }
}
