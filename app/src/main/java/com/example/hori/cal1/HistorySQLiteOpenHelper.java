package com.example.hori.cal1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by hori on 2014/11/07.
 */
public class HistorySQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String HISTORY_TABLE = "history";
    static final String DB = "history.db";
    static final int DB_VERSION =1;
    static final String CREATE_TABLE = "create table "
            + HISTORY_TABLE
            + "(_id integer primary key autoincrement, formula text)";
    static final String DROP_TABLE = "drop table" + HISTORY_TABLE;

    public HistorySQLiteOpenHelper(Context context) {
        super(context, DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }


}
