package xyz.drafter.dell.transfer;

import java.util.Random;

/**
 * @author wangmeng
 * @date 2019/11/30
 * @desciption
 */
public class SafeOperateTwo implements ITransfer {

    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) throws InterruptedException {
        Random r = new Random();
        while (true){
            if (from.getLock().tryLock()){
                try {
                    System.out.println(Thread.currentThread().getName()+" get"+from.getName());
                    Thread.sleep(100);
                    //不会阻塞
                    if (to.getLock().tryLock()){
                        try {
                            System.out.println(Thread.currentThread().getName()+" get"+to.getName());
                            from.flyMoney(amount);
                            to.addMoney(amount);
                            break;

                        }finally {
                            to.getLock().unlock();
                        }
                    }


                }finally {
                    from.getLock().unlock();

                }

            }
            // 防止活锁，错开多个线程达到锁 又释放锁的情况
            Thread.sleep(r.nextInt(10));

        }

    }
}
