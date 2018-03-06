package com.grj.demo.threaddemo;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

public class ThreadWithReturnGetSum {

    public static int sum = 0;

    public static void main(String[] args)
            throws ExecutionException
            , InterruptedException {
        int taskSize = 5;
        //创建一个线程池
        ExecutorService pool =
                Executors.newFixedThreadPool(taskSize);

        ArrayList<Future> list =
                new ArrayList<Future>();
        for(int i=0;i<taskSize;i++){
            Callable c = new CallableRandom();
            Future f = pool.submit(c);
            list.add(f);
        }
        //关闭线程池
        pool.shutdown();
        //获取所有并发任务的运行结果
        int i = 0;
        /**
         * list大小为5
         * i = 0,1,2,3,4,5,   6
         */
        for(Future f : list){
            //从Future对象上获取任务的返回值
            // , 并输出到控制台

            System.out.println(
                    ">>>"+f.get().toString()
            );
            sum += Integer.valueOf(f.get().toString());
            if(i==list.size()-1){
                System.out.println(">>> sum: "+sum);
            }
            i++;
        }

    }

}

class CallableRandom implements Callable<Object>{

    @Override
    public Object call() throws Exception {
        return new Random().nextInt(10);
    }
}
