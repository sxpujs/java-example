package com.demo.concurrent;

import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class ListenableFutureDemo {

    // 创建一个由invoke线程执行的线程池
    static ListeningExecutorService executorService = MoreExecutors.listeningDecorator(
            Executors.newFixedThreadPool(2));

    ExecutorService es = Executors.newSingleThreadExecutor();

    //static ListeningExecutorService executorService = MoreExecutors.newDirectExecutorService();

    static Callable multiple(int a, int b) throws InterruptedException {
        Thread.sleep(1000);
        return () -> a * b;
    }

    public static void test1() throws Exception {

        log.info("begin");
        ListenableFuture<?> listenableFuture = executorService.submit(multiple(3, 10));

        listenableFuture.addListener(() -> {
            log.info("listen success");
        }, executorService);

        // FutureCallback接口包含onSuccess()、onFailure()两个方法
        Futures.addCallback(listenableFuture, new FutureCallback<Object>() {
            @Override
            public void onSuccess(@Nullable Object result) {
                log.info("res={}", result);
            }

            @Override
            public void onFailure(Throwable t) {
                log.error("error={}", t.getMessage());
            }
        }, executorService);

        //输出：
        //20:10:10.220 [main] INFO  c.d.concurrent.ListenableFutureDemo - begin
        //20:10:11.246 [main] INFO  c.d.concurrent.ListenableFutureDemo - listen success
        //20:10:11.252 [main] INFO  c.d.concurrent.ListenableFutureDemo - res=30

    }

    public static void test2() {
        ListenableFuture<Integer> future1 = executorService.submit(() -> 1 + 2);
        ListenableFuture<Integer> future2 = executorService.submit(() -> Integer.parseInt("3q"));
        ListenableFuture<List<Object>> futures = Futures.allAsList(future1, future2);
        futures = Futures.successfulAsList(future1, future2);

        Futures.addCallback(futures, new FutureCallback<>() {
            @Override
            public void onSuccess(@Nullable List<Object> result) {
                log.info(String.valueOf(result));
            }

            @Override
            public void onFailure(Throwable t) {
                log.warn(t.getMessage());
            }
        }, executorService);
        //输出：
        //20:11:39.992 [main] INFO  c.d.concurrent.ListenableFutureDemo - [3, null]
    }

    public static void test3() throws Exception {
        // 原Future
        ListenableFuture<String> future3 = executorService.submit(() -> "hello, future");
        // 同步转换
        ListenableFuture<Integer> future5 = Futures.transform(future3, String::length, executorService);
        // 异步转换
        ListenableFuture<Integer> future6 = Futures.transformAsync(future3, input -> Futures.immediateFuture(input.length()), executorService);

        log.info(future3.get());
        log.info(String.valueOf(future5.get()));
        log.info(String.valueOf(future6.get()));
        //输出：
        //20:12:24.810 [main] INFO  c.d.concurrent.ListenableFutureDemo - hello, future
        //20:12:24.812 [main] INFO  c.d.concurrent.ListenableFutureDemo - 13
        //20:12:24.812 [main] INFO  c.d.concurrent.ListenableFutureDemo - 13
    }

    public static void main(String[] args) throws Exception {
        //test1();
        //test2();
        //test3();
        //executorService.shutdown();

        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("finish");
            });
        }
    }
}