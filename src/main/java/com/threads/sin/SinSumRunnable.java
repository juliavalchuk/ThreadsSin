package com.threads.sin;

/**
 * Created by Yuliia_Valchuk
 */
public class SinSumRunnable implements Runnable{
    private double sum = 0;
    private int start, finish;


    public SinSumRunnable(int start, int finish){
        this.start = start;
        this.finish = finish;
    }

    @Override
    public void run() {
          sum = sumSin(start, finish);
    }

    public double getSum(){
        return sum;
    }

    private double sumSin(int from, int to){
        double s = 0;
        for(int i = from; i < to; ++i){
             s += Math.sin((double)i);
        }
        return s;
    }
}
