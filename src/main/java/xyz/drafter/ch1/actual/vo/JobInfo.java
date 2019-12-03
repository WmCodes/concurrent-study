package xyz.drafter.ch1.actual.vo;

import xyz.drafter.ch1.actual.CheckJobProcesser;
import xyz.drafter.ch1.actual.ITaskProcesser;
import xyz.drafter.ch1.actual.TaskResult;
import xyz.drafter.ch1.actual.TaskResultType;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author drafter
 * @date 2019/12/2
 * @desciption
 */
public class JobInfo<R> {
    // 提交给框架执行的工作实体类

    // 区分一个唯一的工作
    private final String jobName;
    // 工作的任务个数
    private final int jobLength;
    // 这个工作任务的处理器
    private final ITaskProcesser<?,?> taskProcesser;
    private AtomicInteger successCount;
    private AtomicInteger taskProcesserCount;
    // 放结果队列  ，双端安全队列，  拿结果从头拿，放结果从尾巴上
    private LinkedBlockingDeque<TaskResult<R>> taskDetailQueue;
    private final long expireTime;


    public JobInfo(String jobName, int jobLength, ITaskProcesser<?, ?> taskProcesser, long expireTime) {
        this.jobName = jobName;
        this.jobLength = jobLength;
        this.taskProcesser = taskProcesser;
        this.successCount = new AtomicInteger(0);
        this.taskProcesserCount =  new AtomicInteger(0);
        this.taskDetailQueue = new LinkedBlockingDeque<>(jobLength);
        this.expireTime = expireTime;
    }

    public ITaskProcesser<?, ?> getTaskProcesser() {
        return taskProcesser;
    }

    public int getSuccessCount() {
        return successCount.get();
    }

    public int  getTaskProcesserCount() {
        return taskProcesserCount.get();
    }

    public int getFailCount(){
        return taskProcesserCount.get()-successCount.get();
    }

    //任务总进度
    public String getTotalProcess(){
        return "Success[ "+successCount.get()+" ] Current[ "+taskProcesserCount.get()+" ] Total[ "+jobLength+"]";
    }

    public List<TaskResult<R>> getTaskDetail(){
         List<TaskResult<R>> taskList = new LinkedList<>();
         TaskResult<R> taskResult;
         while ((taskResult = taskDetailQueue.pollFirst()) != null){
             taskList.add(taskResult);
         }
         return taskList;
    }

    // 保证最终一致性即可，不需要加锁
    public void addTaskResult(TaskResult<R> result, CheckJobProcesser checkJobProcesser){
        if (TaskResultType.SUCCESS.equals(result.getResultType())){
            successCount.incrementAndGet();
        }
        taskDetailQueue.addLast(result);
        // 处理总数+1
        taskProcesserCount.incrementAndGet();
        if (taskProcesserCount.get() == jobLength){
            // 任务全部完成
            checkJobProcesser.putJob(jobName, expireTime);
        }
    }
}
