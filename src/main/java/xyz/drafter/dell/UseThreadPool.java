package xyz.drafter.dell;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author wangmeng
 * @date 2019/11/30
 * @desciption
 */
public class UseThreadPool {

    static class Worker implements Runnable
    {

        private String taskName;
        private Random r = new Random();

        public Worker(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" process the task : "+ taskName);
            try {
                Thread.sleep(r.nextInt(1000)*5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }




    static class CallWorker implements Callable<String>{

        private String taskName;
        private Random r = new Random();

        public CallWorker(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }


        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread().getName()+" process the task : "+ taskName);

            return  Thread.currentThread().getName()+ " : "+r.nextInt(100)*5;

        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
     /*   ExecutorService pool = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10),
                new ThreadPoolExecutor.DiscardOldestPolicy());*/
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0;i<6; i++){
            Worker worker = new Worker("Worker_"+i);
            pool.execute(worker);
        }

        for (int i = 0;i<6; i++){
            CallWorker callWorker = new CallWorker("CallWorker_"+i);
            Future<String> result = pool.submit(callWorker);
            System.out.println(result.get());
        }

        pool.shutdown();

    }
}
