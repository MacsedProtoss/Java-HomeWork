package org.hustunique.macsed.todolist.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.hustunique.macsed.todolist.Data.DataManager;
import org.hustunique.macsed.todolist.Data.SortType;
import org.hustunique.macsed.todolist.R;

public class FileIOBuilder {

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

    public void getIO(){
        final AlertDialog.Builder builder = new  AlertDialog.Builder(context);
        builder.setTitle("导入/导出");

        View dialogLayout = inflater.inflate(R.layout.layout_import_export, null);

        final EditText fileName = dialogLayout.findViewById(R.id.file_name);
        final Button importBtn = dialogLayout.findViewById(R.id.import_btn);
        final Button exportBtn = dialogLayout.findViewById(R.id.export_btn);

        builder.setView(dialogLayout);

        final ScrollingActivity activity = (ScrollingActivity)context;
        final AlertDialog dialog = builder.create();

        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean result = manager.writeToExternal(fileName.getText().toString());
                if (result == false){
                    Toast toast=Toast.makeText(context, "无权限或者未找到该文件！", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Toast toast=Toast.makeText(context, "导入成功！", Toast.LENGTH_SHORT);
                    toast.show();
                }
                dialog.dismiss();
            }
        });

        importBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean result = manager.readFromExternal(fileName.getText().toString());
                if (result == false){
                    Toast toast=Toast.makeText(context, "无权限或者未找到该文件！", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Toast toast=Toast.makeText(context, "导入成功！", Toast.LENGTH_SHORT);
                    toast.show();
                }
                activity.setSortType(SortType.fileDefault);
                activity.adapter.setSortType(SortType.fileDefault);
                activity.adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
