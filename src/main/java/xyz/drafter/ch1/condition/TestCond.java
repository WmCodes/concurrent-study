package xyz.drafter.ch1.condition;

import xyz.drafter.util.SleepTools;

/**
 * @author wangmeng
 * @date 2019/11/28
 * @desciption
 */
public class TestCond {

    private static ExpressCond expressCond = new ExpressCond(0,ExpressCond.CITY);



    private static class CheckKm extends Thread{
        @Override
        public void run() {
            expressCond.waitKm();
        }
    }


 /*   private static class CheckSite extends Thread{
        @Override
        public void run() {
            expressCond.waitSite();
        }
    }*/

    public static void main(String[] args) {
      /*  for (int i = 0;i<3;i++){
            new CheckSite().start();
        }*/
        for (int i = 0;i<3;i++){
            new CheckKm().start();
        }
        SleepTools.second(50);
       // expressCond.changeKm();

    }
}
