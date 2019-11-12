package org.hustunique.macsed.todolist.Data.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LongTermTask extends Task {

    private List<Task> sonTasks;
    private Date endTime;

    public Date getEndTime() {
        return endTime;
    }

    public LongTermTask getParentTask() {
        return parentTask;
    }

    public List<Task> getSonTasks() {
        return sonTasks;
    }

    public LongTermTask(String name, String description, LongTermTask parentTask, Date endTime) {
        super(name, description);
        super.type = TaskType.LongTerm;
        this.endTime = endTime;
        super.parentTask = parentTask;
        this.sonTasks = new ArrayList();
        if (parentTask != null){
            parentTask.sonTasks.add(this);
        }

    }

    public void addSonTask(Task sonTask){
        sonTasks.add(sonTask);
    }

    public void deleteSonTask(int index){
        sonTasks.remove(index);
    }

    public void updateSonTask(int index,Task sonTask){
        sonTasks.set(index,sonTask);
    }

    public void clearSonTasks(){
        sonTasks = new ArrayList<>();
    }
}
