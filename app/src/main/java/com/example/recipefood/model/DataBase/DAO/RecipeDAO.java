package com.example.recipefood.model.DataBase.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.recipefood.model.RecipeFavorite;

import java.util.List;

@Dao
public interface RecipeDAO {
    @Insert
    public  void insert(RecipeFavorite recipe_favorite);

    @Query("DELETE  FROM RecipeFavorite WHERE RecipeFavorite.Name=:name ")
    public  void deleteRecipeByName(String name);

    @Query("SELECT * FROM RecipeFavorite")
    public List<RecipeFavorite> getAllRecipe();

    @Query("DELETE  FROM RecipeFavorite ")
    public  void  deleteAll();

    @Query("SELECT * FROM RecipeFavorite WHERE RecipeFavorite.Name=:name")
    public List<RecipeFavorite> getRecipeByName(String name);

}
