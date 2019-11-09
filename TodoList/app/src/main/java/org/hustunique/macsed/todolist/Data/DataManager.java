package org.hustunique.macsed.todolist.Data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataManager {

    private List<Task> tasks;
    private String json;

    public void setTask(List<Task> tasks){
        this.tasks = tasks;
    }

    public void setJson(String json){
        this.json = json;
    }

    public List<Task> decodeJson(){

        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        List<Map<String,String>> DecodeBackData = new ArrayList<Map<String,String>>();
        tasks = new ArrayList<>();

        if (json == null){
            Log.d("Error in decoding Json","task is null");

        }else{
            DecodeBackData = gson.fromJson(json, List.class);
        }

        for (Map<String,String> task:DecodeBackData) {

            String codeBack = gson.toJson(task);

            switch (task.get("type")){
                case "Temporary":

                    TemporaryTask temp = gson.fromJson(codeBack,TemporaryTask.class);

                    tasks.add(temp);
                    break;
                case "Repeat":
                    tasks.add(gson.fromJson(codeBack,RepeatTask.class));
                    break;
                case "LongTerm":
                    tasks.add(gson.fromJson(codeBack,LongTermTask.class));
                    break;
            }


        }

        return tasks;

    }


    public String encodeJson(){
        
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        json = null;
        List tempList = new ArrayList();

        if (tasks == null){
            Log.d("Error in encoding Json","task is null");

        }else{

            json = gson.toJson(tasks,List.class);
        }

        return json;
    }




}

