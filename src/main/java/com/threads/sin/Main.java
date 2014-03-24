package com.threads.sin;

import java.util.concurrent.*;

/**
 * Created by Yuliia_Valchuk
 *
 * найоптимальніше рахувати суму при n = 8
 *
 * з ExecutorService программа працює швидше(де використовується Callable), ніж без
 *
 */
public class Main {

    public static void main(String args[])
    {
        int n = 3, N = 1000;

        long time = System.currentTimeMillis();
        System.out.println(runnableSinSum(N, n));
        System.out.println(System.currentTimeMillis() - time);

        time = System.currentTimeMillis();
        System.out.println(callableSinSum(N, n));
        System.out.println(System.currentTimeMillis() - time);
    }

    public static double runnableSinSum(int N, int n){
        double sum = 0;
        int k, intervalAmount;
        int sendedInterval = -N;
        int interval = (2 * N + 1);

        intervalAmount = interval / n;
        k =interval % n;
        SinSumRunnable[] runnables = new SinSumRunnable[n];
        Thread[] threads = new Thread[n];

        for(int i = 0; i < n; ++i){
            runnables[i] = new SinSumRunnable(sendedInterval, sendedInterval + (intervalAmount + (i<k?1:0)));
            threads[i] = new Thread(runnables[i]);
            threads[i].start();
            sendedInterval += (intervalAmount + (i<k?1:0));
        }

        for(int i = 0; i < n; ++i){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sum += runnables[i].getSum();
        }

        return sum;
    }

    public static double callableSinSum(int N, int n){
        double sum = 0;
        int k, intervalAmount;
        int sendedInterval = -N;
        int interval = (2 * N + 1);

        intervalAmount = interval / n;
        k =interval % n;
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Double>[] futures = new Future[n];
        Thread[] threads = new Thread[n];

        for(int i = 0; i < n; ++i){
            futures[i] = executorService.submit(new SinSunCallable(sendedInterval,
                    sendedInterval + (intervalAmount + (i<k?1:0))));
           // futures[i] = new FutureTask<Double>(callables[i]);
           // threads[i] = new Thread(futures[i]);
           // threads[i].start();
            sendedInterval += (intervalAmount + (i<k?1:0));
        }

        for(int i = 0; i < n; ++i){
            try {
               // threads[i].join();
                sum += futures[i].get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return sum;
    }
}
