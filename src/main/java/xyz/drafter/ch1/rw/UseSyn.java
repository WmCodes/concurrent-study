package xyz.drafter.ch1.rw;

import xyz.drafter.util.SleepTools;

/**
 * @author wangmeng
 * @date 2019/11/28
 * @desciption
 */
public class UseSyn implements GoodsService {

    private GoodsInfo goodsInfo;


    public UseSyn(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public synchronized GoodsInfo geNum() {
        SleepTools.ms(5);
        return this.goodsInfo;
    }

    @Override
    public  synchronized void setNum(int number) {
        SleepTools.ms(5);
        goodsInfo.changeNumber(number);

    }
}
