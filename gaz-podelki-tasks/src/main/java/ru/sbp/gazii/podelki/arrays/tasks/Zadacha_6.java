package ru.sbp.gazii.podelki.arrays.tasks;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/674/
public class Zadacha_6 {
    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 2, 2, 1}; //495 //49
        int[] nums2 = new int[]{2, 2}; //948 // 94
        Arrays.stream(intersect(nums1, nums2)).forEach(System.out::println);
    }

    public static int[] intersect(int[] nums1, int[] nums2) {
        Set<Integer> normset = new HashSet<>();
        Set<Integer> huev = new HashSet<>();
        Set<Integer> set1 = Arrays.stream(nums1)
                .boxed()
                .collect(Collectors.toSet());
        Set<Integer> set2 = Arrays.stream(nums2)
                .boxed()
                .collect(Collectors.toSet());
        for (Integer s : set1) {
            Boolean a = set2.contains(s) ? normset.add(s) : huev.add(s);
        }
//        huev.stream().mapToInt(Integer::intValue).forEach(System.out::println);
        return normset.stream().mapToInt(Integer::intValue).toArray();
    }
}
