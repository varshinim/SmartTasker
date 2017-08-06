package com.codepath.smarttasker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;


public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent intent = getIntent();
        String editText = intent.getStringExtra("item");
        EditText editItem = (EditText) findViewById(R.id.editText);
        editItem.setText(editText);
        editItem.setSelection(editItem.getText().length());
    }

    public void onSaveItem(View v){
        EditText etName = (EditText) findViewById(R.id.editText);

        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("item", etName.getText().toString());
        int pos = getIntent().getIntExtra("pos", -1);
        data.putExtra("pos", pos);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        finish();
    }
}
