package xyz.drafter.dell.schd;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangmeng
 * @date 2019/11/30
 * @desciption
 */
public class ScheduleWorker implements Runnable {

    public final static int Normal = 0;// 普通任务类型

    public final static int HasException = -1;// 会抛出异常的任务类型

    public final static int ProcessException = 1;// 抛出异常但会捕获的任务类型

    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int taskType;

    public ScheduleWorker(int taskType) {
        this.taskType = taskType;
    }

    @Override
    public void run() {

        if (taskType == HasException){
            System.out.println(formatter.format(new Date())+ " Exeception made....");
            throw new RuntimeException(" HasException Happen");
        }else if (taskType == ProcessException){
            try {
                System.out.println(formatter.format(new Date()) + " Exeception made.... but catch");
                throw new RuntimeException(" ProcessException Happen");
            }catch (Exception e){
                System.out.println("Exception be catched");
            }
        }else {
            System.out.println(formatter.format(new Date()) + " Normal made");

        }
    }
}
