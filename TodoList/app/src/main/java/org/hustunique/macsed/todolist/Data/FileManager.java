package org.hustunique.macsed.todolist.Data;

import android.os.Environment;
import android.os.storage.StorageVolume;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileManager {

    private String path;

    public void setPath(String path) {
        StringBuilder builder = new StringBuilder();
        builder.append(path);
        Log.d("path",path);
        builder.append("/data.json");
        this.path = builder.toString();
        Log.d("path",this.path);

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
            return  null;
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }

        return sb.toString();


    }

    public boolean writeToExternal(String fileName,String content) {
        String state = "";
        state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)){
            String Epath = "";
            Epath = Environment.getExternalStorageDirectory().getAbsolutePath();
            Log.d("external path",Epath);
            StringBuilder builder = new StringBuilder();
            builder.append(Epath);
            builder.append("/Download/");
            builder.append(fileName);

            File file = new File(builder.toString());
            if (file.exists()){
                file.delete();
            }

            try {
                FileOutputStream output = new FileOutputStream(file);
                output.write(content.getBytes());
                output.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;

        }else{
           return false;
        }

    }

    public String readFromExternal(String fileName){
        String state = "";
        state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)){
            StringBuilder sb = new StringBuilder("");
            String Epath = "";
            Epath = Environment.getExternalStorageDirectory().getAbsolutePath();
            Log.d("external path",Epath);
            StringBuilder builder = new StringBuilder();
            builder.append(Epath);
            builder.append("/Download/");
            builder.append(fileName);

            File file = new File(builder.toString());

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
                return  null;
            } catch (IOException e) {
                e.printStackTrace();
                return  null;
            }

            String imported = sb.toString();

            return imported;

        }else{
            return null;
        }
    }



}
