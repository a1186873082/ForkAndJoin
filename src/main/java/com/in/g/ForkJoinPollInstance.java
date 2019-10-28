package com.in.g;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPollInstance {
    private static ForkJoinPool forkJoinPool = null;

    public ForkJoinPool getInstance() {
        if (forkJoinPool == null) {
            synchronized(this){
                forkJoinPool = new ForkJoinPool();
            }
        }
        return forkJoinPool;
    }
}
