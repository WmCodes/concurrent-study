package xyz.drafter.ch1.rw;

/**
 * @author wangmeng
 * @date 2019/11/28
 * @desciption
 */
public class GoodsInfo {

    private final String name;
    private double totalMoney;//总销售额
    private int storeNumber;//库存数

    public GoodsInfo(String name, double totalMoney, int storeNumber) {
        this.name = name;
        this.totalMoney = totalMoney;
        this.storeNumber = storeNumber;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public int getStoreNumber() {
        return storeNumber;
    }

    public void changeNumber(int sellNumber){
        this.totalMoney += sellNumber * 10;
        this.storeNumber -= sellNumber;
    }
}
