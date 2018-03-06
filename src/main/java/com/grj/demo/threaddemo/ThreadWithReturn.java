package com.grj.demo.threaddemo;


import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.*;

/**
 * 1.创建一个线程池
 * 2.创建一个有多个返回值的任务
 */
public class ThreadWithReturn {

    public static void main(String[] args)
            throws ExecutionException
            , InterruptedException {

        System.out.println("-- Program startup --");

        Date date1 = new Date();

        int taskSize = 5;

        //创建一个线程池
        ExecutorService pool =
                Executors.newFixedThreadPool(taskSize);

        //创建多个有返回值的任务
        ArrayList<Future> list = new ArrayList<>();

        for (int i = 0; i < taskSize; i++) {
            Callable c = new MyCallable(i + "");
            //执行任务并获取Future对象
            Future f = pool.submit(c);
            //Syste,.out.println(">>>"+f.get().toString())
            list.add(f);
        }

        //关闭线程池
        pool.shutdown();

        //获取所有并发任务的运行结果
        for (Future f : list) {
            //从Future对象上获取任务的返回值, 并输出到控制台
            System.out.println(">>>" + f.get().toString());
        }

        Date date2 = new Date();
        System.out.println("---程序停止运行---, 程序" +
                "运行时间["
                + (date2.getTime() - date1.getTime())
                + "]");
    }


}

class MyCallable implements Callable<Object> {
    private String taskNum;

    MyCallable(String taskNum) {
        this.taskNum = taskNum;
    }

    public Object call() throws InterruptedException {
        System.out.println(">>>" + taskNum + "任务启动");
        Date dateTmp1 = new Date();
        Thread.sleep(1000);
        Date dateTmp2 = new Date();
        long time  =
                dateTmp2.getTime()-dateTmp1.getTime();
        System.out.println(">>>"+taskNum+"任务终止");
        return taskNum+"任务返回运行结果" +
                ",当前任务时间["+time+"]";
    }

}

