package com.adairtechnology.sgsautomobile;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Android-Team1 on 2/4/2017.
 */


public class DBController1 extends SQLiteOpenHelper {
    private static final String tablename = "places";  // table name
    private static final String place = "place";  // column name
    private static final String id = "ID";  // auto generated ID column
    private static final String country = "country"; // column name
    private static final String databasename = "placesinfo"; // Dtabasename
    private static final int versioncode = 1; //versioncode of the database

    public DBController1(Context context) {
        super(context, databasename, null, versioncode);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE IF NOT EXISTS " + tablename + "(" + id + " integer primary key, " + place + " text, " + country + " text)";
        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old,
                          int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS " + tablename;
        database.execSQL(query);
        onCreate(database);
    }

    public ArrayList<HashMap<String, String>> getAllPlace() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM " + tablename;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", cursor.getString(0));
                map.put("place", cursor.getString(1));
                map.put("country", cursor.getString(2));

                wordList.add(map);
            } while (cursor.moveToNext());
        }

        // return contact list
        return wordList;
    }
}