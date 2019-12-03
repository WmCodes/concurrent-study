package xyz.drafter.dell.transfer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangmeng
 * @date 2019/11/30
 * @desciption 用户账户实体类
 */
public class UserAccount {

    //private int id;
    private final String name; //账户名称
    private int money; // 账户余额

    private final Lock lock = new ReentrantLock();

    public UserAccount(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public Lock getLock() {
        return lock;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "name='" + name + '\'' +
                ", money=" + money +
                '}';
    }

    //转入资金
    public void addMoney(int amount){
        money = money +amount;
    }

    //转出资金
    public void flyMoney(int amount){
        money = money -amount;
    }
}
