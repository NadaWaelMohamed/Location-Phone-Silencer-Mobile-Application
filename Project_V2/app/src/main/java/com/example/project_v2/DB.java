package com.example.project_v2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.widget.Toast;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class DB extends SQLiteOpenHelper {
    public DB(Context context) {
        super(context, "Location.db", null,  1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
         DB.execSQL("create Table LocationDetails(id Number primary key , Latitude NUMBER , Longitude NUMBER  , CheckColumn TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
         DB.execSQL("drop Table if exists LocationDetails");
    }
    // Mnf3sh tkon number l2n put btshel string
    public Boolean InsertData(String id , String Latitude , String Longitude , String  CheckColumn ){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID" , id);
        contentValues.put("Latitude" , Latitude);
        contentValues.put("Longitude" , Longitude);
        contentValues.put("CheckColumn" , CheckColumn);
        long result = DB.insert("LocationDetails" , null ,contentValues);
        //table not found
        if(result == -1){
            return false;
        }
        else{
            return true;
        }



    }
    public Boolean UpdateData(String id , String Latitude , String Longitude , String  CheckColumn ){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID" , id);
        contentValues.put("Latitude" , Latitude);
        contentValues.put("Longitude" , Longitude);
        contentValues.put("CheckColumn" , CheckColumn);
        Cursor cursor = DB.rawQuery("Select * from LocationDetails where id = ?" , new String[]{id});
        if(cursor.getCount()>0) {
            // id 3shn hoa el pk
            long result = DB.update("LocationDetails", contentValues, "id = ?", new String[]{id});
            //table not found
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        else{
            return false;
        }
    }
    public Boolean DeleteData(String id){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor = DB.rawQuery("Select * from LocationDetails where id = ?" , new String[]{id});
        if(cursor.getCount()>0) {
            // id 3shn hoa el pk
            long result = DB.delete("LocationDetails", "id = ?", new String[]{id});
            //table not found
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        else{
            return false;
        }
    }
    //ale hya view data
    public Cursor GetData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor = DB.rawQuery("Select * from LocationDetails", null);
        return cursor;
    }


}
