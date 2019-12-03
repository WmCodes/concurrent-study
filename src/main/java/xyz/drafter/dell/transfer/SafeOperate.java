package xyz.drafter.dell.transfer;

/**
 * @author wangmeng
 * @date 2019/11/30
 * @desciption
 */
public class SafeOperate implements ITransfer {
    private static Object tieLock = new Object();
    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) throws InterruptedException {

        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);

        if (fromHash < toHash){

            synchronized (from){

                System.out.println(Thread.currentThread().getName()+" get"+from.getName());
                Thread.sleep(100);
                synchronized (to){
                    System.out.println(Thread.currentThread().getName()+" get"+to.getName());
                    from.flyMoney(amount);
                    to.addMoney(amount);
                }

            }

        }else if (fromHash > toHash){

            synchronized (to){

                System.out.println(Thread.currentThread().getName()+" get"+to.getName());
                Thread.sleep(100);
                synchronized (from){
                    System.out.println(Thread.currentThread().getName()+" get"+from.getName());
                    from.flyMoney(amount);
                    to.addMoney(amount);
                }

            }

        }else {
            synchronized (tieLock){
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


    }
}
