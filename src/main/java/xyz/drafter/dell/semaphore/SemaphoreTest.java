package xyz.drafter.dell.semaphore;

import java.sql.Connection;
import java.util.Random;

/**
 * @author wangmeng
 * @date 2019/11/27
 * @desciption
 */
public class SemaphoreTest {

    private static DBPoolSemaphore dbPoolSemaphore = new DBPoolSemaphore();

    private static class BusiThread extends Thread{


        @Override
        public void run() {
            Random r = new Random();// 让每个线程持有连接的时间不一样
            long start = System.currentTimeMillis();
            try {
                Connection connection = dbPoolSemaphore.takeConnect();
                System.out.println("Thread_ "+Thread.currentThread().getId()+"_获取数据库连接共耗时[ "
                +(System.currentTimeMillis()-start)+" ]ms.");

                Thread.sleep(100+r.nextInt(100));
                System.out.println("归还连接*****************");
                dbPoolSemaphore.returnConnect(connection);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {

        for (int i = 0;i<50;i++){

            Thread thread = new BusiThread();
            thread.start();
        }


    }


}
