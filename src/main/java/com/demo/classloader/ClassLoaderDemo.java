package com.demo.classloader;

import java.util.ArrayList;

public class ClassLoaderDemo {

    public static void main(String[] args) {
        System.out.println("Classloader of this class:" + ClassLoaderDemo.class.getClassLoader());

        //System.out.println("Classloader of Logging:"
        //        + Logging.class.getClassLoader());

        System.out.println("Classloader of ArrayList:" + ArrayList.class.getClassLoader());

        System.out.println(ClassLoader.getSystemResource("java/lang/Class.class"));
    }
}