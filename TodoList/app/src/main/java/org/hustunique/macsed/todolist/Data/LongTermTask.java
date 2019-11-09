package org.hustunique.macsed.todolist.Data;

import java.util.Date;
import java.util.List;

public class LongTermTask extends Task {

    LongTermTask parentTask;
    List<LongTermTask> sonTasks;
    Date endTime;

    public LongTermTask(String name, String description,LongTermTask parentTask,Date endTime) {
        super(name, description);
        super.type = TaskType.LongTerm;
        this.endTime = endTime;
        this.parentTask = parentTask;
        this.sonTasks = null;
        parentTask.sonTasks.add(this);
    }

}
