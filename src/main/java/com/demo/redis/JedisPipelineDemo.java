package com.demo.redis;

import com.google.common.base.Stopwatch;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
    }

    void serialTask() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        withoutPipeline(); // 310 ms
        System.out.println("withoutPipeline time: " + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) + " ms");

        stopwatch.reset().start();
        withPipeline(); // 16 ms
        System.out.println("withPipeline time: " + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) + " ms");
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