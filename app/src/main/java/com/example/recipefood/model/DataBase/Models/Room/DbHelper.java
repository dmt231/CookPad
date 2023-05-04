package com.example.recipefood.model.DataBase.Models.Room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.recipefood.model.DataBase.Models.RecipeFavoriteDownload;

@Database(entities = {RecipeFavoriteDownload.class},version = 2)
public abstract class DbHelper extends RoomDatabase {
    //them db
    public static  final  String NAME_DATA_BASE ="RecipeFavorite.db";
    public  static  DbHelper instance;

    public static Migration migration=new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
           database.execSQL("ALTER TABLE RecipeFavoriteDownload ADD sourceUrl  Text ");
           database.execSQL("ALTER TABLE RecipeFavoriteDownload ADD spoonacularSourceUrl  Text ");
        }
    };

    public  static synchronized DbHelper getInstance(Context context){
      if(instance==null){
          instance= Room.databaseBuilder(context,DbHelper.class,NAME_DATA_BASE)
                  .allowMainThreadQueries()
                  .addMigrations(migration)
                  .build();
      }
      return instance;
    };
    public  abstract RecipeFavoriteDao recipeFavoriteDao();
}
