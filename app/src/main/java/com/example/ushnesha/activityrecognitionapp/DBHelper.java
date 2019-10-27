package com.example.ushnesha.activityrecognitionapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION =1;
    public DBHelper(Context context){
        super(context,"todo_db",null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //앱 설치시 한번 호출
        //테이블 생성

        String userTable = "CREATE TABLE user ("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "starting_coordinate,"+
                "other_Information)";

        String habitsTable = "CREATE TABLE habits ("+
                "_id integer primary key AUTOINCREMENT,"+
                "habit_name not null,"+
                "description,"+
                "achievement_rate)";

        sqLiteDatabase.execSQL(userTable);
        sqLiteDatabase.execSQL(habitsTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //버전 변경시마다 호출
        //스키마 변경 및 수정
        if(newVersion == DB_VERSION){
            sqLiteDatabase.execSQL("drop table user");
            sqLiteDatabase.execSQL("drop table habits");
            onCreate(sqLiteDatabase);
        }



    }
}
