package com.codepath.smarttasker.adaptors;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.smarttasker.R;
import com.codepath.smarttasker.models.Task;

import java.text.SimpleDateFormat;
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
        TextView tvDueDate = (TextView) view.findViewById(R.id.dueDate);
        TextView tvPriority = (TextView) view.findViewById(R.id.priority);
        Task current = taskList.get(position);
        tvBody.setText(current.getText());

        if (current.getDueDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yy");
            String dateStr = sdf.format(current.getDueDate());
            tvDueDate.setText(dateStr);
        }

        tvPriority.setText("P" + Integer.toString(current.getPriority()));
        return view;
    }

    @Override
    public Task getItem(int i){
        return taskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return taskList.get(i).getId();
    }

    @Override
    public int getCount() {
        try {
            int size = taskList.size();
            return size;
        } catch(NullPointerException ex) {
            return 0;
        }
    }

    public void updateTaskList(List<Task> newlist) {
        taskList.clear();
        taskList.addAll(newlist);
        this.notifyDataSetChanged();
    }
}
