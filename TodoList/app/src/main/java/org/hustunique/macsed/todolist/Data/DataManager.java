package org.hustunique.macsed.todolist.Data;


import android.util.Log;

import org.hustunique.macsed.todolist.Data.Task.Task;
import org.hustunique.macsed.todolist.UI.MainListAdapter;

import java.nio.file.Path;
import java.util.List;

public class DataManager {

    private FileManager fileManager = new FileManager();

    private JosnManager josnManager = new JosnManager();

    private List<Task> listData = null;

    public List getListData(){
        return this.listData;
    }


    public void initWithPath(String path) {
        fileManager.setPath(path);
        josnManager.setJson(fileManager.readJson());
        listData = josnManager.decodeJson();
    }

    public void addNewDataToList(Task newTask,MainListAdapter adapter){

        if (listData == null){
            Log.d("Error in DataManager","find null while add New Data. It's mainly caused by not have Manager init");
            return;
        }

        listData.add(newTask);
        josnManager.setTask(listData);
        fileManager.writeJson(josnManager.encodeJson());
        adapter.setTasks(listData);
        adapter.notifyDataSetChanged();
    }

    public void updateDataInList(Task updatedTask,int index,MainListAdapter adapter){
        if (listData == null){
            Log.d("Error in DataManager","find null while update Data. It's mainly caused by not have Manager init");
            return;
        }

        listData.set(index,updatedTask);
        josnManager.setTask(listData);
        fileManager.writeJson(josnManager.encodeJson());
        adapter.setTasks(listData);
        adapter.notifyDataSetChanged();

    }

    public void deleteDataInList(int index , MainListAdapter adapter){

        if (listData == null){
            Log.d("Error in DataManager","find null while delete Data. It's mainly caused by not have Manager init");
            return;
        }

        listData.remove(index);
        josnManager.setTask(listData);
        fileManager.writeJson(josnManager.encodeJson());
        adapter.setTasks(listData);
        adapter.notifyDataSetChanged();
    }


}
