package xyz.drafter.dell.comps;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @author wangmeng
 * @date 2019/11/30
 * @desciption
 */
public class WorkTask implements Callable<Integer> {

    private String name;
    public WorkTask(String name){
        this.name = name;
    }

    @Override
    public Integer call() {
        int sleepTime = new Random().nextInt(1000);


        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sleepTime;
    }
}
