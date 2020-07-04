package com.demo.guava;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RateLimiterDemo {

    final RateLimiter rateLimiter = RateLimiter.create(5.0); // rate is "2 permits per second"

    void submitTasks(List<Runnable> tasks, Executor executor) {
        for (Runnable task : tasks) {
            rateLimiter.acquire(); // may wait
            executor.execute(task);
        }
    }

    public static void main(String[] args) {
        RateLimiterDemo demo = new RateLimiterDemo();
        Executor executor = Executors.newFixedThreadPool(100);
        List<Runnable> tasks = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            tasks.add(() -> {
                try {
                    log.info("start");
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
            });
        }
        demo.submitTasks(tasks, executor);
    }
}