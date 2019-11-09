package org.hustunique.macsed.todolist.Data;

import java.util.Date;
import java.util.List;

public class LongTermTask extends Task {

    private LongTermTask parentTask;
    private List<LongTermTask> sonTasks;
    private Date endTime;

    public Date getEndTime() {
        return endTime;
    }

    public LongTermTask getParentTask() {
        return parentTask;
    }

    public List<LongTermTask> getSonTasks() {
        return sonTasks;
    }

    public LongTermTask(String name, String description, LongTermTask parentTask, Date endTime) {
        super(name, description);
        super.type = TaskType.LongTerm;
        this.endTime = endTime;
        this.parentTask = parentTask;
        this.sonTasks = null;
        parentTask.sonTasks.add(this);
    }

}
