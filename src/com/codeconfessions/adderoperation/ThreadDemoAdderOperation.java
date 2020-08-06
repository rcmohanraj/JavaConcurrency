package com.codeconfessions.adderoperation;

import java.util.ArrayList;
import java.util.List;

public class ThreadDemoAdderOperation {

    public static void main(String[] args) {
        adderOperation();
    }

    //Simulating Adder Operation using java.util.concurrent.atomic package
    private static void adderOperation() {
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
