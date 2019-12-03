package xyz.drafter.ch1.lock;

import xyz.drafter.util.SleepTools;

/**
 * @author wangmeng
 * @date 2019/11/28
 * @desciption
 */
public class Test {

    private static LockDemo lockDemo = new LockDemo();

    private static class Check extends Thread{
        @Override
        public void run() {
            lockDemo.increment();
        }
    }

    public static void main(String[] args) {
        for (int i = 0;i<10;i++){
            new Test.Check().start();
        }

        SleepTools.second(100);

    }
}
