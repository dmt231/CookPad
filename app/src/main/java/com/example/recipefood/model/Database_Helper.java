package com.example.recipefood.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database_Helper extends SQLiteOpenHelper {
    private static final String database_name = "recipe.db";
    public Database_Helper(@Nullable Context context) {
        super(context, database_name, null, 1);
    }
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Recipe(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Name TEXT, Image TEXT, " +
                "Time INTEGER, Likes INTEGER, Servings INTEGER, Ingredients TEXT, Instruction TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop TABLE IF EXISTS Recipe");
    }
    public boolean insertData(String Name, String Image, int Time, int likes, int Serving,
        String ingredient, String instruction ){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", Name);
        contentValues.put("Image",Image);
        contentValues.put("Time", Time);
        contentValues.put("Likes", likes);
        contentValues.put("Servings", Serving);
        contentValues.put("Ingredients", ingredient);
        contentValues.put("Instruction", instruction);
        long result = database.insert("Recipe", null,contentValues);
        if(result == -1){
            return false;
        }return true;
    }
    public boolean checkName(String name){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select Name from Recipe where Name = ?", new String[]{name});
        if(cursor.getCount()>0){
            return true;
        }
        return false;
    }
    public void delete(String Name){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("Recipe", "Name=?", new String[]{Name});
    }
}
