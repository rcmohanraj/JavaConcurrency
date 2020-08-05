package com.codeconfessions.confinement;

public class DownloadFilesTask implements Runnable {

    private DownloadStatus status;

    public DownloadFilesTask() {
        this.status = new DownloadStatus();
    }

    @Override
    public void run() {
        System.out.println(Thread.activeCount());
        for(int i = 0; i < 10_000; i++) {
            status.incrementTotalBytes();
        }
        System.out.println("Download completed:"+Thread.currentThread().getName());
    }

    public DownloadStatus getStatus() {
        return status;
    }
}
