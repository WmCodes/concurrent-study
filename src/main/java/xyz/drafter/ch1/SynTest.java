package xyz.drafter.ch1;

/**
 * @author wangmeng
 * @date 2019/11/27
 * @desciption
 */
public class SynTest {

    private volatile int age = 100000;

    public int getAge() {
        return age;
    }

    private static class TestThread extends Thread{

        private SynTest synTest;
        public TestThread(SynTest synTest,String name){
            super(name);
            this.synTest = synTest;
        }

        @Override
        public void run() {
            for (int i =0;i<10000;i++){
                synTest.test();
            }
            System.out.println(Thread.currentThread().getName()+" age = "+synTest.getAge());
        }
    }

    public void test(){
        age ++;
    }
}
