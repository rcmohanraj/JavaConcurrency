package com.codeconfessions.atomicoperation;

import java.util.ArrayList;
import java.util.List;

public class ThreadDemoAtomicOperation {

    public static void main(String[] args) {
        atomicOperation();
    }

    //Simulating Atomic Operation using java.util.concurrent.atomic package
    private static void atomicOperation() {
        DownloadStatus status = new DownloadStatus();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new DownloadFilesTask(status));
            thread.start();
            threads.add(thread);
        }
        for(Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Total Bytes Downloaded:"+status.getTotalBytes());
    }
}
