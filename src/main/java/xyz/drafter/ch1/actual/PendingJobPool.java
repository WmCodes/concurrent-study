package xyz.drafter.ch1.actual;

import xyz.drafter.ch1.actual.vo.JobInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author drafter
 * @date 2019/12/2
 * @desciption
 */
public class PendingJobPool {

    // 保守估计
    private static final int THREAD_COUNTS = Runtime.getRuntime().availableProcessors();

    private static BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<>(5000);
    // 框架的主题类，也是调用者主要使用的类

    // 线程池 固定大小，有界队列
    // 默认 饱和策略AbortPolicy ，抛出异常
    private static ExecutorService taskExecutor =
            new ThreadPoolExecutor(THREAD_COUNTS, THREAD_COUNTS, 60,
                    TimeUnit.SECONDS,taskQueue);

    // job 的存放容器
    private static ConcurrentHashMap<String, JobInfo<?>> jobinfoMap =
            new ConcurrentHashMap<>();

    private static CheckJobProcesser checkJobProcesser = CheckJobProcesser.getInstance();

    // 单例模式,限制线程数
    private PendingJobPool(){};

    private static class JobPoolHolder{
        private static PendingJobPool pool = new PendingJobPool();
    }

    public static PendingJobPool getInstance(){
        return JobPoolHolder.pool;
    }
    // 单例模式


    public static Map<String,JobInfo<?>> getMap(){
        return jobinfoMap;
    }

    // 注册Job
    public <R> void registerJob(String jobName,int jobLengTh,ITaskProcesser<?,?> taskProcesser,long expireTime){
        JobInfo<R> jobInfo = new JobInfo<>(jobName, jobLengTh, taskProcesser, expireTime);
        if (jobinfoMap.putIfAbsent(jobName, jobInfo) != null){
            throw new RuntimeException(jobName + " 已经注册！");
        }
    }

    private <R> JobInfo<R> getJob(String jobName){
        JobInfo<R> jobInfo = (JobInfo<R>) jobinfoMap.get(jobName);
        if (null == jobInfo){
            throw new RuntimeException(jobName + " 是个非法的任务");
        }
        return jobInfo;
    }

    // 调用者提交工作中的任务
    public <T,R> void putTask(String jobName,T t){
        JobInfo<R> jobInfo = getJob(jobName);
        PendingTask<T,R> task = new PendingTask<>(jobInfo, t);
        taskExecutor.execute(task);
    }

    // 获得该jobName里已处理任务的任务详情
    public <R> List<TaskResult<R>> getTaskDetail(String jobName){
        JobInfo<R> jobInfo = getJob(jobName);
        return jobInfo.getTaskDetail();
    }

    // 获得该任务的处理详情
    public <R> String getTaskProcess(String jobName){
        JobInfo<R> jobInfo = getJob(jobName);
        return jobInfo.getTotalProcess();
    }



    // 对工作中的任务进行包装，提交给线程池使用，并处理任务的结果，写入缓存以供查询
    private static class PendingTask<T,R> implements Runnable{

        private JobInfo<R> jobInfo;
        private T processData;

        public PendingTask(JobInfo<R> jobInfo, T processData) {
            this.jobInfo = jobInfo;
            this.processData = processData;
        }

        @Override
        public void run() {
            R r = null;
            ITaskProcesser<T,R> taskProcesser =
                    (ITaskProcesser<T,R>) jobInfo.getTaskProcesser();
            TaskResult<R> result = null;
            // 业务人员实现的具体方法
            try {
                result = taskProcesser.taskExecute(processData);
                // 结果数据检查

                if (result == null){
                    result  = new TaskResult<R>(TaskResultType.EXCEPTION,r,"result is null");
                }
                if (result.getResultType() == null){
                    if (result.getReason() == null){
                        result  = new TaskResult<R>(TaskResultType.EXCEPTION,r,"reason is null");
                    }else {
                        result  = new TaskResult<R>(TaskResultType.EXCEPTION,r,"result is null,but reason: "+result.getReason());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                result  = new TaskResult<R>(TaskResultType.EXCEPTION,r,e.getMessage());
            }finally {
                jobInfo.addTaskResult(result,checkJobProcesser);
            }

        }
    }



}
