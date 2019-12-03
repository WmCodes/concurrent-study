package xyz.drafter.dell.transfer;

/**
 * @author wangmeng
 * @date 2019/11/30
 * @desciption
 */
public class TransferAccount implements ITransfer {
    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) throws InterruptedException {
        synchronized (from){

            System.out.println(Thread.currentThread().getName()+" get"+from.getName());
            Thread.sleep(100);
            synchronized (to){
                System.out.println(Thread.currentThread().getName()+" get"+to.getName());
                from.flyMoney(amount);
                to.addMoney(amount);
            }

        }
    }
}
