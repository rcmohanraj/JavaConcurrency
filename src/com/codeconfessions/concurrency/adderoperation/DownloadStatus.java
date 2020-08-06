package com.codeconfessions.concurrency.adderoperation;

import java.util.concurrent.atomic.LongAdder;

public class DownloadStatus {

    private LongAdder totalBytes = new LongAdder();

    public int getTotalBytes() {
        return totalBytes.intValue();
    }

    public void incrementTotalBytes() {
        totalBytes.increment();
    }
}
