package com.in.g;

import java.util.concurrent.RecursiveTask;

/**
 * 第一次使用分支/合并框架
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    //要求求和数组
    private long[] numbers;
    //子任务处理的数组的起始位置和终止位置
    private int start;
    private int end;

    //不再将任务分解为子任务的数组大小
    public static final long THRESHOLD = 10_000;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    public ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    //RecursiveTask抽象方法
    @Override
    protected Long compute() {
        //该任务负责求和的部分的大小
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        //创建一个子任务来为数组的前一半求和
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
        //利用另一个ForkJoinPool线程异步执行新创建的子任务
        leftTask.fork();
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
        //读取第一个子任务的结果，如果尚未完成就等待
        Long rightResult = rightTask.compute();
        //同步执行第二个子任务，有可能允许进一步递归划分
        rightTask.fork();
        //最终结果时两个子任务的组合
        Long leftResult = leftTask.compute();
        return leftResult + rightResult;
    }

    //在子任务不再可分时计算结果
    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }
}
