package xyz.drafter.ch1.actual.test;

import xyz.drafter.ch1.actual.PendingJobPool;
import xyz.drafter.ch1.actual.TaskResult;
import xyz.drafter.util.SleepTools;

import java.util.List;
import java.util.Random;

/**
 * @author drafter
 * @date 2019/12/2
 * @desciption
 */
public class AppTest {

    private final static String JOB_NAME = "计算数值";
    private final static int JOB_LENGTH = 100;
    private static class QueryResult implements Runnable{

        private PendingJobPool pool;

        public QueryResult(PendingJobPool pool){
            this.pool = pool;
        }

        @Override
        public void run() {
            // 查询次数
            int i =0;
            while (i < 100){
                List<TaskResult<String>> taskDetail = pool.getTaskDetail(JOB_NAME);
                if(!taskDetail.isEmpty()){
                    System.out.println(pool.getTaskProcess(JOB_NAME));
                    System.out.println(taskDetail);
                }
                SleepTools.ms(100);
                i++;

            }

        }
    }


    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        PendingJobPool pool = PendingJobPool.getInstance();
        pool.registerJob(JOB_NAME, JOB_LENGTH, myTask, 1000*5);
        Random r = new Random();
        for (int i = 0;i<JOB_LENGTH;i++){
            pool.putTask(JOB_NAME,r.nextInt(1000));
        }
        Thread t = new Thread(new QueryResult(pool));
        t.start();

    }

}
