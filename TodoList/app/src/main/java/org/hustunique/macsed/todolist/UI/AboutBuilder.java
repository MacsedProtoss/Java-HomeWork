package org.hustunique.macsed.todolist.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import org.hustunique.macsed.todolist.R;

public class AboutBuilder {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    private LayoutInflater inflater;

    public void getAbout(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("关于");

        View dialogLayout = inflater.inflate(R.layout.layout_about, null);

        builder.setView(dialogLayout);

        final AlertDialog dialog = builder.create();

        dialog.show();
    }

}
