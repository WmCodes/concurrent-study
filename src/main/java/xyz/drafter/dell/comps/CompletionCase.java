package xyz.drafter.dell.comps;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangmeng
 * @date 2019/11/30
 * @desciption
 */
public class CompletionCase {

    private final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final int TOTAL_TASK = Runtime.getRuntime().availableProcessors()*10;


    // 方法一 自己写集合来实现获取线程池中任务的返回结果
    public void testByQueue() throws Exception{
        long start = System.currentTimeMillis();
        // 统计所有任务休眠的时长
        AtomicInteger count = new AtomicInteger(0);
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        BlockingQueue<Future<Integer>> queue =
                new LinkedBlockingQueue<>();

        //向里扔任务
        for (int i = 0;i<TOTAL_TASK;i++){
            Future<Integer> future = pool.submit(new WorkTask("ExecTask"+i));
            queue.add(future);
        }


        // 检查线程池任务执行结果
        // 按照放入queue的顺序取出
        for (int i = 0;i<TOTAL_TASK;i++){
            int sleeptTime = queue.take().get();
            System.out.println("sleep ；"+sleeptTime+" ms...");
            count.addAndGet(sleeptTime);
        }


        pool.shutdown();
        System.out.println("----------------tasks sleep time " + count.get()+"ms,and spend time "
                +(System.currentTimeMillis()-start)+" ms");



    }


    // 方法二  通过CompletionService 来实现获取线程池中任务的返回结果
    public void testByCompletion() throws Exception{
        long start = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

        CompletionService<Integer> completionService = new ExecutorCompletionService(pool);

        for (int i = 0;i<TOTAL_TASK;i++){
            completionService.submit(new WorkTask("ExecTask"+i));
        }

        // 按照先结束的线程取出
        for (int i = 0;i<TOTAL_TASK;i++){
            int sleeptTime = completionService.take().get();
            System.out.println("sleep ；"+sleeptTime+" ms...");
            count.addAndGet(sleeptTime);
        }

        pool.shutdown();
        System.out.println("----------------tasks sleep time " + count.get()+"ms,and spend time "
                +(System.currentTimeMillis()-start)+" ms");


    }


    public static void main(String[] args) throws Exception {
        CompletionCase t = new CompletionCase();
        t.testByQueue();
        t.testByCompletion();

    }
}
