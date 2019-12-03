package xyz.drafter.ch1.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangmeng
 * @date 2019/11/28
 * @desciption
 */
public class TestManyThread {

    static class Service {
        private Lock lock = new ReentrantLock();

        public void testMethod() {
            try {
                lock.lock();
                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread().getName() + " " + (i + 1));
                }
            } finally {
                lock.unlock();
            }
        }
    }

    static class MyThread extends Thread {
        private Service service;

        public MyThread(Service service) {
            this.service = service;
        }

        @Override
        public void run() {
            service.testMethod();
        }
    }

    public static void main(String[] args) {
        Service service = new Service();
        MyThread t1 = new MyThread(service);
        MyThread t2 = new MyThread(service);
        MyThread t3 = new MyThread(service);
        MyThread t4 = new MyThread(service);
        MyThread t5 = new MyThread(service);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
