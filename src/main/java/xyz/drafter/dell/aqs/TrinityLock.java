package xyz.drafter.dell.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author wangmeng
 * @date 2019/11/28
 * @desciption
 *
 * 共享锁
 */
public class TrinityLock implements Lock {


    private final Sync sync = new Sync(3);


     private static final class Sync extends AbstractQueuedSynchronizer{

         Sync(int count){
             if (count<0){
                 throw new IllegalArgumentException("count must large than zero.");
             }
             setState(count);
         }


         @Override
         protected int tryAcquireShared(int arg) {
             for (;;){
                 int current = getState();
                 int newCount = current - arg;
                 if (newCount < 0|| compareAndSetState(current, newCount)){
                     return newCount;//AQS里会获得这个结果，如果小于0，则将线程放入同步队列阻塞
                 }
             }
         }

         @Override
         protected boolean tryReleaseShared(int arg) {
             for (;;){
                 int current = getState();
                 int newCount = current + arg;
                 if (compareAndSetState(current, newCount)){
                     return true;
                 }
             }
         }



         final ConditionObject newCondition(){
             return new ConditionObject();
         }

     }



    @Override
    public void lock() {
         sync.acquireShared(1);

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
         sync.acquireSharedInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1)>=0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
            sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
