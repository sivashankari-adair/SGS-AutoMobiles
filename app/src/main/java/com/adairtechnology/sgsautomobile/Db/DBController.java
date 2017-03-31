package com.adairtechnology.sgsautomobile.Db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.adairtechnology.sgsautomobile.Models.Godown;
import com.adairtechnology.sgsautomobile.Models.Item;
import com.adairtechnology.sgsautomobile.Models.ListItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Android-Team1 on 2/4/2017.
 */

public class DBController extends SQLiteOpenHelper {
    private static final String tablename = "items";  // table name
    private static final String name = "name";  // column name
    private static final String id = "ID";  // auto generated ID column
    private static final String itemcode = "itemcode";
    private static final String quantity = "quantity";// column name
    private static final String databasename = "iteminfo"; // Dtabasename
    private static final int versioncode = 1; //versioncode of the database

    private static final String VendorTable = "tableofvendor";
    private static final String PartyName = "nameofparty";
    private static final String BillNo = "billNoofparty";
    private static final String Date = "dateofparty";
    private static final String GowdnCode ="vendorgodowncode";


    public DBController(Context context) {
        super(context, databasename, null, versioncode);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query,vendorQuery;
        query = "CREATE TABLE IF NOT EXISTS " + tablename + "(" + id + " integer primary key, "
                                                                + name + " text, "
                                                                + quantity + " text, "
                                                                + itemcode + " text)";

        vendorQuery = "CREATE TABLE IF NOT EXISTS " + VendorTable + "(" + PartyName + " TEXT, " + BillNo + " TEXT, " + Date + " INTEGER ," + GowdnCode + " TEXT)";
        database.execSQL(query);
        database.execSQL(vendorQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old,
                          int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS " + tablename;
        database.execSQL(query);
        onCreate(database);

    }


    public void delete(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" delete from " +VendorTable);
    }
    public ArrayList<HashMap<String, String>> getNewVendor() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM " + VendorTable;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put(PartyName, cursor.getString(0));
                map.put(BillNo, cursor.getString(1));
                map.put(Date, cursor.getString(2));
                map.put(GowdnCode,cursor.getString(3));
                wordList.add(map);
                System.out.println("The gowdown :" +String.valueOf(map));
                System.out.println("The gowdown1 :" +map);
                System.out.println("The gowdown :" +map.toString());

                System.out.println("DBHelper: 0" + PartyName);
                System.out.println("DBHelper: 1" + BillNo);
                System.out.println("DBHelper 2" + Date);
                System.out.println("DBHelper 2" + GowdnCode);

                System.out.println("The gowdown :" +String.valueOf(wordList));
                System.out.println("The gowdown1 :" +wordList);
                System.out.println("The gowdown :" +wordList.toString());
            } while (cursor.moveToNext());
        }

        // return contact list
        return wordList;
    }
    public ArrayList<Godown> getVendor() {
        String selectqurey = "SELECT * FROM " + VendorTable;
        ArrayList<Godown> vendorInfo = new ArrayList<Godown>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(selectqurey, null);
        if (c != null) {
            while (c.moveToNext()) {
                String name = c.getString(c.getColumnIndex(PartyName));
                String code = c.getString(c.getColumnIndex(GowdnCode));
                String billno = c.getString(c.getColumnIndex(BillNo));
                String date = c.getString(c.getColumnIndex(Date));

                Godown godown = new Godown();
                godown.setPartyName(name);
                godown.setGcode(code);
                godown.setPartyBillNo(billno);
                godown.setPartyDate(date);

                System.out.println("The gowdown :" +String.valueOf(godown));
                System.out.println("The gowdown1 :" +godown);
                System.out.println("The gowdown :" +godown.toString());

                System.out.println("DBHelper: " + name);
                System.out.println("DBHelper: " + code);
                System.out.println("DBHelper " + billno);

                vendorInfo.add(godown);
                System.out.println("The gowdown :" +String.valueOf(vendorInfo));
                System.out.println("The gowdown1 :" +vendorInfo);
                System.out.println("The gowdown :" +vendorInfo.toString());


            }
        }
        database.close();
        return vendorInfo;
    }
    /* Method for fetching record from Database (For all items into cardview or grid view using pojo class)*/
    public ArrayList<Item> getAllEmployee() {
        String selectQuery = "SELECT  * FROM " + tablename;
        ArrayList<Item> employees = new ArrayList<Item>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);
        if (c != null) {
            while (c.moveToNext()) {
                String code1 = c.getString(c.getColumnIndex(id));
                String name1 = c.getString(c.getColumnIndex(name));
                String quantity1 = c.getString(c.getColumnIndex(quantity));
                String itemcode1 = c.getString(c.getColumnIndex(itemcode));

                Item emp = new Item();
                emp.setName(name1);
                emp.setQty(quantity1);
                emp.setItemcode(itemcode1);

                Log.v("DBHelper: ", "Name: " + name1);
                Log.v("DBHelper: ", "Code: " + code1);
                Log.v("DBHelper: ", "Email: " + quantity1);

                employees.add(emp);
            }
        }
        database.close();
        return employees;

    }


    /* Method for fetching record from Database (For all items into cardview or grid view using pojo class)*/
    public ArrayList<ListItem> getAllListItem() {
        String selectQuery = "SELECT  * FROM " + tablename;
        ArrayList<ListItem> employees = new ArrayList<ListItem>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);
        if (c != null) {
            while (c.moveToNext()) {
                String code1 = c.getString(c.getColumnIndex(id));
                String name1 = c.getString(c.getColumnIndex(name));
                String quantity1 = c.getString(c.getColumnIndex(quantity));
                String itemcode1 = c.getString(c.getColumnIndex(itemcode));

                ListItem emp = new ListItem();
                emp.setItemName(name1);
                emp.setIteQuty(quantity1);
                emp.setItemCode(itemcode1);

                Log.v("DBHelper: ", "Name: " + name1);
                Log.v("DBHelper: ", "Code: " + code1);
                Log.v("DBHelper: ", "Email: " + quantity1);

                employees.add(emp);
            }
        }
        database.close();
        return employees;

    }


//(get all items into listview(like single row)--listview)
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
                map.put("name", cursor.getString(1));
                map.put("quantity", cursor.getString(2));
                map.put("itemcode", cursor.getString(3));

                wordList.add(map);
            } while (cursor.moveToNext());
        }
        // return contact list
        return wordList;
    }
/*
    public ArrayList<String> getAllPlace() {
        ArrayList<String> wordList;
        wordList = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + VendorTable;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", cursor.getString(0));
                map.put("name", cursor.getString(1));
                map.put("quantity", cursor.getString(2));
                map.put("itemcode", cursor.getString(3));

                wordList.add(String.valueOf(map));
            } while (cursor.moveToNext());
        }
        // return contact list
        return wordList;
    }
*/



}