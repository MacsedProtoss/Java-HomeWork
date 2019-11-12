package org.hustunique.macsed.todolist.Data;


import android.util.Log;

import org.hustunique.macsed.todolist.Data.Task.LongTermTask;
import org.hustunique.macsed.todolist.Data.Task.Task;
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
        List<Task> output = new ArrayList();

        for (Task task:listData
             ) {

            if (task.parentTask == null){
                output.add(task);
            }

        }

        return output;
    }

    public List getSubListOf(LongTermTask parentTask){

        for (Task task:listData
        ) {

            if (task.equals(parentTask)){
                LongTermTask t = (LongTermTask) task;
                return t.getSonTasks();
            }
        }

        return null;
    }


    public void initWithPath(String path) {
        fileManager.setPath(path);
        jsonManager.setJson(fileManager.readJson());
        listData = jsonManager.decodeJson();
    }

    public void addNewDataToList(Task newTask,MainListAdapter adapter){

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


}
