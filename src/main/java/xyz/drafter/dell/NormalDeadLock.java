package xyz.drafter.dell;

/**
 * @author wangmeng
 * @date 2019/11/30
 * @desciption
 */
public class NormalDeadLock {

    private static Object valueFist = new Object();// 第一个锁
    private static Object valueSecond = new Object(); // 第二个锁


    // 先拿第一个锁，在拿第二个锁
    private static void firstToSecond(){
        String threadName = Thread.currentThread().getName();
        synchronized (valueFist){
            System.out.println(threadName+" get first");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (valueSecond){
                System.out.println(threadName + " get second");
            }
        }

    }

    private static void secondToFirst(){
        String threadName = Thread.currentThread().getName();
        synchronized (valueSecond){
            System.out.println(threadName+" get second");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (valueFist){
                System.out.println(threadName + " get first");
            }
        }

    }


    private static class TestThread extends Thread{

        private String name;
        public TestThread(String name){
            this.name = name;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);

            secondToFirst();

        }
    }

    public static void main(String[] args) {

        Thread.currentThread().setName("TestDealLock");
        TestThread testThread = new TestThread("SubTestThread");

        testThread.start();
        firstToSecond();
    }

}
