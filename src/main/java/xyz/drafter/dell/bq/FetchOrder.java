package xyz.drafter.dell.bq;

import java.util.concurrent.DelayQueue;

/**
 * @author wangmeng
 * @date 2019/11/29
 * @desciption
 */
public class FetchOrder implements Runnable {

    private DelayQueue<ItemVo<Order>> queue;

    public FetchOrder(DelayQueue<ItemVo<Order>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            try {
                ItemVo<Order>  item = queue.take();
                Order order = item.getData();
                System.out.println("queue订单内容: "+order);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


}
