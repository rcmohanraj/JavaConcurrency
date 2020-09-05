package com.rcmcode.concurrency.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ThreadDemoSynchronizedCollection {

    public static void main(String[] args) {
        regularCollection();
        synchroniziedCollection();
    }

    private static void regularCollection() {
        Collection<Integer> collection = new ArrayList<>();
        createAndRunThread(collection);
    }

    private static void synchroniziedCollection() {
        Collection<Integer> collection = Collections.synchronizedCollection(new ArrayList<>());
        createAndRunThread(collection);
    }

    private static void createAndRunThread(Collection<Integer> collection) {
        Thread thread1 = new Thread(() -> collection.addAll(Arrays.asList(1, 2, 3)));

        Thread thread2 = new Thread(() -> collection.addAll(Arrays.asList(4, 5, 6)));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(collection);
    }
}
