package com.codepath.smarttasker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codepath.smarttasker.models.Task;

import java.util.ArrayList;


public class ItemListDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =  1;
    public static final String DATABASE_NAME = "todo.db";
    public static final String TABLE_NAME = "items";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "text";


    public ItemListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT " + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertItem(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, task.getText());
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateItem(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, task.getText());
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
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        if (res.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(res.getInt(0));
                task.setText(res.getString(1));
                items.add(task);

            } while (res.moveToNext());
        }
        return items;
    }
}
