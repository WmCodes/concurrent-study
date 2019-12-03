package xyz.drafter.ch1.rw;

import xyz.drafter.util.SleepTools;

import java.util.Random;

/**
 * @author wangmeng
 * @date 2019/11/28
 * @desciption
 */
public class BusiApp {


    static final int readWriteRatio = 10;// 读写线程的比例
    static final int minThreadCount = 3;// 最少线程数


    private static class GetThread implements  Runnable{

        private GoodsService goodsService ;

        public GetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
        long start = System.currentTimeMillis();
        for (int i= 0;i<100;i++){
            goodsService.geNum();
        }

            System.out.println(Thread.currentThread().getName()+" 读取商品数据耗时: "+(System.currentTimeMillis() - start)+" ms");
        }
    }

    private static class SetThread implements Runnable{

        private GoodsService goodsService ;

        public SetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }


        @Override
        public void run() {
            long start = System.currentTimeMillis();
            Random r = new Random();
            for (int i= 0;i<10;i++){
                SleepTools.ms(50);
                goodsService.setNum(r.nextInt(10));
            }
            System.out.println(Thread.currentThread().getName()+" 写商品数据耗时: "+(System.currentTimeMillis() - start)+" ms");
        }
    }

    public static void main(String[] args) {
        GoodsInfo goodsInfo = new GoodsInfo("cup", 100000, 100000);
        //GoodsService goodsService = new UseSyn(goodsInfo);
        GoodsService goodsService = new UseRwLock(goodsInfo);
        for (int i = 0;i<minThreadCount;i++){
            Thread setT = new Thread(new SetThread(goodsService));
            for (int j = 0; j<readWriteRatio;j++){
                Thread getT = new Thread(new GetThread(goodsService));
                getT.start();
            }
            SleepTools.ms(100);
            setT.start();
        }

    }
}
