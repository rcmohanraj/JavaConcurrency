package com.codeconfessions.confinement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThreadDemoConfinement {

    public static void main(String[] args) {
        confinementStrategy();
    }

    private static void confinementStrategy() {
        List<Thread> threads = new ArrayList<>();
        List<DownloadFilesTask> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DownloadFilesTask task = new DownloadFilesTask();
            tasks.add(task);
            Thread thread = new Thread(task);
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

        Optional<Integer> optionalTotalBytes = tasks.stream()
                .map(t -> t.getStatus().getTotalBytes())
                .reduce(Integer::sum); //will accept Binary Operator Functional Interface
        System.out.println("Total Bytes Downloaded:"+optionalTotalBytes.get());
    }
}
