package xyz.drafter.ch1.actual;

/**
 * @author drafter
 * @date 2019/12/2
 * @desciption
 */
public class TaskResult<R> {

    private final TaskResultType resultType;
    private final R returnValue;// 方法的业务结果数据
    private final String reason; // 这里放方法失败的原因

    public TaskResult(TaskResultType resultType, R returnValue, String reason) {
        this.resultType = resultType;
        this.returnValue = returnValue;
        this.reason = reason;
    }



    public TaskResult(TaskResultType resultType, R returnValue) {
        this.resultType = resultType;
        this.returnValue = returnValue;
        this.reason = "SUCCESS";
    }

    public TaskResultType getResultType() {
        return resultType;
    }

    public R getReturnValue() {
        return returnValue;
    }

    public String getReason() {
        return reason;
    }


    @Override
    public String toString() {
        return "TaskResult{" +
                "resultType=" + resultType +
                ", returnValue=" + returnValue +
                ", reason='" + reason + '\'' +
                '}';
    }
}
