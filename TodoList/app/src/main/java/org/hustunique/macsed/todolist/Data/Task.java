package org.hustunique.macsed.todolist.Data;


public class Task {
    private String name;
    private String description;
    protected TaskType type;
    private Boolean isDone;

    public TaskType getType(){
        return this.type;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public Boolean getIsDone(){
        return this.isDone;
    }

    Task(String name,String description){
        this.name = name;
        this.description = description;
        this.isDone = false;
    }

}


