package xyz.drafter.ch1.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author wangmeng
 * @date 2019/11/27
 * @desciption
 */
public class FindDirsFiles extends RecursiveAction {

    // 当前任务需要搜寻的目录
    private File path;

    public FindDirsFiles(File path) {
        this.path = path;
    }

    @Override
    protected void compute() {
        List<FindDirsFiles> subTasks = new ArrayList<>();
        File[] files = path.listFiles();
        if (files != null){
            for (File file :files){
                if (file.isDirectory()){
                    subTasks.add(new FindDirsFiles(file));
                }else {
                    //遇到文件 检查
                    if (file.getAbsolutePath().endsWith("txt")){
                        System.out.println("文件: "+file.getAbsolutePath());
                    }
                }
            }
            if (!subTasks.isEmpty()){
                for (FindDirsFiles subTask:invokeAll(subTasks)){
                    // 等待子任务完成
                    subTask.join();
                }
            }
        }
    }


    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        FindDirsFiles task = new FindDirsFiles(new File("F:/"));
        // 异步
        pool.execute(task);
        System.out.println("Task is Running......");

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int otherWork = 0;
        for (int i = 0;i<100;i++){
            otherWork = otherWork+i;
        }

        System.out.println("Main Thread done sth....,otherWork = "+otherWork);
        // 阻塞
        task.join();
        System.out.println("Task end");
    }
}
