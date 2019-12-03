package xyz.drafter.ch1;

import xyz.drafter.util.SleepTools;

/**
 * @author wangmeng
 * @date 2019/11/27
 * @desciption
 */
public class VolatileUnsafe {

    private static class VolatileVar implements Runnable{

        private volatile int a = 0;

        @Override
        public void run() {
            a = a+1;
            System.out.println(Thread.currentThread().getName()+" ====a = "+a);
            SleepTools.ms(100);
            a = a+1;
            System.out.println(Thread.currentThread().getName()+" ====a = "+a);

        }
    }

    public static void main(String[] args) {
        VolatileVar v = new VolatileVar();
        Thread t1 = new Thread(v);
        Thread t2 = new Thread(v);
        Thread t3 = new Thread(v);
        Thread t4 = new Thread(v);
        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }

}
