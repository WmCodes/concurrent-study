package xyz.drafter.dell;

import java.util.concurrent.Callable;

/**
 * @author wangmeng
 * @date 2019/11/26
 * @desciption
 */
public class NewThread {

    public static class UseThread extends Thread{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

    public static class UseRun implements Runnable{

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }


    public static class UseCall implements Callable<String>{

        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread().getName());
            return "CallResult";
        }
    }
}
