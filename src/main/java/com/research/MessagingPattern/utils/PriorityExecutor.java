
package com.research.MessagingPattern.utils;


import java.util.concurrent.*;


public class PriorityExecutor extends ThreadPoolExecutor {

    private int priority;

    public PriorityExecutor(int corePoolSize, int maximumPoolSize,
                            long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.priority = 0;
    }

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new PriorityExecutor(nThreads, nThreads, 0L,
                TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>());
    }


    public Future<?> submit(Runnable task, int priority) {
        return super.submit(new ComparableFutureTask(task, null, priority));
    }

    public void execute(Runnable command){

        priority = (priority + 1) % Integer.MAX_VALUE;

        execute(command, priority);
    }


    public void execute(Runnable command, int priority) {
        super.execute(new ComparableFutureTask(command, null, priority));
    }

    public Future<?> submit(Callable<?> callable, int priority){
        return super.submit(new ComparableFutureTask(callable, priority));
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return (RunnableFuture<T>) callable;
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return (RunnableFuture<T>) runnable;
    }
}

   class ComparableFutureTask<T> extends FutureTask<T> implements Comparable<ComparableFutureTask<T>> {

    volatile int priority = 0;

    public ComparableFutureTask(Runnable runnable, T result, int priority) {
        super(runnable, result);
        this.priority = priority;
    }

    public ComparableFutureTask(Callable<T> callable, int priority) {
        super(callable);
        this.priority = priority;
    }

    @Override
    public int compareTo(ComparableFutureTask<T> o) {
        return Integer.valueOf(priority).compareTo(o.priority);
    }
   }