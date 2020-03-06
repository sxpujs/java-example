package com.demo.redis;

import com.google.common.base.Stopwatch;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JedisPipelineDemo {

    Jedis jedis = new Jedis("localhost");

    void withoutPipeline() {
        for (int i = 0; i < 10000; i++)
            jedis.ping();
    }

    void withPipeline() {
        Pipeline p = jedis.pipelined();
        for (int i = 0; i < 10000; i++)
            p.ping();
        p.sync(); // 获取所有的响应
    }

    void serialTask() {
        printTime(() -> withoutPipeline(), "withoutPipeline"); // 346.4 ms
        printTime(() -> withPipeline(), "withPipeline");       // 18.34 ms
    }

    // 虽然传入的是Runnable对象，直接调用run方法并没有创建线程，而是使用当前线程同步执行方法。
    void printTime(Runnable task, String taskName) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        task.run();
        System.out.println(taskName + " took: " + stopwatch.stop());
    }

    void concurrentTask() {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(() -> withoutPipeline());
        pool.submit(() -> withPipeline());
        pool.shutdown();
    }

    public static void main(String[] args) {
        JedisPipelineDemo demo = new JedisPipelineDemo();
        demo.serialTask();
    }
}