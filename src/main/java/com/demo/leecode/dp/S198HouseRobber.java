package com.demo.leecode.dp;

/**
 * 题目：https://leetcode-cn.com/problems/house-robber/
 * 解题：https://leetcode-cn.com/problems/house-robber/solution/da-jia-jie-she-by-leetcode/
 */
public class S198HouseRobber {

    public int rob(int[] num) {
        int prevMax = 0;
        int currMax = 0;
        for (int x : num) {
            int temp = currMax;
            currMax = Math.max(prevMax + x, currMax);
            prevMax = temp;
        }
        return currMax;
    }

    public static void main(String[] args) {
        int[] num = new int[]{10, 3, 6, 100, 99};
        System.out.println(new S198HouseRobber().rob(num));

        num = new int[]{10, 3, 6, 100, 90};
        System.out.println(new S198HouseRobber().rob(num));
    }
}