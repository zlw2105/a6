package com.example.a6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseManagement extends SQLiteOpenHelper {

    public static final String DATA_NAME = "spend.db";
    public static final String TABLE_NAME = "Spend";
    public static final String COL_NAME_ITEM = "Item";
    public static final String COL_NAME_DATE = "Date";
    public static final String COL_NAME_PRICE = "Price";

    public DataBaseManagement(Context context){
        super(context, DATA_NAME, null, 4);
    }

    // create the talbe.
    public void onCreate(SQLiteDatabase sqlDB){
        sqlDB.execSQL("CREATE TABLE " + TABLE_NAME + " (Count INTEGER PRIMARY KEY AUTOINCREMENT, Item varchar(100), Date varchar(100), Price DOUBLE)");
    }

    @Override
    // drop table.
    public void onUpgrade(SQLiteDatabase sqlDB, int older, int newer){
        sqlDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqlDB);
    }

    // add the information to the table.
    public boolean add(TransModel mo){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME_ITEM, mo.mItem);
        contentValues.put(COL_NAME_DATE, mo.mDate);
        contentValues.put(COL_NAME_PRICE, mo.mPrice);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    //use the cursor to find the data in the talbe.
    public Cursor get() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return result;
    }

    // put the information which we typed as the key to find the data in the table.
    public Cursor pullData(String A1, String A2, String Da1, String Da2) {
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        Cursor result = null;

        Double Am1 = null;
        Double Am2 = null;

        if (!A1.isEmpty()) {
            Am1 = Double.parseDouble(A1);
        }
        if (!A2.isEmpty()) {
            Am2 = Double.parseDouble(A2);
        }
        if (Am1 != null && Am2 == null && Da1.isEmpty() && Da2.isEmpty()) {
            result = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + Am1, null);
        }
        else if (Am1 == null && Am2 != null && Da1.isEmpty() && Da2.isEmpty()) {
            result = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price <= " + Am2, null);
        }
        else if (Am1 == null && Am2 == null && !Da1.isEmpty() && Da2.isEmpty()) {
            result = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Date >= '" + Da1 + "'", null);
        }
        else if (Am1 == null && Am2 == null && Da1.isEmpty() && !Da2.isEmpty()) {
            result = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Date <= '" + Da2 + "'", null);
        }
        else if (Am1 != null && Am2 != null && Da1.isEmpty() && Da2.isEmpty()) {
            result = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + Am1 + " AND Price <= " + Am2, null);
        }
        else if (Am1 == null && Am2 == null && !Da1.isEmpty() && !Da2.isEmpty()) {
            result = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Date >= '" + Da1 + "' AND Date <= '" + Da2 + "'", null);
        }
        else if (Am1 != null && Am2 == null && !Da1.isEmpty() && Da2.isEmpty()) {
            result = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + Am1 + " AND Date >= '" + Da1 + "'", null);
        }
        else if (Am1 != null && Am2 == null && Da1.isEmpty() && !Da2.isEmpty()) {
            result = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + Am1 + " AND Date <= '" + Da2 + "'", null);
        }
        else if (Am1 == null && Am2 != null && !Da1.isEmpty() && Da2.isEmpty()) {
            result = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price <= " + Am2 + " AND Date >= '" + Da1 + "'", null);
        }
        else if (Am1 == null && Am2 != null && Da1.isEmpty() &&!Da2.isEmpty()) {
            result = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price <= " + Am2 + " AND Date <= '" + Da2 + "'", null);
        }
        else if (Am1 != null && Am2 != null && !Da1.isEmpty() && Da2.isEmpty()) {
            result = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + Am1 + " AND Price <= " +
                    Am2 + " AND Date >= '" + Da1 + "'", null);
        }
        else if (Am1 != null && Am2 != null && Da1.isEmpty() && !Da2.isEmpty()) {
            result = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + Am1 + " AND Price <= " +
                    Am2 + " AND Date <= '" + Da2 + "'", null);
        }
        else if (Am1 == null && Am2 != null && !Da1.isEmpty() && !Da2.isEmpty()) {
            result = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price <= " + Am2 + " AND Date >= '" +
                    Da1 + "' AND Date <= '" + Da2 + "'", null);
        }
        else if (Am1 != null && Am2 == null && !Da1.isEmpty() && !Da2.isEmpty()) {
            result = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + Am1 + " AND Date >= '" +
                    Da1 + "' AND Date <= '" + Da2 +"'", null);
        }
        else if (Am1 != null && Am2 != null && Da1 != null && Da2 != null) {
            result = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Price >= " + Am1 + " AND Price <= " + Am2 + " AND Date >= '" +
                    Da1 + "' AND Date <= '" + Da2 + "'", null);
        }
        return result;
    }

}

