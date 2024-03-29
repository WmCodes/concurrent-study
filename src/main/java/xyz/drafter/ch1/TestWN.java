package xyz.drafter.ch1;

/**
 * @author wangmeng
 * @date 2019/11/27
 * @desciption 测试wait notify  notifyAll
 */
public class TestWN {
    private static Express express = new Express(0,Express.CITY);


    private static class CheckKm extends  Thread{
        @Override
        public void run() {
            express.waitKm();
        }
    }

    private static class CheckSite extends Thread{
        @Override
        public void run() {
            express.waitSite();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0;i<3;i++){
            new CheckSite().start();
        }
        for (int i = 0;i<3;i++){
            new CheckKm().start();
        }

        Thread.sleep(1000);
        express.changeKm();

    }

}
