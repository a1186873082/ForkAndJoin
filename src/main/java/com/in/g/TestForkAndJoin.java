package com.in.g;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class TestForkAndJoin {

    public static long forkJoinSum(long n) {
        long[] number = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(number);
        return new ForkJoinPollInstance().getInstance().invoke(task);
    }

    public static void main(String[] args) {
        System.out.println(forkJoinSum(100));
    }

}
