package org.hustunique.macsed.todolist.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.hustunique.macsed.todolist.Data.DataManager;
import org.hustunique.macsed.todolist.Data.Task.Task;
import org.hustunique.macsed.todolist.R;

public class SearchBuilder {
    private Context context;
    private LayoutInflater inflater;
    private DataManager manager;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void setManager(DataManager manager) {
        this.manager = manager;
    }

    public void getSearch(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("搜索任务");

        View dialogLayout = inflater.inflate(R.layout.layout_search, null);

        final Button searchBtn = dialogLayout.findViewById(R.id.search_btn);
        final EditText search_name = dialogLayout.findViewById(R.id.search_name);

        builder.setView(dialogLayout);

        final AlertDialog dialog = builder.create();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = search_name.getText().toString();

                Task searchResult = manager.searchTask(name);
                if (searchResult == null){
                    Toast toast=Toast.makeText(context, "没找到该任务", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    CustomDialogBuilder builder = new CustomDialogBuilder(context,inflater,manager);
                    builder.setActionEnabeld(false);
                    builder.getThingsPreviewView(null,searchResult,0,null);
                }

                dialog.dismiss();
            }
        });


        dialog.show();
    }


}