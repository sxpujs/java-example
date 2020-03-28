package com.demo.designpattern;

import java.util.*;
import java.util.concurrent.*;

public class SingletonTest {

    public static void main(String[] args) {
        // 定义可返回计算结果的非线程安全的Callback实例
        Callable<Integer> unsafeCallableTask = () -> Singleton.getSingleton().getUnsafeNext();
        runTask(unsafeCallableTask);
        // unsafe counter may less than 1000, i.e. 984
        System.out.println("current counter = " + Singleton.getSingleton().getUnsafeCounter());

        // 定义可返回计算结果的线程安全的Callback实例（基于AtomicInteger）
        Callable<Integer> safeCallableTask = () -> Singleton.getSingleton().getSafeNext();
        runTask(safeCallableTask);
        // safe counter should be 1000
        System.out.println("current counter = " + Singleton.getSingleton().getSafeCounter());

    }

    public static void runTask(Callable<Integer> callableTask) {
        List<Callable<Integer>> callableTasks = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            callableTasks.add(callableTask);
        }
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService threadPool = Executors.newFixedThreadPool(cores);
        Map<Integer, Integer> frequency = new HashMap<>();
        try {
            List<Future<Integer>> futures = threadPool.invokeAll(callableTasks);
            for (Future<Integer> future : futures) {
                frequency.put(future.get(), frequency.getOrDefault(future.get(), 0) + 1);
                //System.out.printf("counter=%s\n", future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        threadPool.shutdown();
    }
}