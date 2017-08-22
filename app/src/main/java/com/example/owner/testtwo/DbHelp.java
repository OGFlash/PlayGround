package com.example.owner.testtwo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Owner on 8/16/2017.
 */

public class DbHelp extends SQLiteOpenHelper {

    private static final String DB_NAME = "frank";
    private static final int DB_VERSION = 1;

    public DbHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DbHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE WHATEVER ("
        + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
        + "NAME TEXT, "
        + "DESCRIPTION TEXT, "
        + "AGE INTEGER);");

        insertWhatever(sqLiteDatabase, "RedHot", "Best Hot Sauce", 3);




    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    private static void insertWhatever(SQLiteDatabase sqLiteDatabase, String name, String description, Integer Age){

        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("DESCRIPTION", description);
        contentValues.put("AGE", Age);

        sqLiteDatabase.insert("WHATEVER", null, contentValues);


        contentValues.put("DESCRIPTION", "Franks");
        sqLiteDatabase.update("WHATEVER", contentValues, "NAME = ?", new String[] {"RedHot"});



        /************* EXAMPLE QUERY **************/
        Cursor cursor = sqLiteDatabase.query("WHATEVER",
                                            new String[] { "NAME", "DESCRIPTION"},
                                            "NAME = ?",
                                            new String[]{"RedHot"},
                                            null, null, null);
        //NOTE TRY CATCH. SQL and CURSOR CLOSE

    }


}
