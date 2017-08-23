package com.codepath.smarttasker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.codepath.smarttasker.models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class ItemListDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =  3;
    public static final String DATABASE_NAME = "tasks.db";
    public static final String TABLE_NAME = "task";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "text";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_DUE_DATE = "dueDate";


    public ItemListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PRIORITY + " INTEGER, " +
                COLUMN_DUE_DATE + "  TEXT " + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DB Upgrade:", "vesrion");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertItem(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, task.getText());
        // contentValues.put(COLUMN_DUE_DATE, task.getDueDate().toString());
        contentValues.put(COLUMN_PRIORITY, task.getPriority());
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateItem(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, task.getText());
        contentValues.put(COLUMN_PRIORITY, task.getPriority());
        if (task.getDueDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(task.getDueDate());
            contentValues.put(COLUMN_DUE_DATE, dateStr);
        }
        db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[] { Integer.toString(task.getId()) } );
        return true;
    }

    public boolean deleteItem(Task item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[] { Integer.toString(item.getId()) });
        return true;
    }

    public Cursor getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_ID + "=?", new String[] { Integer.toString(id) } );
        return res;
    }
    public ArrayList<Task> getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Task> items = new ArrayList<Task>();

        Date date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        if (res.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(res.getInt(0));
                task.setText(res.getString(1));
                task.setPriority(res.getInt(2));
                String dateStr = res.getString(3);
                if (dateStr != null) {
                    try {
                        date = sdf.parse(dateStr);
                        task.setDueDate(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                items.add(task);

            } while (res.moveToNext());
        }

        Collections.sort(items, new Comparator<Task>() {
            @Override
            public int compare(Task task, Task t1) {
                int i = task.getDueDate().compareTo(t1.getDueDate());
                if (i == 0) {
                    if (task.getPriority() >= t1.getPriority()) {
                        i = 1;
                    }
                }
                return i;
            }
        });

        return items;
    }
}
