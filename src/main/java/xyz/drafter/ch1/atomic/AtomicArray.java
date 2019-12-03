package xyz.drafter.ch1.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author wangmeng
 * @date 2019/11/28
 * @desciption
 */
public class AtomicArray {

    static int[] value = new int[]{1,2};
    static AtomicIntegerArray ai = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        ai.getAndSet(0, 3);
        System.out.println(ai.get(0));
        System.out.println(value[0]);

    }
}
