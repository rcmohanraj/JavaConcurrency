package com.rcmcode.concurrency.signalling;

public class ThreadDemoSignaling {

    public static void main(String[] args) {
        threadSignaling();
    }

    //Simulating Thread Siganaling
    private static void threadSignaling() {
        DownloadStatus status = new DownloadStatus();
        Thread thread1 = new Thread(new DownloadFilesTask(status));
        Thread thread2 = new Thread(
                () -> {
                    while(!status.isDone()) {
                        System.out.println("Waiting for file download to be done:"+ status.isDone());
                        synchronized (status) {
                            try {
                                status.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    System.out.println("Total Bytes Downloaded:"+status.getTotalBytes());
                }
        );
        thread1.start();
        thread2.start();
    }
}
