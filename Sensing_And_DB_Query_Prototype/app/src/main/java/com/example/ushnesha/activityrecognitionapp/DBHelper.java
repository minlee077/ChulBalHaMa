package com.example.ushnesha.activityrecognitionapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION =2;
    //스키마 변경 및 수정시에 DB_VERSION 바꿔주기

    public DBHelper(Context context){
        super(context,"todo_db",null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String userTable = "CREATE TABLE user ("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "starting_coordinate,"+
                "name default 'johndoe')";

        String destinationsTable = "CREATE TABLE destinations(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "coordinate not null," +
                "time," +
                "destination_name)";

        String habitsTable = "CREATE TABLE habits ("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "habit_name not null,"+
                "description," +
                "prepare default 'empty',"+
                "achievement_rate default 0)";

        String dayOfWeekTable = "CREATE TABLE day_of_week("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "day not null," +
                "departure_time," +
                "destination_id integer," +
                "habit_id integer," +
                "FOREIGN KEY(destination_id) REFERENCES destinations(_id)," +
                "FOREIGN KEY(habit_id) REFERENCES habits(_id))";

        String historyTable = "CREATE TABLE history(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "achievement," +
                "departure_time," +
                "arrival_time," +
                "idle_time," +
                "habit_id integer," +
                "FOREIGN KEY(habit_id) REFERENCES habits(_id))";

        String srbaiTable = "CREATE TABLE srbai(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "day," +
                "score default 0," +
                "habit_id integer," +
                "FOREIGN KEY(habit_id) REFERENCES habits(_id))";

        sqLiteDatabase.execSQL(userTable);
        sqLiteDatabase.execSQL(habitsTable);
        sqLiteDatabase.execSQL(destinationsTable);
        sqLiteDatabase.execSQL(dayOfWeekTable);
        sqLiteDatabase.execSQL(historyTable);
        sqLiteDatabase.execSQL(srbaiTable);


        //생성된 table 확인
        Cursor c = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                Log.e("DB Creation", "Table Name=> "+c.getString(0));
                c.moveToNext();
            }
        }
        Log.e("Database Creation", "onCreate: schema created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //Db 생성시마다 내부적으로 호출
        if(newVersion == DB_VERSION){
            sqLiteDatabase.execSQL("drop table srbai");
            sqLiteDatabase.execSQL("drop table history");
            sqLiteDatabase.execSQL("drop table day_of_week");
            sqLiteDatabase.execSQL("drop table destinations");
            sqLiteDatabase.execSQL("drop table habits");
            sqLiteDatabase.execSQL("drop table user");
            onCreate(sqLiteDatabase);
            Log.e("DB drop", "onUpgrade: schema renewed");
        }



    }
}
