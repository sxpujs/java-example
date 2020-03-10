package com.demo.leecode.dp;

public class S053MaximumSubarray {

    public int maxSubArray(int[] nums) {
        int maxSum = nums[0];
        int n = nums.length;
        int[] d = new int[n]; // 状态转移表
        d[0] = nums[0];
        for (int i = 1; i < n; i++) {
            d[i] = Math.max(d[i - 1] + nums[i], nums[i]);
            maxSum = Math.max(d[i], maxSum);
        }
        return maxSum;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(new S053MaximumSubarray().maxSubArray(nums));
    }
}
