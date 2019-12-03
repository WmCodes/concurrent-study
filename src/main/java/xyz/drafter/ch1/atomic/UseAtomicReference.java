package xyz.drafter.ch1.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author wangmeng
 * @date 2019/11/28
 * @desciption
 */
public class UseAtomicReference {

    static AtomicReference<UserInfo> userRef =  new AtomicReference<UserInfo>();
    public static void main(String[] args) {
        UserInfo userInfo  = new UserInfo("drafter", 25); //要修改的实体的实例
        userRef.set(userInfo);
        UserInfo updateUserInfo  = new UserInfo("Bill", 15);
        userRef.compareAndSet(userInfo, updateUserInfo);

        System.out.println(userRef.get().getName());
        System.out.println(userRef.get().getAge());
        System.out.println(userInfo.getName());
        System.out.println(userInfo.getAge());

    }
    static class UserInfo{
        private String name;
        private int age;

        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
