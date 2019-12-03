package xyz.drafter.ch1.actual.test;

import xyz.drafter.ch1.actual.ITaskProcesser;
import xyz.drafter.ch1.actual.TaskResult;
import xyz.drafter.ch1.actual.TaskResultType;
import xyz.drafter.util.SleepTools;

import java.util.Random;

/**
 * @author drafter
 * @date 2019/12/2
 * @desciption
 */
public class MyTask implements ITaskProcesser<Integer,Integer> {



    // 一个实际任务类，将数值加上一个随机数，并休眠随机时间



    @Override
    public TaskResult<Integer> taskExecute(Integer data) {
        Random r = new Random();
        int flag = r.nextInt(500);
        SleepTools.ms(flag);
        if (flag <= 300){
            // 正常处理的情况
            Integer rerurnValue = data.intValue() + flag;
            return new TaskResult<>(TaskResultType.SUCCESS,rerurnValue);

        }else if (flag> 300&& flag <= 400){
            return new TaskResult<>(TaskResultType.FAILURE,-1,"Failure");
        }else {
            try {
                throw new RuntimeException("发生异常!!!");
            }catch (Exception e){
                return new TaskResult<Integer>(TaskResultType.EXCEPTION,-1,e.getMessage() );
            }

        }

    }

}
