package xyz.drafter.ch1.actual;

import xyz.drafter.dell.bq.ItemVo;

import java.util.concurrent.DelayQueue;

/**
 * @author drafter
 * @date 2019/12/2
 * @desciption
 */
public class CheckJobProcesser {
    // 任务完成后，在一定时间内供查询，之后为释放资源节约内存，需要定期处理过期的任务

    // 存放已完成任务等待过期的队列
    private static DelayQueue<ItemVo<String>> queue =
            new DelayQueue<>();

    // 单例模式
    private CheckJobProcesser(){}
    private static class ProcesserHolder{
        public static CheckJobProcesser processer = new CheckJobProcesser();
    }
    public static CheckJobProcesser getInstance(){
        return ProcesserHolder.processer;
    }

    // 单例模式
    // 处理到期任务的实现
    private static class FetchJob implements Runnable{

        @Override
        public void run() {
            while (true){
                try {
                    ItemVo<String> itemVo = queue.take();
                    // 拿到已经过期的任务名字
                    String jobName = itemVo.getData();
                    PendingJobPool.getMap().remove(jobName);
                    System.out.println(jobName+ " is out of data,remove form map!!!");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }


            }
        }
    }

    //
    public void putJob(String jobName,long expireTime){
        ItemVo<String> itemVo = new ItemVo<>(expireTime, jobName);
        queue.offer(itemVo);
        System.out.println("Job[ "+jobName+" 已经放入过期检查缓存，过期时长: "+expireTime);

    }


    static {
        Thread thread = new Thread(new FetchJob());
        thread.setDaemon(true);
        thread.start();
        System.out.println("开启任务过期检查守护线程.......");

    }
}
