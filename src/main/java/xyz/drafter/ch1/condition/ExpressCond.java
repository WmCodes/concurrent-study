package xyz.drafter.ch1.condition;

import xyz.drafter.util.SleepTools;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangmeng
 * @date 2019/11/28
 * @desciption
 */
public class ExpressCond {

    public final static String CITY ="ShangHai";
    private int km;// 快递运输里程数
    private String site; //快递到达地点
    private Lock lock = new ReentrantLock();
    private Condition kmCond = lock.newCondition();
    private Condition siteCond = lock.newCondition();
    int count = 0;

    public ExpressCond() {
    }

    public ExpressCond(int km, String site) {
        this.km = km;
        this.site = site;
    }

    // 变化公里数，然后通知处于wait状态并需要处理公里数的线程进行业务处理
 /*   public void changeKm(){
        lock.lock();

        try {
            this.km = 101;
            kmCond.signal(); //通知
        }finally {
            lock.unlock();
        }

    }*/

    // 变化地点，然后通知处于wait状态并需要处理地点的线程进行业务处理
/*    public void changeSite(){

        lock.lock();
        try {
            this.site = "BeiJing";
            siteCond.signal();// 通知
        }finally {
            lock.unlock();
        }
    }*/

    public void increment(){
        lock.lock();//获取锁
        System.out.println(Thread.currentThread().getName()+" 获得锁");
        SleepTools.second(2);
        try {
            count++;
        }finally {
            // 必须释放锁
            lock.unlock();
        }
        System.out.println(Thread.currentThread().getId()+" ："+count);

    }

    //当快递的里程数大于100时更新数据库
    public  void waitKm(){
        System.out.println(Thread.currentThread().getName()+" :尝试获取锁");
        lock.lock();
        SleepTools.second(5);
        System.out.println(Thread.currentThread().getName()+" :获取锁成功");
        try {
            while (this.km <= 100){
                try {
                   kmCond.await();
                   System.out.println(Thread.currentThread().getName()+"***********************");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }finally {
            lock.unlock();
        }

        System.out.println(Thread.currentThread().getName()+" the Km is "+ this.km +" , i will chang db");
    }

/*    // 当快递到达目的地时通知用户
    public void waitSite(){
        lock.lock();
        try {
          while (CITY.equals(this.site)){
              try {
             siteCond.await();
              System.out.println("---------------------");
              }catch (Exception e){
                  e.printStackTrace();
              }
          }
        }finally {
            lock.unlock();
        }

        System.out.println(" the site is "+this.site +" , i will call user ");

    }*/
}
