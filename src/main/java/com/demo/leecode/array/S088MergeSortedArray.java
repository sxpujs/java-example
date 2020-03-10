package com.demo.leecode.array;

import java.util.Arrays;

/**
 * 题目：https://leetcode-cn.com/problems/merge-sorted-array/
 * 官方解题：https://leetcode-cn.com/problems/merge-sorted-array/solution/he-bing-liang-ge-you-xu-shu-zu-by-leetcode/
 */
public class S088MergeSortedArray {

    public static void main(String[] args) {
        int[] nums1 = new int[]{1,2,3};
        int[] nums2 = new int[]{2,5,6};
        int[] result = new S088MergeSortedArray().merge(nums1, nums2);
        System.out.println(Arrays.toString(result));
    }

    public int[] merge(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[] result = new int[m + n];
        // Two get pointers for nums1 and nums2.
        int p1 = 0;
        int p2 = 0;
        int p = 0;

        while ((p1 < m) && (p2 < n))
            result[p++] = (nums1[p1] < nums2[p2]) ? nums1[p1++] : nums2[p2++];

        // if there are still elements to add
        while (p1 < m)
            result[p++] = nums1[p1++];
        while (p2 < n)
            result[p++] = nums2[p2++];
        return result;
    }
}