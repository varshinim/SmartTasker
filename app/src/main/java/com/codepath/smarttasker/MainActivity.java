package com.codepath.smarttasker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    public static final int Edit_Item_Activity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListenerForLongClick();
        setupListViewListenerForClick();
    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    public void setupListViewListenerForLongClick(){
        lvItems.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener(){
                @Override
                public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id){
                    items.remove(pos);
                    itemsAdapter.notifyDataSetChanged();
                    writeItems();
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
                    i.putExtra("pos", pos);
                    i.putExtra("item", items.get(pos));
                    startActivityForResult(i, Edit_Item_Activity);
                }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == Edit_Item_Activity) {
            String item = data.getStringExtra("item");
            int pos = data.getIntExtra("pos", -1);
            if (pos >= 0) {
                items.set(pos, item);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
            }
        }
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }
        catch(IOException e){
            items = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile, items);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
