package com.example.recipefood.model.DataBase.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.recipefood.model.RecipeFavorite;

@Entity(tableName ="RecipeFavoriteDownload" )
public class RecipeFavoriteDownload extends RecipeFavorite {
    @PrimaryKey
    private int ID;
    private String sourceUrl;
    private String spoonacularSourceUrl;

    public RecipeFavoriteDownload(int id,String name, String image, int time, int likes, int serving, String ingredients, String instruction,String sourceUrl,String spoonacularSourceUrl) {
        super(name, image, time, likes, serving, ingredients, instruction);
        this.setID(id);
        this.setSourceUrl(sourceUrl);
        this.setSpoonacularSourceUrl(spoonacularSourceUrl);
    }

    public RecipeFavoriteDownload() {
        super();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSpoonacularSourceUrl() {
        return spoonacularSourceUrl;
    }

    public void setSpoonacularSourceUrl(String spoonacularSourceUrl) {
        this.spoonacularSourceUrl = spoonacularSourceUrl;
    }
}
