package xyz.drafter.dell;

/**
 * @author wangmeng
 * @date 2019/11/26
 * @desciption
 */
public class EndThread {

    private static class UseThread extends  Thread{

        public UseThread(String name){
            super(name);
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            while (!isInterrupted()){
                System.out.println(threadName+ " is run!");
            }
            System.out.println(threadName+ " interrput flag is "+isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread endThread = new UseThread("endThread");
        endThread.start();
        Thread.sleep(10);
        endThread.interrupt();
    }
}
