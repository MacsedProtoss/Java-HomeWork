package org.hustunique.macsed.todolist.Data;

enum TaskType{
    Temporary,Repeat,LongTerm
}


public class Task {
    String name;
    String description;
    TaskType type;
    Boolean isDone;


    Task(String name,String description){
        this.name = name;
        this.description = description;
        this.isDone = false;
    }

}


