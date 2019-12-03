package xyz.drafter.dell.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author wangmeng
 * @date 2019/11/27
 * @desciption
 */
public class UseFuture {

    private static class UseCallable implements Callable<Integer>{

        private int sum;

        @Override
        public Integer call() throws Exception {
            System.out.println("Callable 子线程开始计算");
            Thread.sleep(2000);
            for (int i = 0;i<5000;i++){
                sum = sum+i;
            }
            System.out.println("Callable 子线程计算完成，结果= "+sum);
            return sum;
        }
    }

    public static void main(String[] args) {

        try {
        UseCallable useCallable = new UseCallable();
            FutureTask<Integer> futureTask = new FutureTask<>(useCallable);
            new Thread(futureTask).start();
            Random r = new Random();
            TimeUnit.SECONDS.sleep(1);
            if (r.nextBoolean()) // 随机决定是获得结果还是终止任务
            {
                System.out.println("Get UseCallable result = "+futureTask.get());
            }else {
                System.out.println("中断计算");
                futureTask.cancel(true);
            }





        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
