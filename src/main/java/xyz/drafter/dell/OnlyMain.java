package xyz.drafter.dell;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author wangmeng
 * @date 2019/11/26
 * @desciption
 */
public class OnlyMain {


    public static void main(String[] args) {
        //虚拟机线程管理接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo:threadInfos){
            System.out.println("[" +threadInfo.getThreadId()+"]"+" "+threadInfo.getThreadName());
        }

    }
}
