package com.example.recipefood.model.roomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.recipefood.model.RecipeFavorite;

import java.util.List;

@Dao
public interface recipeDao {
    @Insert
    void insertFavorite(RecipeFavorite food);

    @Delete
    void deleteFavorite(RecipeFavorite food);

    @Query("Select * from RecipeFavorite")
    List<RecipeFavorite> getFoods();

    @Update
    void updateFoods(RecipeFavorite food);

    @Query("Select * from RecipeFavorite where id = :currentId")
    RecipeFavorite getFoodsByID(int currentId);
}
