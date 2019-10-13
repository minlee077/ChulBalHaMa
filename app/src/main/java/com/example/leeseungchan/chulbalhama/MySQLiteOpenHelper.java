package com.example.leeseungchan.chulbalhama;

import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.NotificationCompat;


public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private Context context;

    public MySQLiteOpenHelper(Context context, String name,
                              CursorFactory factory, int version){
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        StringBuffer habit = new StringBuffer();
        habit.append("Create table habit(");
        habit.append("id integer primary key autoincrement,");
        habit.append("name text unique NOT NULL,");
        habit.append("description text default null,");
        habit.append("prepare text default null,");
        habit.append("foreign key(location_id)");
        habit.append("references location(location_id))");


        StringBuffer location = new StringBuffer();
        location.append("Create table location(");
        location.append("id integer primary key autoincrement,");
        location.append("name text Unique NOT NULL,");
        location.append("Latitude real NOT NULL,");
        location.append("longitude real NOT NULL");

        StringBuffer week = new StringBuffer();
        week.append("Create table week(");
        week.append("id integer primary key autoincrement,");
        week.append("day text not null,");
        week.append("hour integer default(null),");
        week.append("foreign key(habit_id) references habit(habit_id))");

        db.execSQL(location.toString());
        db.execSQL(habit.toString());
        db.execSQL(week.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String sql = "drop table if exists habit";
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean insertHabit(String name, String description, int habit_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("habit_id", habit_id);

        long result = db.insert("habit", null, values);
        if(result == -1){
            return false;
        }

        return true;
    }

    public Cursor getAllHabit(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from habit order by id desc", null);
        return cursor;
    }

    public boolean updataeData(String id, String name, String description, String prepare, int habit_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("description", description);
        values.put("prepare", prepare);
        values.put("name", name);
        values.put("habit_id",habit_id);
        db.update("habit", values,"ID = ?", new String[]{id});
        return true;
    }

    public Integer deleteHabit(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("habit", "name = ?", new String[] {id});
    }

}
