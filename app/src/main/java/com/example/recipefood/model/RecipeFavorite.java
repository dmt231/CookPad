package com.example.recipefood.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "RecipeFavorite")
public class RecipeFavorite implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    private int id;

    private String Name;
    private String Image;
    private int Time;
    private int Likes;
    private int Serving;
    private String Ingredients;
    private String Instruction;

     public RecipeFavorite(String name, String image, int time, int likes, int serving, String ingredients, String instruction) {
        Name = name;
        Image = image;
        Time = time;
        Likes = likes;
        Serving = serving;
        Ingredients = ingredients;
        Instruction = instruction;
    }

    public RecipeFavorite() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public int getLikes() {
        return Likes;
    }

    public void setLikes(int likes) {
        Likes = likes;
    }

    public int getServing() {
        return Serving;
    }

    public void setServing(int serving) {
        Serving = serving;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public String getInstruction() {
        return Instruction;
    }

    public void setInstruction(String instruction) {
        Instruction = instruction;
    }
}
