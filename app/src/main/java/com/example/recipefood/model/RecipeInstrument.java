package com.example.recipefood.model;

import java.io.Serializable;

public class RecipeInstrument implements Serializable {
    private int id;
    private String name;
    private String ingredients;
    private String instructions;
    private String images;
    private int Likes;
    private int Serving;
    private int Time;
    private String sourceName;
    private String sourceUrl;
    private String spoonacularSourceUrl;

    public int getUserid() {
        return Userid;
    }

    private int Userid;

    public RecipeInstrument(int id, String name, String ingredients, String instructions, String images, int likes, int serving, int time, String sourceName, String sourceUrl, String spoonacularSourceUrl, int Userid) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.images = images;
        Likes = likes;
        Serving = serving;
        Time = time;
        this.sourceName = sourceName;
        this.sourceUrl = sourceUrl;
        this.spoonacularSourceUrl = spoonacularSourceUrl;
        this.Userid = Userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
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
