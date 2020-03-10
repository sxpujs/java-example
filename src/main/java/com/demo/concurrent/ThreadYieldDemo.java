package com.demo.concurrent;

public class ThreadYieldDemo {

    public static void main(String[] args) {
        Runnable prod = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("I am Producer : Produced Item " + i);
                Thread.yield();
            }
        };
        Runnable cons = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("I am Consumer : Consumed Item " + i);
                Thread.yield();
            }
        };
        Thread producer = new Thread(prod);
        Thread consumer = new Thread(cons);

        producer.setPriority(Thread.MIN_PRIORITY); //Min Priority
        consumer.setPriority(Thread.MAX_PRIORITY); //Max Priority

        producer.start();
        consumer.start();
    }
}