package com.demo.algorithm;

public class ReverseInt {

    public static void main(String[] args) {
        System.out.println(numReverse(1234560));
    }

    public static int numReverse(int num) {
        int modNum = 0;// 余数
        int result = 0;
        while (num / 10 != 0) {// 当num为一位数时，跳出循环
            modNum = num % 10;
            num = num / 10;
            result = result * 10 + modNum;
        }
        return result * 10 + num;// 当num为一位数时，返回结果
    }
}
