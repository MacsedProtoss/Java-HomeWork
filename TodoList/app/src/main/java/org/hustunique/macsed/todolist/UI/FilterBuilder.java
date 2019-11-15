package org.hustunique.macsed.todolist.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import org.hustunique.macsed.todolist.Data.SortType;
import org.hustunique.macsed.todolist.R;

public class FilterBuilder {
    private Context context;
    private LayoutInflater inflater;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void getFilter(){
        final AlertDialog.Builder builder = new  AlertDialog.Builder(context);
        builder.setTitle("选择排序方式");

        View dialogLayout = inflater.inflate(R.layout.layout_filter_select, null);

        final Button defaultBtn = dialogLayout.findViewById(R.id.fileDefaultBtn);
        final Button dueTimeBtn = dialogLayout.findViewById(R.id.dueTimeBtn);
        final Button doneBtn = dialogLayout.findViewById(R.id.filter_doneBtn);

        builder.setView(dialogLayout);

        final ScrollingActivity activity = (ScrollingActivity)context;
        final AlertDialog dialog = builder.create();

        if (activity.adapter.getSortType() == SortType.done){
            doneBtn.setText("显示未完成");
        }else{
            doneBtn.setText("显示已完成");
        }

        defaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setSortType(SortType.fileDefault);
                activity.adapter.setSortType(activity.getSortType());
                activity.adapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        });

        dueTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setSortType(SortType.dueTime);
                activity.adapter.setSortType(activity.getSortType());
                activity.adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("btnName",doneBtn.getText().toString());
                if (doneBtn.getText().toString().equals("显示未完成")){

                    activity.setSortType(SortType.undo);
                    activity.adapter.setSortType(activity.getSortType());
                    activity.adapter.notifyDataSetChanged();


                    dialog.dismiss();
                }else{

                    activity.setSortType(SortType.done);
                    activity.adapter.setSortType(activity.getSortType());
                    activity.adapter.notifyDataSetChanged();


                    dialog.dismiss();

                }
            }
        });





        dialog.show();
    }


}
