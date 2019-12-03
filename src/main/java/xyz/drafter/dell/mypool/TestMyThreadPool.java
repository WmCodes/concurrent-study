package xyz.drafter.dell.mypool;

import java.util.Random;

/**
 * @author wangmeng
 * @date 2019/11/30
 * @desciption
 */
public class TestMyThreadPool {

    public static void main(String[] args) throws InterruptedException {
        MyThreadPool t = new MyThreadPool(3,0);

        t.execute(new MyTask("testA"));
        t.execute(new MyTask("testB"));
        t.execute(new MyTask("testC"));
        t.execute(new MyTask("testD"));
        t.execute(new MyTask("testE"));
        System.out.println(t);
        Thread.sleep(100000);
        t.destory();
        System.out.println(t);



    }

    static class MyTask implements Runnable{

        private String name;
        private Random r = new Random();

        public MyTask(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(r.nextInt(1000)+2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务 "+name+" 完成");
        }
    }
}
