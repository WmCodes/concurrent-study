package xyz.drafter.dell.transfer;

/**
 * @author wangmeng
 * @date 2019/11/30
 * @desciption
 */
public class PayCompany {

    private static class TransferThread extends Thread{
        private String name;
        private UserAccount form;
        private UserAccount to;
        private int amount;
        private ITransfer transfer;


        public TransferThread(String name,UserAccount form,UserAccount to,int amount,ITransfer transfer){
            this.name = name;
            this.form = form;
            this.to = to;
            this.amount = amount;
            this.transfer = transfer;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);

            try {
                transfer.transfer(form, to, amount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        public static void main(String[] args) {

            UserAccount zhangsan = new UserAccount("zhangsan", 20000);
            UserAccount lisi = new UserAccount("lisi", 20000);
            //ITransfer transfer = new TransferAccount();
           // ITransfer transfer = new SafeOperate();

            // 活锁
            ITransfer transfer = new SafeOperateTwo();

            TransferThread zhangsantoLisi = new TransferThread("zhangsanToLisi",
                    zhangsan, lisi, 2000,transfer );
            TransferThread lisiToZhangsan = new TransferThread("lisiToZhangsan",
                    lisi, zhangsan, 4000,transfer );
            zhangsantoLisi.start();
            lisiToZhangsan.start();

        }
    }
}
