package xyz.drafter.ch1;

import xyz.drafter.util.SleepTools;

/**
 * @author wangmeng
 * @date 2019/11/27
 * @desciption
 */
public class SynClzAndInst {

    //类锁线程
    private static class SynClass extends Thread{

        @Override
        public void run() {
            System.out.println("TestClass is running....");
            synClass();

        }
    }

    // 对象锁线程
    private static class InstanceSyn implements Runnable{
        private SynClzAndInst synClzAndInst;
        public InstanceSyn(SynClzAndInst synClzAndInst){
            this.synClzAndInst = synClzAndInst;
        }


        @Override
        public void run() {
            System.out.println("TestInstance is running...");
            synClzAndInst.instance();
        }
    }

    // 对象锁线程
    private static class Instance2Syn implements Runnable{
        private SynClzAndInst synClzAndInst;
        public Instance2Syn(SynClzAndInst synClzAndInst){
            this.synClzAndInst = synClzAndInst;
        }


        @Override
        public void run() {
            System.out.println("TestInstance2 is running...");
            synClzAndInst.instance2();
        }
    }

    public synchronized void instance()  {
        try {
            Thread.sleep(3000);
            System.out.println("synInstance is going....." + this.toString());
            Thread.sleep(3000);
            System.out.println("synInstance  ended....." + this.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized void instance2()  {
        try {
            Thread.sleep(3000);
            System.out.println("synInstance2 is going....." + this.toString());
            Thread.sleep(3000);
            System.out.println("synInstance2  ended....." + this.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static synchronized void synClass(){
        try {
            Thread.sleep(3000);
            System.out.println("synClass is going.....");
            Thread.sleep(3000);
            System.out.println("synClass  ended....." );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SynClzAndInst synClzAndInst = new SynClzAndInst();
        SynClzAndInst synClzAndInst2 = new SynClzAndInst();
        // synClzAndInst 的对象锁 锁的是每一个对象实例
        Thread t1 = new Thread(new InstanceSyn(synClzAndInst));
        // synClzAndInst2 的对象锁
        //Thread t2 = new Thread(new Instance2Syn(synClzAndInst2));
        // synClzAndInst 的对象锁
        //Thread t2 = new Thread(new Instance2Syn(synClzAndInst2));
        t1.start();
       // t2.start();

        SynClass synClass = new SynClass();
        // 类锁 锁的是每个类的Class对象 唯一
        synClass.start();
        SleepTools.second(1);

    }

}
