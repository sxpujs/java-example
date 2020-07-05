package com.demo.guava;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Slf4j
public class RateLimiterDemo {

    static void submitTasks1() {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        RateLimiter rateLimiter = RateLimiter.create(5); // rate is "5 permits per second"
        IntStream.range(0, 10).forEach(i -> pool.submit(() -> {
            if (rateLimiter.tryAcquire()) {
                try {
                    log.info("start");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            } else {
                log.warn("限流");
            }
        }));
        pool.shutdown();
    }

    static void submitTasks2() {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        RateLimiter rateLimiter = RateLimiter.create(5); // rate is "5 permits per second"
        IntStream.range(0, 10).forEach(i -> pool.submit(() -> {
            rateLimiter.acquire();
            log.info("start");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        pool.shutdown();
    }

    static void submitTasks3() {
        RateLimiter r = RateLimiter.create(5);
        log.info("start");
        for (;;) {
            log.info("get 1 tokens: " + r.acquire() + "s");
        }
    }

    static void submitTasks4() {
        RateLimiter r = RateLimiter.create(5);
        log.info("start");
        for (;;) {
            if (r.tryAcquire()) {
                log.info("run");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //submitTasks1();
        submitTasks2();
        //submitTasks3();
        //submitTasks4();
    }
}