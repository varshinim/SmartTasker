package com.codepath.smarttasker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import com.codepath.smarttasker.adaptors.TodoArrayAdapter;
import com.codepath.smarttasker.data.ItemListDbHelper;
import com.codepath.smarttasker.models.Task;



public class MainActivity extends AppCompatActivity {
    ArrayList<Task> items;
    TodoArrayAdapter itemsAdapter;

    ItemListDbHelper dbHelper;
    ListView lvItems;
    public static final int Edit_Item_Activity = 1;
    public static final int DEFAULT_PRIORITY = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);

        dbHelper = new ItemListDbHelper(MainActivity.this);
        items = dbHelper.getAllItems();

        itemsAdapter = new TodoArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListenerForLongClick();
        setupListViewListenerForClick();
    }

    public void updateTaskList() {
        items = dbHelper.getAllItems();
        itemsAdapter.clear();
        itemsAdapter.updateTaskList(items);
    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        if (!itemText.equals("")) {
            Task task = new Task();
            task.setText(itemText);
            task.setPriority(DEFAULT_PRIORITY);

            dbHelper.insertItem(task);
            updateTaskList();
            etNewItem.setText("");
        }
    }


    public void setupListViewListenerForLongClick(){
        lvItems.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener(){
                @Override
                public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id){
                    dbHelper.deleteItem(items.get(pos));
                    updateTaskList();
                    return true;
                }
        });
    }

    public void setupListViewListenerForClick(){
        lvItems.setOnItemClickListener(
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapter, View item, int pos, long id){
                    Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                    i.putExtra("item", items.get(pos));
                    startActivityForResult(i, Edit_Item_Activity);
                }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == Edit_Item_Activity) {
            Task task = data.getParcelableExtra("item");
            dbHelper.updateItem(task);
            updateTaskList();
        }
    }
}
