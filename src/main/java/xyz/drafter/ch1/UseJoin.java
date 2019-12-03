package xyz.drafter.ch1;

import xyz.drafter.util.SleepTools;

/**
 * @author wangmeng
 * @date 2019/11/27
 * @desciption
 */
public class UseJoin {

    static class JumpQueue implements Runnable{
        private Thread thread;

        public JumpQueue(Thread thread){
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName()+" terminted ****.");
        }
    }

    public static void main(String[] args) throws  Exception{
        // 主线程插入到 1线程之前，1线程插入到2线程之前.......
        // 必须主线程完成后 后面的线程才能一次执行

        Thread previous = Thread.currentThread(); // 主线程
        for (int i =0 ;i<10;i++){
            // i =0; previous 是主线程， i =1 ；previous是 i = 0 这个线程
            Thread thread = new Thread(new JumpQueue(previous),String.valueOf(i));
            System.out.println(previous.getName()+" jump queue the thread: "+thread.getName());
            thread.start();
            previous = thread;
        }
        SleepTools.second(2);
        System.out.println(Thread.currentThread().getName() +" terminate .");

    }
}
