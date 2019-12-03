package xyz.drafter.dell;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * @author wangmeng
 * @date 2019/11/27
 * @desciption
 */
public class UseExchange {
    private static final Exchanger<Set<String>> exchange =
            new Exchanger<>();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setA = new HashSet<>();//存放数据的容器
                try {

                    /**
                     * 添加数据
                     * set.add(....)
                     *
                     */
                    setA = exchange.exchange(setA);// 交换set

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setB = new HashSet<>();//存放数据的容器
                try {

                    /**
                     * 添加数据
                     * set.add(....)
                     *
                     */
                    setB = exchange.exchange(setB);// 交换set

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
