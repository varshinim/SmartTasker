package com.codepath.smarttasker.adaptors;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.smarttasker.R;
import com.codepath.smarttasker.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TodoArrayAdapter extends ArrayAdapter {
    Context context;
    List<Task> taskList = new ArrayList<Task>();
    int layoutResourceId;

    public TodoArrayAdapter(Context context, int layoutResourceId,
                     List<Task> objects) {
        super(context, layoutResourceId, objects);
        this.layoutResourceId = layoutResourceId;
        this.taskList=objects;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_view, parent, false);
        TextView tvBody = (TextView) view.findViewById(R.id.listItem);
        Task current = taskList.get(position);
        tvBody.setText(current.getText());
        return view;
    }
}
