package com.example.btckandroid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Truy vấn không trả kết quả CREATE, INSERT, UPDATE, DELETE,...
    public void QueryData(String cmd){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(cmd);
    }

    //Truy vấn trả lại kết quả: SELECT
    public Cursor GetData(String cmd){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(cmd, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
