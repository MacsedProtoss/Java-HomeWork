package org.hustunique.macsed.todolist.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.hustunique.macsed.todolist.Data.DataManager;
import org.hustunique.macsed.todolist.Data.Task.LongTermTask;
import org.hustunique.macsed.todolist.Data.Task.RepeatTask;
import org.hustunique.macsed.todolist.Data.Task.Task;
import org.hustunique.macsed.todolist.Data.Task.TemporaryTask;
import org.hustunique.macsed.todolist.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CustomDialogBuilder {

    private Context context;
    private LayoutInflater inflater;
    private DataManager dataManager;

    public CustomDialogBuilder(Context context,LayoutInflater inflater,DataManager datamanager){
        this.context = context;
        this.inflater = inflater;
        this.dataManager = datamanager;
    }

    public void getAddTingsView(MainListAdapter madapter){

        final Context context = this.context;
        final LayoutInflater inflater = this.inflater;
        final MainListAdapter adapter = madapter;
        final DataManager manager = this.dataManager;

        AlertDialog.Builder builder = new  AlertDialog.Builder(context);
        builder.setTitle("");

        View dialogLayout = inflater.inflate(R.layout.add_new_reminder, null);
        builder.setView(dialogLayout);

        final Spinner spinner = dialogLayout.findViewById(R.id.add_spin);
        final EditText nameText = dialogLayout.findViewById(R.id.add_name);
        final EditText descriptionText = dialogLayout.findViewById(R.id.add_description);
        final EditText endTimeText = dialogLayout.findViewById(R.id.add_endTime);
        final EditText repeatTimeText = dialogLayout.findViewById(R.id.add_repeatTime);
        final EditText strideText = dialogLayout.findViewById(R.id.add_stride);
        final TextView subText = dialogLayout.findViewById(R.id.add_sub);

        builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String dateString = endTimeText.getText().toString();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date formatedDate = null;

                try {
                    formatedDate = format.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast toast=Toast.makeText(context, "添加失败！输入的日期有误", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }


                if (repeatTimeText.getVisibility() == View.VISIBLE){
                    //MARK : repeat

                    if (strideText.getText().toString().equals("") || repeatTimeText.getText().toString().equals("") || nameText.getText().toString().equals("") || descriptionText.getText().toString().equals("")){
                        Toast toast=Toast.makeText(context, "添加失败！有未输入的信息", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }


                    int increasedDays = Integer.valueOf(strideText.getText().toString())*Integer.valueOf(repeatTimeText.getText().toString());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(formatedDate);
                    cal.add(Calendar.DATE, increasedDays); //minus number would decrement the days
                    Date endTime = cal.getTime();

                    RepeatTask newTask = new RepeatTask(nameText.getText().toString(),descriptionText.getText().toString(),formatedDate,endTime,Integer.valueOf(strideText.getText().toString()),Integer.valueOf(repeatTimeText.getText().toString()));
                    manager.addNewDataToList(newTask,adapter);

                }else {

                    if (nameText.getText().toString().equals("") || descriptionText.getText().toString().equals("")){
                        Toast toast=Toast.makeText(context, "添加失败！有未输入的信息", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    if (subText.getVisibility() == View.VISIBLE){
                        //MARK : longTerm
                        LongTermTask newTask = new LongTermTask(nameText.getText().toString(),descriptionText.getText().toString(),null,formatedDate);
                        manager.addNewDataToList(newTask,adapter);

                    }else{
                        //MARK : temporary

                        if (nameText.getText().toString().equals("") || descriptionText.getText().toString().equals("")){
                            Toast toast=Toast.makeText(context, "添加失败！有未输入的信息", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                        }

                        TemporaryTask newTask = new TemporaryTask(nameText.getText().toString(),descriptionText.getText().toString(),formatedDate);
                        manager.addNewDataToList(newTask,adapter);
                    }
                }
            }
        });



        final AlertDialog dialog = builder.create();



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    repeatTimeText.setVisibility(View.INVISIBLE);
                    strideText.setVisibility(View.INVISIBLE);
                    subText.setVisibility(View.INVISIBLE);
                    endTimeText.setHint("结束时间(yyyy-MM-dd)");
                }else if (position == 1){
                    repeatTimeText.setVisibility(View.VISIBLE);
                    strideText.setVisibility(View.VISIBLE);
                    subText.setVisibility(View.INVISIBLE);
                    endTimeText.setHint("第一次截止时间(yyyy-MM-dd)");
                }else if (position == 2){
                    repeatTimeText.setVisibility(View.INVISIBLE);
                    strideText.setVisibility(View.INVISIBLE);
                    subText.setVisibility(View.VISIBLE);
                    endTimeText.setHint("结束时间(yyyy-MM-dd)");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dialog.show();
    }

    public void getThingsPreviewView(final MainListAdapter madapter, final Task finalTask, final int position){

        final Context context = this.context;
        final LayoutInflater inflater = this.inflater;
        final MainListAdapter adapter = madapter;
        final DataManager manager = this.dataManager;

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

                getEditThingsView(madapter,finalTask,position);
            }
        });

        builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d("will delete item at position",String.valueOf(position));
                manager.deleteDataInList(position,madapter);
            }

        });

        builder.show();
    }

    public void getEditThingsView(final MainListAdapter madapter, Task finalTask, final int position){
        final Context context = this.context;
        final LayoutInflater inflater = this.inflater;
        final MainListAdapter adapter = madapter;
        final DataManager manager = this.dataManager;

        AlertDialog.Builder builder = new  AlertDialog.Builder(context);
        builder.setTitle("");

        View dialogLayout = inflater.inflate(R.layout.add_new_reminder, null);
        builder.setView(dialogLayout);

        final Spinner spinner = dialogLayout.findViewById(R.id.add_spin);
        final EditText nameText = dialogLayout.findViewById(R.id.add_name);
        final EditText descriptionText = dialogLayout.findViewById(R.id.add_description);
        final EditText endTimeText = dialogLayout.findViewById(R.id.add_endTime);
        final EditText repeatTimeText = dialogLayout.findViewById(R.id.add_repeatTime);
        final EditText strideText = dialogLayout.findViewById(R.id.add_stride);
        final TextView subText = dialogLayout.findViewById(R.id.add_sub);
        final TextView titleText = dialogLayout.findViewById(R.id.titleText);

        titleText.setText("修改事项");
        nameText.setText(finalTask.getName());
        descriptionText.setText(finalTask.getDescription());

        switch(finalTask.getType()){
            case Temporary:

                spinner.setSelection(0);
                TemporaryTask temporaryTask = (TemporaryTask)finalTask;
                repeatTimeText.setVisibility(View.INVISIBLE);
                strideText.setVisibility(View.INVISIBLE);
                endTimeText.setText(temporaryTask.getEndTime().toString());
                subText.setVisibility(View.INVISIBLE);

                break;
            case Repeat:

                spinner.setSelection(1);
                RepeatTask repeatTask = (RepeatTask) finalTask;
                repeatTimeText.setVisibility(View.VISIBLE);
                strideText.setVisibility(View.VISIBLE);
                endTimeText.setText(repeatTask.getEndTime().toString());
                repeatTimeText.setText(String.valueOf(repeatTask.getRepeatTime()));
                strideText.setText(String.valueOf(repeatTask.getStride()));
                subText.setVisibility(View.INVISIBLE);

                break;
            case LongTerm:

                spinner.setSelection(2);
                LongTermTask longTermTask = (LongTermTask) finalTask;
                repeatTimeText.setVisibility(View.INVISIBLE);
                strideText.setVisibility(View.INVISIBLE);
                endTimeText.setText(longTermTask.getEndTime().toString());
                subText.setVisibility(View.VISIBLE);

                break;
        }

        builder.setPositiveButton("完成", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String dateString = endTimeText.getText().toString();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date formatedDate = null;

                try {
                    formatedDate = format.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast toast=Toast.makeText(context, "修改失败！输入的日期有误", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }


                if (repeatTimeText.getVisibility() == View.VISIBLE){
                    //MARK : repeat

                    if (strideText.getText().toString().equals("") || repeatTimeText.getText().toString().equals("") || nameText.getText().toString().equals("") || descriptionText.getText().toString().equals("")){
                        Toast toast=Toast.makeText(context, "修改失败！有未输入的信息", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }


                    int increasedDays = Integer.valueOf(strideText.getText().toString())*Integer.valueOf(repeatTimeText.getText().toString());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(formatedDate);
                    cal.add(Calendar.DATE, increasedDays); //minus number would decrement the days
                    Date endTime = cal.getTime();

                    RepeatTask newTask = new RepeatTask(nameText.getText().toString(),descriptionText.getText().toString(),formatedDate,endTime,Integer.valueOf(strideText.getText().toString()),Integer.valueOf(repeatTimeText.getText().toString()));
                    manager.updateDataInList(newTask,position,madapter);

                }else {

                    if (nameText.getText().toString().equals("") || descriptionText.getText().toString().equals("")){
                        Toast toast=Toast.makeText(context, "修改失败！有未输入的信息", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    if (subText.getVisibility() == View.VISIBLE){
                        //MARK : longTerm
                        LongTermTask newTask = new LongTermTask(nameText.getText().toString(),descriptionText.getText().toString(),null,formatedDate);
                        manager.updateDataInList(newTask,position,madapter);

                    }else{
                        //MARK : temporary

                        if (nameText.getText().toString().equals("") || descriptionText.getText().toString().equals("")){
                            Toast toast=Toast.makeText(context, "修改失败！有未输入的信息", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                        }

                        TemporaryTask newTask = new TemporaryTask(nameText.getText().toString(),descriptionText.getText().toString(),formatedDate);
                        manager.updateDataInList(newTask,position,madapter);
                    }
                }
            }
        });



        final AlertDialog dialog = builder.create();



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    repeatTimeText.setVisibility(View.INVISIBLE);
                    strideText.setVisibility(View.INVISIBLE);
                    subText.setVisibility(View.INVISIBLE);
                    endTimeText.setHint("结束时间(yyyy-MM-dd)");
                }else if (position == 1){
                    repeatTimeText.setVisibility(View.VISIBLE);
                    strideText.setVisibility(View.VISIBLE);
                    subText.setVisibility(View.INVISIBLE);
                    endTimeText.setHint("第一次截止时间(yyyy-MM-dd)");
                }else if (position == 2){
                    repeatTimeText.setVisibility(View.INVISIBLE);
                    strideText.setVisibility(View.INVISIBLE);
                    subText.setVisibility(View.VISIBLE);
                    endTimeText.setHint("结束时间(yyyy-MM-dd)");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dialog.show();

    }

    

}

