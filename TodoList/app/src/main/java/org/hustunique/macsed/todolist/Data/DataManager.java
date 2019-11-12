package org.hustunique.macsed.todolist.Data;


import android.util.Log;
import android.widget.Switch;

import org.hustunique.macsed.todolist.Data.Task.LongTermTask;
import org.hustunique.macsed.todolist.Data.Task.RepeatTask;
import org.hustunique.macsed.todolist.Data.Task.Task;
import org.hustunique.macsed.todolist.Data.Task.TemporaryTask;
import org.hustunique.macsed.todolist.UI.MainListAdapter;

import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private FileManager fileManager = new FileManager();

    private JsonManager jsonManager = new JsonManager();

    private List<Task> listData = null;

    public List getListData(){
        return this.listData;
    }

    public List getMainListData(){
        return this.listData;
    }

    public List getSubListOf(LongTermTask parentTask){

        return parentTask.getSonTasks();
    }


    public void initWithPath(String path) {
        fileManager.setPath(path);
        jsonManager.setJson(fileManager.readJson());
        listData = jsonManager.decodeJson();
    }

    public void addNewDataToList(Task newTask,MainListAdapter adapter){

        switch (newTask.getType()){
            case Temporary:
                newTask = (TemporaryTask)newTask;
                break;
            case LongTerm:
                newTask = (LongTermTask)newTask;
                break;
            case Repeat:
                newTask = (RepeatTask)newTask;
                break;
        }

        if (listData == null){
            Log.d("Error in DataManager","find null while add New Data. It's mainly caused by not have Manager init");
            return;
        }

        listData.add(newTask);
        jsonManager.setTask(listData);
        fileManager.writeJson(jsonManager.encodeJson());
        adapter.setTasks(listData);
        adapter.notifyDataSetChanged();
    }

    public void updateDataInList(Task updatedTask,int index,MainListAdapter adapter){


        if (listData == null){
            Log.d("Error in DataManager","find null while update Data. It's mainly caused by not have Manager init");
            return;
        }

        listData.set(index,updatedTask);
        jsonManager.setTask(listData);
        fileManager.writeJson(jsonManager.encodeJson());
        adapter.setTasks(listData);
        adapter.notifyDataSetChanged();

    }

    public void deleteDataInList(int index , MainListAdapter adapter){

        if (listData == null){
            Log.d("Error in DataManager","find null while delete Data. It's mainly caused by not have Manager init");
            return;
        }

        listData.remove(index);
        jsonManager.setTask(listData);
        fileManager.writeJson(jsonManager.encodeJson());
        adapter.setTasks(listData);
        adapter.notifyDataSetChanged();
    }

    public void deleteSubDataInList(int index , MainListAdapter adapter,LongTermTask parentTask){

        parentTask.deleteSonTask(index);

        jsonManager.setTask(listData);
        fileManager.writeJson(jsonManager.encodeJson());
        adapter.setTasks(getSubListOf(parentTask));
        adapter.notifyDataSetChanged();

    }

    public void updateSubDataInList(int index , MainListAdapter adapter,LongTermTask parentTask,Task newTask){

        switch (newTask.getType()){
            case Temporary:
                newTask = (TemporaryTask)newTask;
                break;
            case LongTerm:
                newTask = (LongTermTask)newTask;
                break;
            case Repeat:
                newTask = (RepeatTask)newTask;
                break;
        }


        parentTask.updateSonTask(index,newTask);

        jsonManager.setTask(listData);
        fileManager.writeJson(jsonManager.encodeJson());
        adapter.setTasks(getSubListOf(parentTask));
        adapter.notifyDataSetChanged();
    }

    public void addSubDataInList( MainListAdapter adapter,LongTermTask parentTask,Task newTask){

        switch (newTask.getType()){
            case Temporary:
                newTask = (TemporaryTask)newTask;
                break;
            case LongTerm:
                newTask = (LongTermTask)newTask;
                break;
            case Repeat:
                newTask = (RepeatTask)newTask;
                break;
        }

        parentTask.addSonTask(newTask);

        jsonManager.setTask(listData);
        fileManager.writeJson(jsonManager.encodeJson());
        adapter.setTasks(getSubListOf(parentTask));
        adapter.notifyDataSetChanged();
    }


}
