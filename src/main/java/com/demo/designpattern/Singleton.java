package com.demo.designpattern;

import java.util.concurrent.atomic.AtomicInteger;

public class Singleton {

    private volatile static Singleton singleton;
    private int counter = 0;
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private Singleton() {
    }

    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    public int getUnsafeNext() {
        return ++counter;
    }

    public int getUnsafeCounter() {
        return counter;
    }

    public int getSafeNext() {
        return atomicInteger.incrementAndGet();
    }

    public int getSafeCounter() {
        return atomicInteger.get();
    }

}