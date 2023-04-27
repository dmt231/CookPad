package com.example.recipefood.model.DataBase.Models.Room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.recipefood.model.DataBase.Models.RecipeFavoriteDownload;

@Database(entities = {RecipeFavoriteDownload.class},version = 1)
public abstract class DbHelper extends RoomDatabase {
    public static  final  String NAME_DATA_BASE ="RecipeFavorite.db";
    public  static  DbHelper instance;

    public  static synchronized DbHelper getInstance(Context context){
      if(instance==null){
          instance= Room.databaseBuilder(context,DbHelper.class,NAME_DATA_BASE)
                  .allowMainThreadQueries()
                  .build();
      }
      return instance;
    };
    public  abstract RecipeFavoriteDao recipeFavoriteDao();
}
