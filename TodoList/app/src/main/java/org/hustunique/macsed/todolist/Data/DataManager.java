package org.hustunique.macsed.todolist.Data;


import android.util.Log;

import org.hustunique.macsed.todolist.Data.Task.LongTermTask;
import org.hustunique.macsed.todolist.Data.Task.RepeatTask;
import org.hustunique.macsed.todolist.Data.Task.Task;
import org.hustunique.macsed.todolist.Data.Task.TaskType;
import org.hustunique.macsed.todolist.Data.Task.TemporaryTask;
import org.hustunique.macsed.todolist.UI.MainListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManager {

    private FileManager fileManager = new FileManager();

    private JsonManager jsonManager = new JsonManager();

    private List<Task> listData = null;

    public List getListData(){
        return this.listData;
    }


    public List getSortedList(List<Task> tasks,SortType type){
        switch (type){
            case fileDefault:
                return this.listData;
            case custom:
                return null;
            case dueTime:
                List<Task> output = new ArrayList<Task>(tasks); ;
                int count = output.size();

                Log.d("task count",String.valueOf(count));

                for (int i = 0 ; i< count ; i++){

                    Task task = output.get(i);
                    Date miniumEndTime = new Date();
                    switch(task.getType()) {
                        case Temporary:
                            task = (TemporaryTask)task;
                            miniumEndTime = ((TemporaryTask) task).getEndTime();
                            break;
                        case Repeat:
                            task = (RepeatTask)task;
                            miniumEndTime = ((TemporaryTask) task).getEndTime();
                            break;
                        case LongTerm:
                            task = (LongTermTask)task;
                            miniumEndTime = ((TemporaryTask) task).getEndTime();
                            break;

                    }

                    for (int j = i+1 ; j < count ; j++){
                        Task task1 = output.get(j);
                        Date currentDate = new Date();
                        switch(task1.getType()) {
                            case Temporary:
                                task1 = (TemporaryTask)task1;
                                currentDate = ((TemporaryTask) task1).getEndTime();
                                break;
                            case Repeat:
                                task1 = (RepeatTask)task1;
                                currentDate = ((TemporaryTask) task1).getEndTime();
                                break;
                            case LongTerm:
                                task1 = (LongTermTask)task1;
                                currentDate = ((TemporaryTask) task1).getEndTime();
                                break;

                        }

                        Log.d("task time current",currentDate.toString());
                        Log.d("task time minium",miniumEndTime.toString());
                        Log.d("task time is before",String.valueOf(currentDate.before(miniumEndTime)));


                        if (currentDate.before(miniumEndTime)){
                            output.set(i,task1);
                            output.set(j,task);
                        }

                    }
                }
                return output;
        }

        return null;
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

    public Task searchTask(String name){

        return loopSearchTask(listData,name);

    }

    private Task loopSearchTask(List<Task> tasks,String name){
        List<Task> parentTasks = new ArrayList<Task>();
        for (Task task : tasks){
            if (task.getName().contains(name)){
                return task;
            }
            if (task.getType() == TaskType.LongTerm){

                if (((LongTermTask) task).getSonTasks().size() > 0){
                    parentTasks.add(task);
                }
            }
        }

        if (parentTasks.size()>0){
            return loopSearchTask(parentTasks,name);
        }else{
            return null;
        }

    }

    public boolean writeToExternal(String filename){
        return fileManager.writeToExternal(filename,jsonManager.encodeJson());
    }


    public boolean readFromExternal(String filename){
        String result = fileManager.readFromExternal(filename);

        if (result != null){

            jsonManager.setJson(result);
            List<Task> imported = jsonManager.decodeJson();

            listData.addAll(imported);

            jsonManager.setTask(listData);
            fileManager.writeJson(jsonManager.encodeJson());

            return true;
        }else{
            return false;
        }


    }

}

