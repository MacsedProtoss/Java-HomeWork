package org.hustunique.macsed.todolist.Data;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class FileManager {

    private String path;

    public void setPath(String path) {
        StringBuilder builder = new StringBuilder();
        builder.append(path);
        builder.append("/data.json");
        this.path = path;
    }

    public void writeJson(String json){
        File file = new File(path);

        if (file.exists()){
            file.delete();
        }

        try {
            FileOutputStream output = new FileOutputStream(file);
            output.write(json.getBytes());
            output.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String readJson(){
        File file = new File(path);
        StringBuilder sb = new StringBuilder("");
        try {
            FileInputStream input = new FileInputStream(file);
            byte[] temp = new byte[1024];
            int len = 0;

            while ((len = input.read(temp)) > 0) {
                sb.append(new String(temp, 0, len));
            }

            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();


    }





}
