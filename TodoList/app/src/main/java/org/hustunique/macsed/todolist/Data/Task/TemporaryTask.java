package org.hustunique.macsed.todolist.Data.Task;

import java.util.Date;

public class TemporaryTask extends Task {

    private Date endTime;

    public Date getEndTime() {
        return endTime;
    }

    public TemporaryTask(String name, String description, Date endTime) {
        super(name,description);
        super.type = TaskType.Temporary;
        this.endTime = endTime;
    }

}
