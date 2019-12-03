package xyz.drafter.dell.mypool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author wangmeng
 * @date 2019/11/30
 * @desciption
 */
public class MyThreadPool {

    // 线程池中默认线程个数为5
    private static int WORK_NUM = 5;

    // 队列默认任务个数为100
    private static int TASK_COUNT = 100;


    // 工作线程
    private WorkThread[] workThreads;


    // 任务队列，作为一个缓冲
    private final BlockingQueue<Runnable> taskQueue;
    // 用户在构造池的时候，期望线程数
    private final int worker_num;

    public MyThreadPool() {
        this(WORK_NUM,TASK_COUNT);

    }

    public MyThreadPool( int worker_num,int taskCount) {
        if (worker_num <= 0)
        {
            worker_num = WORK_NUM;
        }
        if (taskCount <= 0){
            taskCount =TASK_COUNT;
        }
        this.worker_num = worker_num;
        this.taskQueue = new ArrayBlockingQueue<Runnable>(taskCount);

        workThreads = new WorkThread[worker_num];
        for (int i = 0;i<worker_num;i++){
            workThreads[i] = new WorkThread();
            workThreads[i].start();
        }

    }

    // 执行任务，把任务放入任务队列，什么时候执行由线程池管理决定
    public void execute(Runnable task){
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 销毁线程池，该方法保证在所有任务都完成情况下才销毁
    public void destory(){
        System.out.println("ready close pool .....");
        for (int i = 0;i<worker_num;i++){
            workThreads[i].stopWorker();
            workThreads[i] = null;
        }

        // 清空任务队列
        taskQueue.clear();
    }

    @Override
    public String toString() {
        return "MyThreadPool{" +
                "taskQueue number=" + taskQueue.size() +
                ", worker_num=" + worker_num +
                '}';
    }

    private class WorkThread extends Thread{

        @Override
        public void run() {
            Runnable r = null;
            while (!isInterrupted()){
                try {
                    r = taskQueue.take();
                    if (r != null){
                        System.out.println(getId()+" ready exec: "+r);
                        r.run();
                    }
                    // help GC
                    r = null;


                } catch (InterruptedException e) {
                    e.printStackTrace();
                    interrupt();
                }

            }

        }

        public void stopWorker(){
            interrupt();
        }

    }
}
