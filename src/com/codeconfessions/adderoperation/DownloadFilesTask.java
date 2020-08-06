package com.codeconfessions.adderoperation;

public class DownloadFilesTask implements Runnable {

    private DownloadStatus status;

    public DownloadFilesTask(DownloadStatus status) {
        this.status = status;
    }

    @Override
    public void run() {
        System.out.println(Thread.activeCount());
        for(int i = 0; i < 10_000; i++) {
            status.incrementTotalBytes();
        }
        System.out.println("Download completed:"+Thread.currentThread().getName());
    }
}
