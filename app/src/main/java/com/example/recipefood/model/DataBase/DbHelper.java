package com.example.recipefood.model.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.recipefood.model.DataBase.DAO.RecipeDAO;
import com.example.recipefood.model.RecipeFavorite;

@Database(entities = {RecipeFavorite.class},version = 1)
public abstract class DbHelper extends RoomDatabase {

    private static final String DATABASE_NAME="DataRecipe";
    private  static DbHelper instance ;


    public  static synchronized DbHelper getInstance(Context context){
        if(instance==null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), DbHelper.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public  abstract RecipeDAO recipeDAO();


}

