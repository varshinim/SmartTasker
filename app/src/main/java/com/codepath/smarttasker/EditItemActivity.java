package com.codepath.smarttasker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.codepath.smarttasker.models.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.codepath.smarttasker.R.id.editText;
import static com.codepath.smarttasker.R.id.tvDate;
import static com.codepath.smarttasker.R.id.tvPicker;


public class EditItemActivity extends AppCompatActivity {
    private TextView tvDisplayDate;
    private NumberPicker np;
    private Task task;

    private DatePickerDialog datePickerDialog;

    static final int DATE_DIALOG_ID = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent intent = getIntent();
        task = intent.getParcelableExtra("item");
        EditText editItem = (EditText) findViewById(editText);
        editItem.setText(task.getText());
        editItem.setSelection(editItem.getText().length());

        Date date = task.getDueDate();
        if (date == null) {
            Calendar c = Calendar.getInstance();
            date = c.getTime();
        }

        np = (NumberPicker) findViewById(tvPicker);
        np.setMinValue(1);
        np.setMaxValue(4);
        np.setWrapSelectorWheel(true);

        setDateOnView(date);
        addListenerOnDateClick(date);
        setPriorityOnView(task.getPriority());
        addListenerOnPriorityClick(task.getPriority());
    }

    public void onSaveItem(View v){
        EditText etName = (EditText) findViewById(editText);

        Intent data = new Intent();
        task.setText(etName.getText().toString());
        data.putExtra("item", task);
        int pos = getIntent().getIntExtra("pos", -1);
        data.putExtra("pos", pos);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        finish();
    }

    public String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    // display current date
    public void setDateOnView(Date date) {
        tvDisplayDate = (TextView) findViewById(tvDate);
        // set current date into textview
        tvDisplayDate.setText(formatDate(date));
    }

    public void setPriorityOnView(Integer priority) {
        if (priority != null) {
            np.setValue(priority);
        }
    }

    public void addListenerOnDateClick(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        tvDisplayDate = (TextView) findViewById(tvDate);
        datePickerDialog = new DatePickerDialog(EditItemActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int month, int day) {
                        final Calendar c = Calendar.getInstance();
                        c.set(year, month, day);
                        task.setDueDate(c.getTime());
                        tvDisplayDate.setText(formatDate(task.getDueDate()));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        tvDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    public void addListenerOnPriorityClick(Integer priority) {
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                task.setPriority((Integer) newVal);
            }
        });

    }

}
