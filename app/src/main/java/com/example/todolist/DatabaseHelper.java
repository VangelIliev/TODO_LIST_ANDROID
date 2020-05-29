package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todolist.model.Info;
import com.example.todolist.model.User;
import com.example.todolist.model.UserInfo;
import com.example.todolist.util.Utils;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ToDo1.db";
    public static final String USER_TABLE = "User_Table";
    public static final String USER_INFO_TABLE = "User_Info";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Username";
    public static final String COL_3 = "Password";
    public static final String COL_4 = "Email";
    public static final String TO_DO_DATA = "ToDoData";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_TABLE +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,PASSWORD TEXT,EMAIL TEXT)" );
        db.execSQL("CREATE TABLE " + USER_INFO_TABLE +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT, INFO TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_INFO_TABLE);

        onCreate(db);
    }

    public boolean insertData(String username,String password,String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,username);
        contentValues.put(COL_3,password);
        contentValues.put(COL_4,email);

         long result = db.insert(USER_TABLE,null,contentValues);

         if (result == -1)
         {
             return false;
         }
         else
         {
             return true;
         }
    }
    public boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase() ;
        Cursor cursor =  db.rawQuery("SELECT * FROM USER_TABLE WHERE email = ?",new String[]{email});
        if (cursor.getCount() > 0) return false;
        else  return true;

    }
public boolean checkUsername(String username){
        SQLiteDatabase db = this.getReadableDatabase() ;
        Cursor cursor =  db.rawQuery("SELECT * FROM USER_TABLE WHERE username = ?",new String[]{username});
        if (cursor.getCount() > 0) return false;
        else  return true;

}
public Boolean usernamePassword(String username,String password)
{
    SQLiteDatabase mydb = this.getReadableDatabase();
    Cursor cursor = mydb.rawQuery("SELECT * FROM USER_TABLE WHERE username = ? AND password = ?",new String[]{username,password});
    if (cursor.getCount() > 0) return true;
    else return false;
}
    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " +USER_TABLE,null);
        return res;
    }
    public boolean UpdateData(String id,String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3,password);
        db.update("TABLE_NAME",contentValues,"ID = ?",new String[] {id});
        return true;
    }

    public int DeleteData(String username){
        if (username != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(USER_TABLE, "username = ?", new String[]{username});
        }

        return 0;
    }

    public UserInfo getUserInfo(String username) {
        UserInfo userInfo = new UserInfo();

        if (username != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM USER_INFO WHERE username = ?",new String[]{username});
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(0);
                    String data = cursor.getString(2);
                    userInfo.addData(data, id);
                }
            }
        }

        return userInfo;
    }

    public void addUserItem(String itemEntered) {

        if (itemEntered != null) {
            User user = (User) Session.getInstance().getData().get(Utils.LOGGED_IN_USERNAME);
            if (user != null) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(COL_2, user.getUsername());
                contentValues.put("INFO", itemEntered);

                long id = db.insert(USER_INFO_TABLE, null, contentValues);
                user.getUserInfo().addData(itemEntered, id);
            }
        }
    }

    public void deleteData(long infoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_INFO_TABLE, "id = ?", new String[]{String.valueOf(infoId)});
    }
}
