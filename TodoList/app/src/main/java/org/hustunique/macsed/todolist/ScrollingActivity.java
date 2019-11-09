package org.hustunique.macsed.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

                builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                final AlertDialog dialog = builder.create();

                final Spinner spinner = dialogLayout.findViewById(R.id.add_spin);
                final EditText repeatTimeText = dialogLayout.findViewById(R.id.add_repeatTime);
                final EditText strideText = dialogLayout.findViewById(R.id.add_stride);
                final TextView subText = dialogLayout.findViewById(R.id.add_sub);

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
