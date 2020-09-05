package com.rcmcode.concurrency.visibilityproblem;

public class ThreadDemoVisibilityProblem {

    public static void main(String[] args) {
        visibilityProblem();
    }

    //Simulating Visibility Problem
    private static void visibilityProblem() {
        DownloadStatus status = new DownloadStatus();
        Thread thread1 = new Thread(new DownloadFilesTask(status));
        Thread thread2 = new Thread(
                () -> {
                    while(!status.isDone()) {}
                    System.out.println("Total Bytes Downloaded:"+status.getTotalBytes());
                }
        );
        thread1.start();
        thread2.start();
        /*after simulated the visibility problem, we are adding volatile to keyword isDone, which will solve
        * our visibility problem.*/
    }
}
