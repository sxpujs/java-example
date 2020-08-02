package com.demo.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 测试Future用法
 */
@Slf4j
public class SquareCalculator {

    //private static ExecutorService executor = Executors.newSingleThreadExecutor();
    private static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static Future<Integer> calculate(Integer input) {
        return executor.submit(() -> {
            Thread.sleep(1000);
            return input * input;
        });
    }

    public static void testGet() throws InterruptedException, ExecutionException {
        Future<Integer> future = calculate(10);

        while (!future.isDone()) {
            log.info("Calculating...");
            Thread.sleep(300);
        }

        Integer result = future.get();
        log.info("result={}", result);
    }

    public static void testTimeout() {
        Future<Integer> future = calculate(10);
        Integer result = null;
        try {
            result = future.get(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.warn("invoke calculate timeout.");
        }
        log.info("result={}", result);
    }

    public static void testCancel() throws InterruptedException {
        Future<Integer> future = calculate(10);
        //Thread.sleep(2000);
        boolean canceled = future.cancel(true);
        log.info("canceled={}", canceled);
    }

    public static void testMultithreading() throws InterruptedException, ExecutionException {
        Future<Integer> future1 = calculate(10);
        Future<Integer> future2 = calculate(100);

        while (!(future1.isDone() && future2.isDone())) {
            log.info(
                    String.format(
                            "future1 is %s and future2 is %s",
                            future1.isDone() ? "done" : "not done",
                            future2.isDone() ? "done" : "not done"
                    )
            );
            Thread.sleep(300);
        }

        Integer result1 = future1.get();
        Integer result2 = future2.get();

        System.out.println(result1 + " and " + result2);

    }

    public static void main(String[] args) throws Exception {
        //testGet();
        //testTimeout();
        //testCancel();
        testMultithreading();
        executor.shutdown();
    }

}