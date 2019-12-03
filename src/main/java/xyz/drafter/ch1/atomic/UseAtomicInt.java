package xyz.drafter.ch1.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangmeng
 * @date 2019/11/28
 * @desciption
 */
public class UseAtomicInt {

    static AtomicInteger ai = new AtomicInteger(10);

    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());
        System.out.println(ai.incrementAndGet());
        System.out.println(ai.get());
    }
}
