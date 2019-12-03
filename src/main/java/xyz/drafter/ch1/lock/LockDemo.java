package xyz.drafter.ch1.lock;

import xyz.drafter.util.SleepTools;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangmeng
 * @date 2019/11/28
 * @desciption
 */
public class LockDemo {

    private Lock lock = new ReentrantLock();

    private int count = 0;

    private static class CheckKm extends Thread{
        LockDemo lockDemo = new LockDemo();
        @Override
        public void run() {
            lockDemo.increment();
        }
    }

    public void increment(){
        lock.lock();//获取锁
        System.out.println(Thread.currentThread().getName()+" 获得锁");
        SleepTools.second(2);
        try {
            count++;
        }finally {
            // 必须释放锁
            lock.unlock();
        }
        System.out.println(Thread.currentThread().getId()+" ："+count);

    }

    public synchronized void incr2(){
        count++;
    }
}
