package org.hustunique.macsed.todolist.Data;

import java.text.DateFormat;
import java.util.Date;

public class RepeatTask extends Task {

    Date startTime;
    Date endTime;
    int stride;
    int repeatTime;


    RepeatTask(String name, String description,Date startTime,Date endTime,int stride,int repeatTime) {
        super(name, description);
        super.type = TaskType.Repeat;
        this.startTime = startTime;
        this.endTime = endTime;
        this.repeatTime = repeatTime;
        this.stride = stride;
    }

}
