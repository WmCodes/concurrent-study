package xyz.drafter.ch1.aqs;

import xyz.drafter.util.SleepTools;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author drafter
 * @date 2019/11/28
 * @desciption
 */
public class TestMyLock {

    public void test(){
        final Lock lock = new ReentrantLock();
        class Worker extends  Thread{

            public Worker(String name){
                super(name);
            }

            @Override
            public void run() {
                while (true){

                    lock.lock();
                    try {
                        SleepTools.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepTools.second(1);
                    }finally {
                        lock.unlock();
                    }

                    SleepTools.second(2);
                }
            }
        }
        // 启动10个子线程
        for (int i = 0;i< 10;i++){
            Worker w = new Worker("Thread_"+i);
            w.setDaemon(true);
            w.start();
        }
        // 主线程每隔1秒换行
        for (int i = 0;i<10 ;i++){
            SleepTools.second(1);
            System.out.println();
        }
    }


    public static void main(String[] args) {

        TestMyLock testMyLock = new TestMyLock();
        testMyLock.test();
        SleepTools.second(500);
    }
}
