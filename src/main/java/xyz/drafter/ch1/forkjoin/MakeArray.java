package xyz.drafter.ch1.forkjoin;

import java.util.Random;

/**
 * @author wangmeng
 * @date 2019/11/27
 * @desciption
 */
public class MakeArray {

    public static final  int ARRAY_LENGTH = 4000000;

    public static int[] makeArray(){
        Random r = new Random();
        int[] result = new int[ARRAY_LENGTH];
        for (int i =0 ;i<ARRAY_LENGTH;i++){
             result[i] = r.nextInt(10);
        }
        return result;
    }
}
