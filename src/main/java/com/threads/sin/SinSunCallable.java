package com.threads.sin;

import java.util.concurrent.Callable;

/**
 * Created by julia
 */
public class SinSunCallable implements Callable<Double> {
    private int start, finish;

    public SinSunCallable(int start, int finish){
        this.start = start;
        this.finish = finish;
    }

    @Override
    public Double call() throws Exception {
        return sumSin(start, finish);
    }

    private double sumSin(int from, int to){
        double s = 0;
        for(int i = from; i < to; ++i){
            s += Math.sin((double)i);
        }
        return s;
    }
}
