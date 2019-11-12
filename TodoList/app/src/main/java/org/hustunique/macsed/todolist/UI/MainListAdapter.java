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

import org.hustunique.macsed.todolist.Data.DataManager;
import org.hustunique.macsed.todolist.Data.Task.*;
import org.hustunique.macsed.todolist.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainListAdapter extends BaseAdapter {

    private List<Task> tasks;
    private DataManager manager;
    private Context context;
    private LayoutInflater inflater;
    private MainListAdapter adapter = this;

    public void setManager(DataManager manager){
        this.manager = manager;
        tasks = manager.getListData();
    }

    public void setTasks(List<Task> tasks){
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
        final int finalPosition = position;

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomDialogBuilder builder = new CustomDialogBuilder(context,inflater,manager);
                builder.getThingsPreviewView(adapter,finalTask,finalPosition);
            }
        });


        return convertView;
    }







}
