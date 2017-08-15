package com.codepath.smarttasker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.smarttasker.models.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.id;
import static com.codepath.smarttasker.R.id.editText;
import static com.codepath.smarttasker.R.id.tvDate;


public class EditItemActivity extends AppCompatActivity {
    private TextView tvDisplayDate;
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
        setDateOnView(date);
        addListenerOnDateClick(date);
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

    // display current date
    public void setDateOnView(Date date) {
        tvDisplayDate = (TextView) findViewById(tvDate);
        // set current date into textview
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        tvDisplayDate.setText(dateStr);
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
                        // set day of month , month and year value in the edit text
                        tvDisplayDate.setText(new StringBuilder()
                                .append(month + 1).append("/").append(day).append("/")
                                .append(year));


                        final Calendar c = Calendar.getInstance();
                        c.set(year, month, day);
                        task.setDueDate(c.getTime());
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        tvDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

    }

}
