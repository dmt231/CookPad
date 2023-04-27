package com.example.recipefood.model.DataBase.Models.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.recipefood.model.DataBase.Models.RecipeFavoriteDownload;
import java.util.List;

@Dao
public interface RecipeFavoriteDao {
   @Insert
    public  void insert(RecipeFavoriteDownload recipe_favorite);

    @Query("DELETE  FROM RecipeFavoriteDownload WHERE RecipeFavoriteDownload.Name=:name ")
    public  void deleteRecipeByName(String name);

    @Query("SELECT * FROM RecipeFavoriteDownload")
    public LiveData<List<RecipeFavoriteDownload>> getAllRecipe();

    @Query("DELETE  FROM RecipeFavoriteDownload ")
    public  void  deleteAll();

    @Query("SELECT * FROM RecipeFavoriteDownload WHERE RecipeFavoriteDownload.Name=:name")
    public List<RecipeFavoriteDownload> getRecipeByName(String name);

    @Query("SELECT * FROM RecipeFavoriteDownload")
    public List<RecipeFavoriteDownload> getAllNormal();

}
