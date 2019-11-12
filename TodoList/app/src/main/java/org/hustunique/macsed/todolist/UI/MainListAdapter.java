package org.hustunique.macsed.todolist.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.hustunique.macsed.todolist.Data.Task.*;
import org.hustunique.macsed.todolist.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainListAdapter extends BaseAdapter {

    private List<Task> tasks;
    private Context context;
    private LayoutInflater inflater;

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setInflater(LayoutInflater inflater){this.inflater = inflater;}

    @Override
    public int getCount() {
        Log.d("size",""+tasks.size());

        return tasks.size();

    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.layout_list_cell,parent,false);
        TextView nameText = (TextView) convertView.findViewById(R.id.thing_name);
        TextView descriptionText = (TextView) convertView.findViewById(R.id.thing_description);
        TextView typeText = (TextView) convertView.findViewById(R.id.thing_type);
        TextView endTimeText = (TextView) convertView.findViewById(R.id.thing_dueTime);


        Task task = tasks.get(position);

        nameText.setText(task.getName());
        descriptionText.setText(task.getDescription());
        typeText.setText(task.getType().toString());


        switch(task.getType()) {
            case Temporary:
                task = (TemporaryTask)task;
                endTimeText.setText(((TemporaryTask) task).getEndTime().toString());
                break;
            case Repeat:
                task = (RepeatTask)task;
                endTimeText.setText(((RepeatTask) task).getEndTime().toString());
                break;
            case LongTerm:
                task = (LongTermTask)task;
                endTimeText.setText(((LongTermTask) task).getEndTime().toString());
                break;

        }

        final Task finalTask = task;

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new  AlertDialog.Builder(context);
                builder.setTitle("详情");
                View dialogLayout = inflater.inflate(R.layout.layout_preview, null);

                final Spinner spinner = dialogLayout.findViewById(R.id.thing_spin);
                final EditText nameText = dialogLayout.findViewById(R.id.thing_name);
                final EditText descriptionText = dialogLayout.findViewById(R.id.thing_description);
                final EditText endTimeText = dialogLayout.findViewById(R.id.thing_endTime);
                final EditText repeatTimeText = dialogLayout.findViewById(R.id.thing_repeatTime);
                final EditText strideText = dialogLayout.findViewById(R.id.thing_stride);
                final ListView listView = dialogLayout.findViewById(R.id.sub_list);


                spinner.setEnabled(false);
                nameText.setInputType(InputType.TYPE_NULL);
                descriptionText.setInputType(InputType.TYPE_NULL);
                endTimeText.setInputType(InputType.TYPE_NULL);
                repeatTimeText.setInputType(InputType.TYPE_NULL);
                strideText.setInputType(InputType.TYPE_NULL);


                nameText.setText(finalTask.getName());
                descriptionText.setText(finalTask.getDescription());


                switch(finalTask.getType()){
                    case Temporary:


                        spinner.setSelection(0);
                        TemporaryTask temporaryTask = (TemporaryTask)finalTask;
                        repeatTimeText.setVisibility(View.INVISIBLE);
                        strideText.setVisibility(View.INVISIBLE);
                        listView.setVisibility(View.INVISIBLE);
                        endTimeText.setText(temporaryTask.getEndTime().toString());


                        break;
                    case Repeat:

                        spinner.setSelection(1);
                        RepeatTask repeatTask = (RepeatTask) finalTask;
                        repeatTimeText.setVisibility(View.VISIBLE);
                        strideText.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.INVISIBLE);
                        endTimeText.setText(repeatTask.getEndTime().toString());
                        repeatTimeText.setText(String.valueOf(repeatTask.getRepeatTime()));
                        strideText.setText(String.valueOf(repeatTask.getStride()));

                        break;
                    case LongTerm:

                        spinner.setSelection(2);
                        LongTermTask longTermTask = (LongTermTask) finalTask;
                        repeatTimeText.setVisibility(View.INVISIBLE);
                        strideText.setVisibility(View.INVISIBLE);
                        listView.setVisibility(View.VISIBLE);
                        endTimeText.setText(longTermTask.getEndTime().toString());

                        break;
                }

                builder.setView(dialogLayout);

                builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });

                builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {



                    }
                });

                builder.show();
            }
        });


        return convertView;
    }







}
