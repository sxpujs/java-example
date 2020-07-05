package com.demo.concurrent;

import java.util.concurrent.atomic.AtomicLong;

public class ThreadVariableVisibility {

    private AtomicLong atomicLong = new AtomicLong(0);
    private int count = 0;

    private void add10K() {
        int idx = 0;
        while(idx++ < 1000000L) {
            atomicLong.getAndIncrement();
            count += 1;
        }
    }

    public AtomicLong calc() throws InterruptedException {
        // 创建两个线程，执行add()操作
        Thread th1 = new Thread(()->{
            this.add10K();
        });
        Thread th2 = new Thread(()->{
            this.add10K();
        });
        // 启动两个线程
        th1.start();
        th2.start();
        // 等待两个线程执行结束
        th1.join();
        th2.join();


        System.out.println("atomicLong=" + atomicLong.get() + ", count=" + count);
        return atomicLong;
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadVariableVisibility instance = new ThreadVariableVisibility();
        System.out.println(instance.calc());
    }
}