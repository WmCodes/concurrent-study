package xyz.drafter.dell.schd;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangmeng
 * @date 2019/11/30
 * @desciption
 */
public class ScheduleCase {

    public static void main(String[] args) {

        ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(1);
        // 1秒后执行 执行周期3s
        // 抛出异常，会发生阻塞
        schedule.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.HasException), 1000, 3000, TimeUnit.MILLISECONDS);

        schedule.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.Normal), 1000, 3000, TimeUnit.MILLISECONDS);
    }
}
