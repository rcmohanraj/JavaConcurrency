package com.codeconfessions.concurrency.atomicoperation;

import java.util.concurrent.atomic.AtomicInteger;

public class DownloadStatus {

    private AtomicInteger totalBytes = new AtomicInteger();

    public int getTotalBytes() {
        return totalBytes.get();
    }

    public void incrementTotalBytes() {
        totalBytes.incrementAndGet();
    }
}
