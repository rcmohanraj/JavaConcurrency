package com.codeconfessions.signalling;

public class DownloadFilesTask implements Runnable {

    private DownloadStatus status;

    public DownloadFilesTask(DownloadStatus status) {
        this.status = status;
    }

    @Override
    public void run() {
        System.out.println(Thread.activeCount());
        for(int i = 0; i < 1_000_000; i++) {
            status.incrementTotalBytes();
        }
        status.done();
        synchronized (status) {
            status.notify();
        }
        System.out.println("Download completed:"+Thread.currentThread().getName());
    }
}
