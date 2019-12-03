package xyz.drafter.dell.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author wangmeng
 * @date 2019/11/28
 * @desciption
 */
public class TestMyLock {

    public void test(){
        final Lock lock = new TrinityLock();


        class Worker extends Thread{


            @Override
            public void run() {
                while (true){

                    lock.lock();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(Thread.currentThread().getName());
                        TimeUnit.SECONDS.sleep(1);



                    }catch (Exception e){

                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }


                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }

        }


        for (int i = 0;i<10;i++){
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }

        for (int i = 0;i<10;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println();
        }



    }


    public static void main(String[] args) {
        TestMyLock testMyLock = new TestMyLock();
        testMyLock.test();

    }
}
