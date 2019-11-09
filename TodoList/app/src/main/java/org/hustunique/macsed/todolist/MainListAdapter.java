package org.hustunique.macsed.todolist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import org.hustunique.macsed.todolist.Data.*;

import java.util.ArrayList;
import java.util.List;

public class MainListAdapter extends BaseAdapter {

    private List<Task> tasks;
    private Context context;

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setContext(Context context) {
        this.context = context;
    }

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
                endTimeText.setText(((TemporaryTask) task).getEndTime().toString());
                break;
            case LongTerm:
                task = (LongTermTask)task;
                endTimeText.setText(((TemporaryTask) task).getEndTime().toString());
                break;

        }

        return convertView;
    }
}
