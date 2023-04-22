package com.example.recipefood.model;

import java.io.Serializable;

public class RecipeFavorite implements Serializable {
    private String Name;
    private String Image;
    private int Time;

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

    private int Likes;
    private int Serving;
    private String Ingredients;

    public RecipeFavorite(String name, String image, int time, int likes, int serving, String ingredients, String instruction) {
        Name = name;
        Image = image;
        Time = time;
        Likes = likes;
        Serving = serving;
        Ingredients = ingredients;
        Instruction = instruction;
    }

    private String Instruction;
}
