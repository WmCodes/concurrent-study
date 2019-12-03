package xyz.drafter.ch1.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author wangmeng
 * @date 2019/11/28
 * @desciption
 */
public class UseAtomicStampReference {
    static AtomicStampedReference<String> str = new AtomicStampedReference<>("drafter",0);

    public static void main(String[] args) throws InterruptedException {
        final int oldStamp = str.getStamp(); //获取初始版本号
        final String oldReference = str.getReference(); //获取初始数据

        System.out.println(oldReference+"---------------"+oldStamp);
        Thread rightStampThread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+" 当前变量值: "+ oldReference+ " 当前版本戳: "+oldStamp+" - "
                + str.compareAndSet(oldReference, oldReference+ " Java", oldStamp, oldStamp+1));
        });

        Thread errorStampThread = new Thread(() -> {
            String reference = str.getReference();
            System.out.println(Thread.currentThread().getName()+" 当前变量值: "+ reference+ " 当前版本戳: "+str.getStamp()+" - "
                    + str.compareAndSet(reference, reference+ " C", oldStamp, oldStamp+1));
        });

        rightStampThread.start();
        rightStampThread.join();
        errorStampThread.start();
        errorStampThread.join();
        System.out.println(str.getReference()+"**********"+str.getStamp());

    }
}
