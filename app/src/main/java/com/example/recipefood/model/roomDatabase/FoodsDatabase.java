package com.example.recipefood.model.roomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.recipefood.model.RecipeFavorite;

@Database(entities = {RecipeFavorite.class}, version = 1)
public abstract class FoodsDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "Foods.db";
    public static FoodsDatabase instance;
    public static synchronized FoodsDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, FoodsDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract recipeDao recipeDao();
}
