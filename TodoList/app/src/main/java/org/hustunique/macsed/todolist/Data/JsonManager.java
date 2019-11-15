package org.hustunique.macsed.todolist.Data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hustunique.macsed.todolist.Data.Task.*;

public class JsonManager {

    private List<Task> tasks;
    private String json;

    public void setTask(List<Task> tasks){
        this.tasks = tasks;
    }

    public void setJson(String json){
        this.json = json;
    }

    public List<Task> decodeJson(){

//
//        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//        Type type = TypeToken.getParameterized(ArrayList.class,Task.class).getType();
//
//        return gson.fromJson(json,type);

        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();


        List<Map<String,Object>> DecodeBackData = new ArrayList<Map<String,Object>>();
        tasks = new ArrayList<>();

        if (json == null){
            Log.d("Error in decoding Json","task is null");

        }else{

            DecodeBackData = gson.fromJson(json, List.class);
            Log.d("mapValue",DecodeBackData.toString());
        }

        for (Map<String,Object> task:DecodeBackData) {

            tasks.add(decodeFromPart(task));


        }

        Log.d("task info json manager",gson.toJson(tasks));

        return tasks;

    }



    private Task decodeFromPart(Map<String,Object> part){

        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String codeBack = gson.toJson(part);

        Object typeValue = part.get("type");

        switch (typeValue.toString()){
            case "Temporary":
                TemporaryTask task = (gson.fromJson(codeBack,TemporaryTask.class));
                return task;

            case "Repeat":
                RepeatTask task1 = (gson.fromJson(codeBack,RepeatTask.class));
                return task1;

            case "LongTerm":

                Object obj = part.get("sonTasks");

                String listData = obj.toString();
                Log.d("listData",listData);

                Log.d("obj class",obj.getClass().toString());

                List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

                list = (ArrayList)obj;

                Log.d("list",list.toString());

                LongTermTask task2 = (gson.fromJson(codeBack,LongTermTask.class));

                task2.clearSonTasks();

                for (Map<String,Object> temp:list
                     ) {

                    task2.addSonTask(decodeFromPart(temp));
                    Log.d("subTask",gson.toJson(task2));

                }

                Log.d("subTask count",String.valueOf(task2.getSonTasks().size()));

                return  task2;


        }



        return null;


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

