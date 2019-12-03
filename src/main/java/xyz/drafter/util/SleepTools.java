package xyz.drafter.util;

import java.util.concurrent.TimeUnit;

/**
 * @author wangmeng
 * @date 2019/11/27
 * @desciption
 */
public class SleepTools {

    public static final void second(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static final void ms(int ms){
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
