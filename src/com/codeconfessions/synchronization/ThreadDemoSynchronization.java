package com.codeconfessions.synchronization;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ThreadDemoSynchronization {

    public static void main(String[] args) {
        synchronizationDemo();
    }

    private static void synchronizationDemo() {
        LocalDateTime start = LocalDateTime.now();
        System.out.println(start);
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
        System.out.println(Duration.between(start, LocalDateTime.now()).toMillis());
        System.out.println("Total Bytes Downloaded:"+status.getTotalBytes());
        System.out.println("Total Files Downloaded:"+status.getTotalFiles());
    }
}
