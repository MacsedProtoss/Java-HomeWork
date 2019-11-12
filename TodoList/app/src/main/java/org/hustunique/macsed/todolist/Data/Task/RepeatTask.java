package org.hustunique.macsed.todolist.Data.Task;

import java.util.Date;

public class RepeatTask extends Task {

    private Date startTime;
    private Date endTime;
    private int stride;
    private int repeatTime;

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getRepeatTime() {
        return repeatTime;
    }

    public int getStride() {
        return stride;
    }

    public RepeatTask(String name, String description, Date startTime, Date endTime, int stride, int repeatTime) {
        super(name, description);
        super.type = TaskType.Repeat;
        this.startTime = startTime;
        this.endTime = endTime;
        this.repeatTime = repeatTime;
        this.stride = stride;
    }

}
