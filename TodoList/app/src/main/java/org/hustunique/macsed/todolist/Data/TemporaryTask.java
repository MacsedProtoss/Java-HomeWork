package org.hustunique.macsed.todolist.Data;

import java.util.Date;

public class TemporaryTask extends Task {

    Date endTime;

    TemporaryTask(String name,String description, Date endTime) {
        super(name,description);
        super.type = TaskType.Temporary;
        this.endTime = endTime;
    }

}
