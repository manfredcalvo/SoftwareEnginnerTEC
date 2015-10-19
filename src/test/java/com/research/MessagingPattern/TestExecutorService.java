package com.research.MessagingPattern;

import com.research.MessagingPattern.utils.PriorityExecutor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by mcalvo on 17/10/15.
 */
public class TestExecutorService {

    @Test
    public void test(){

        System.out.println("Number of cpus: " + Runtime.getRuntime().availableProcessors());


    }

    @Test
    public void testThreadsWorking()throws Exception{

        ExecutorService service = PriorityExecutor.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Future<?>> resultsFibo = new ArrayList<>();

        Random r = new Random(System.currentTimeMillis());

        PriorityExecutor priorityExecutor = (PriorityExecutor)service;

        for(int x = 0; x < 100; x++){
            int n = 1000000;
            resultsFibo.add(priorityExecutor.submit(new CalculateFibo(n, x), x));
        }


        for(Future<?> f: resultsFibo){

            System.out.println("Result = " + (Integer)f.get());
        }



    }

    private class CalculateFibo implements Callable<Integer>{

        private int n;
        private int id;
        public CalculateFibo(int n, int id){
            this.n = n;this.id = id;
        }

        @Override
        public Integer call() throws Exception {

            int fibo[] = new int[n + 1];
            fibo[0] = 1;
            fibo[1] = 1;

            for(int i = 2; i <= n; i++){

                fibo[i] = fibo[i -1] + fibo[i - 2];


            }

            return fibo[n];


        }
    }
}
