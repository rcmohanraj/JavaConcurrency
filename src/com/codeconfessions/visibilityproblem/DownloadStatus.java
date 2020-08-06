package com.codeconfessions.visibilityproblem;

public class DownloadStatus {

    private int totalBytes;
    private volatile boolean isDone; //adding this volatile keyword to the isDone solved the visibility problem

    public int getTotalBytes() {
        return totalBytes;
    }

    //custom setter function
    public void incrementTotalBytes() {
        totalBytes++;
    }

    public boolean isDone() {
        return isDone;
    }

    //custom setter function
    public void done() {
        this.isDone = true;
    }
}
