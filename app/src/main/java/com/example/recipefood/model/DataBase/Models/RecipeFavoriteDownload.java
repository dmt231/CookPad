package com.example.recipefood.model.DataBase.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.recipefood.model.RecipeFavorite;

@Entity(tableName ="RecipeFavoriteDownload" )
public class RecipeFavoriteDownload extends RecipeFavorite {
    @PrimaryKey(autoGenerate = true)
    private int ID;

    public RecipeFavoriteDownload(String name, String image, int time, int likes, int serving, String ingredients, String instruction) {
        super(name, image, time, likes, serving, ingredients, instruction);
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
}
