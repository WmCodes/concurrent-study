package xyz.drafter.dell.transfer;

/**
 * @author wangmeng
 * @date 2019/11/30
 * @desciption
 */
public interface ITransfer {

    void transfer(UserAccount from, UserAccount to, int amount)throws InterruptedException;
}
