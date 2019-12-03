package xyz.drafter.dell.bq;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author wangmeng
 * @date 2019/11/29
 * @desciption
 *  存放队列的元素
 */
public class ItemVo<T>  implements Delayed {

    private long activeTime;//到期时间，单位毫秒
    private T data;


    //activeTime 是过期时长
    public ItemVo(long activeTime, T data) {
        // 将传入的时长转换为超时的时刻
        this.activeTime = TimeUnit.NANOSECONDS.convert(activeTime,
                TimeUnit.MILLISECONDS)+System.nanoTime();
        this.data = data;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public T getData() {
        return data;
    }

    //返回元素的剩余时间
    @Override
    public long getDelay(TimeUnit unit) {
        long d = unit.convert(this.activeTime- System.nanoTime(),TimeUnit.NANOSECONDS);

        return d;
    }
    //按照剩余时间排序
    @Override
    public int compareTo(Delayed o) {
        long d = getDelay(TimeUnit.NANOSECONDS)-o.getDelay(TimeUnit.NANOSECONDS);
        return (d ==0)?0:(d>0 ?1:-1);
    }
}
