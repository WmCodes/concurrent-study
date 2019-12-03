package xyz.drafter.ch1.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author wangmeng
 * @date 2019/11/27
 * @desciption
 */
public class SumArray {

    // RecursiveTask 有返回值
    //  RecursiveAction 无返回值
    private static class SumTask extends RecursiveTask<Integer>{

        private final static int THRESHOLD = MakeArray.ARRAY_LENGTH /10;
        private int[] src; //实际要统计的数据
        private int fromIndex; // 开始统计的下标
        private int toIndex; // 统计到哪里结束

        public SumTask(int[] src, int fromIndex, int toIndex) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        protected Integer compute() {
            if (toIndex - fromIndex < THRESHOLD){
                int count = 0;
                for (int i = fromIndex;i<=toIndex;i++){
                    //SleepTools.ms(1);
                    count = count +src[i];
                }
                return count;
            }else {
                int mid = (fromIndex + toIndex)/2;
                SumTask left = new SumTask(src, fromIndex, mid);
                SumTask right = new SumTask(src, mid+1, toIndex);
                invokeAll(left,right);
                return left.join()+right.join();


            }
        }
    }


    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int[] src = MakeArray.makeArray();
        SumTask innerFind = new SumTask(src, 0, src.length -1);
        long start = System.currentTimeMillis();

        pool.invoke(innerFind);
        System.out.println("Task is Running.....");
        System.out.println("The count is "+innerFind.join()+" spend time :"+(System.currentTimeMillis() -start)+" ms");

    }
}
