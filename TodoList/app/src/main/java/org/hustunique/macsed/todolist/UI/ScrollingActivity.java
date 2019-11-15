package org.hustunique.macsed.todolist.UI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


import org.hustunique.macsed.todolist.Data.*;
import org.hustunique.macsed.todolist.R;


public class ScrollingActivity extends AppCompatActivity {

    private SortType sortType = SortType.fileDefault;
    final MainListAdapter adapter = new MainListAdapter();
    final DataManager dataManager = new DataManager();

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public SortType getSortType() {
        return sortType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context context = ScrollingActivity.this;
        final LayoutInflater inflater = getLayoutInflater();

        final ListView listView = (ListView) findViewById(R.id.MainList);

        dataManager.initWithPath(getFilesDir().getAbsolutePath());

        adapter.setContext(context);
        adapter.setInflater(inflater);
        adapter.setManager(dataManager);
        adapter.setSortType(sortType);


        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogBuilder builder = new CustomDialogBuilder(context,inflater,dataManager);
                builder.getAddTingsView(adapter,null);

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
        if (id == R.id.action_about){
            return true;
        }else if (id == R.id.action_filter){

            FilterBuilder builder = new FilterBuilder();
            builder.setContext(ScrollingActivity.this);
            builder.setInflater(getLayoutInflater());

            builder.getFilter();


        }else if (id == R.id.action_search){
            SearchBuilder builder = new SearchBuilder();
            builder.setContext(ScrollingActivity.this);
            builder.setInflater(getLayoutInflater());
            builder.setManager(dataManager);
            builder.getSearch();
        }


        return super.onOptionsItemSelected(item);
    }
}
