package com.demo.algorithm;

import java.util.Arrays;

/**
 * 给定一个数组，数组中的正整数乱序，要求调整数组中数字顺序，使得任一奇数在所有偶数前，任一偶数在所有奇数后。
 * 示例：
 * [2，3，4，6，9，7] -> [3,9,7,2,4,6]
 *               or -> [7,9,3,4,2,6]
 * 要求尽可能高的时间和空间复杂度
 */
public class OddEvenArray {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 4, 6, 9, 7};
        System.out.println(Arrays.toString(transformArray(nums)));
    }

    public static int[] transformArray(int[] nums) {
        int left = 0, right = nums.length - 1;
        while(left <= right) {
            while (nums[left] % 2 == 1) {
                left++;
            }
            while (nums[right] % 2 == 0) {
                right--;
            }
            if (left < right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
            }
        }
        return nums;
    }
}