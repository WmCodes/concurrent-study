package xyz.drafter.dell.semaphore;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * @author wangmeng
 * @date 2019/11/27
 * @desciption
 */
public class DBPoolSemaphore {

    private final static int POOL_SIZE = 10;

    private final Semaphore useful,useless;//useful表示可用的数据库连接，useless 表示已用的数据连接


    public DBPoolSemaphore(){
        this.useful = new Semaphore(POOL_SIZE);
        this.useless = new Semaphore(0);
    }
    private static LinkedList<Connection> pool = new LinkedList<>();

    static {

        for (int i= 0;i<POOL_SIZE;i++){
            pool.addLast(SqlConnectImpl.fetchConnection());
        }

    }

    public void returnConnect(Connection connection)throws Exception{
        if (connection!= null){
            System.out.println("当前有 "+useful.getQueueLength()+" 个线程等待数据库连接!!!"
                +" 可用连接数: "+useful.availablePermits());
            useless.acquire();
            synchronized (pool){
                pool.addLast(connection);
            }
            useful.release();
        }

    }

    public Connection takeConnect() throws Exception{
        useful.acquire();//拿不到的话会阻塞这里
        Connection connection;
        synchronized (pool){
            connection = pool.removeFirst();
        }
        //已用连接加1
        useless.release();
        return connection;
    }
}
