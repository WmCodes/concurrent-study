package xyz.drafter.dell.bq;

import java.util.concurrent.DelayQueue;

/**
 * @author wangmeng
 * @date 2019/11/29
 * @desciption
 */
public class PutOrder implements Runnable {

    private DelayQueue<ItemVo<Order>> queue;


    public PutOrder(DelayQueue<ItemVo<Order>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        // 5秒到期
        Order orderTB = new Order("TB123456", 999);
        ItemVo<Order> itemTB = new ItemVo<>(5000, orderTB);
        queue.offer(itemTB);
        System.out.println("订单5秒后到期:"+orderTB);


        Order orderJD = new Order("JD654321", 888);
        ItemVo<Order> itemJD = new ItemVo<>(8000, orderJD);
        queue.offer(itemJD);
        System.out.println("订单8秒后到期:"+orderJD);
    }
}
