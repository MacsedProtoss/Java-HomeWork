package org.hustunique.macsed.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.hustunique.macsed.todolist.Data.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FileManager fileManager = new FileManager();
        fileManager.setPath(getFilesDir().getAbsolutePath());

        final DataManager dataManager = new DataManager();
        dataManager.setJson(fileManager.readJson());

        final List<Task> listData = dataManager.decodeJson();

        final MainListAdapter adapter = new MainListAdapter();
        adapter.setTasks(listData);
        adapter.setContext(ScrollingActivity.this);

        final ListView listView = (ListView) findViewById(R.id.MainList);
        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = ScrollingActivity.this;
                LayoutInflater inflater = getLayoutInflater();

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
                        if (repeatTimeText.getVisibility() == View.VISIBLE){
                            //MARK : repeat
                            //RepeatTask newTask = new RepeatTask(nameText.getText().toString(),descriptionText.getText().toString(),Date(),endTimeText.getText().toString());
                        }else {
                            if (subText.getVisibility() == View.VISIBLE){
                                //MARK : longTerm
                            }else{
                                //MARK : temporary
                                String dateString = endTimeText.getText().toString();
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date formatedDate = null;
                                try {
                                    formatedDate = format.parse(dateString);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                TemporaryTask newTask = new TemporaryTask(nameText.getText().toString(),descriptionText.getText().toString(),formatedDate);
                                listData.add(newTask);
                                dataManager.setTask(listData);
                                fileManager.writeJson(dataManager.encodeJson());
                                adapter.setTasks(listData);
                                adapter.notifyDataSetChanged();
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
                        }else if (position == 1){
                            repeatTimeText.setVisibility(View.VISIBLE);
                            strideText.setVisibility(View.VISIBLE);
                            subText.setVisibility(View.INVISIBLE);
                        }else if (position == 2){
                            repeatTimeText.setVisibility(View.INVISIBLE);
                            strideText.setVisibility(View.INVISIBLE);
                            subText.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });







                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
