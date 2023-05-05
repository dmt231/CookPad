package com.example.recipefood.home;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.model.Repository;

import java.util.ArrayList;

public class HomeFragmentViewModel extends ViewModel {
    public MutableLiveData<ArrayList<RecipeInstrument>> recipeListLiveData;

    public MutableLiveData<ArrayList<RecipeInstrument>> recipeListByUser;

    public MutableLiveData<ArrayList<RecipeInstrument>> favoriteList;
    private Repository database;

    public MutableLiveData<ArrayList<RecipeInstrument>> getFavoriteList(int userId) {
        favoriteList = new MutableLiveData<>();
        database = new Repository();
        loadFavorite(userId);
        return favoriteList;
    }
    public void loadFavorite(int userId){
        database.getRecipeListLiveData().observeForever(new Observer<ArrayList<RecipeInstrument>>() {
            @Override
            public void onChanged(ArrayList<RecipeInstrument> recipeInstruments) {
                if(!recipeInstruments.isEmpty() && recipeInstruments != null){
                    favoriteList.setValue(recipeInstruments);
                }
            }
        });
        database.getAllFoodFavorite(userId);
    }



    public MutableLiveData<ArrayList<RecipeInstrument>> getRecipeListByUser(int userId) {
        recipeListByUser = new MutableLiveData<>();
        database = new Repository();
        loadDataByUser(userId);
        return recipeListByUser;
    }

    public void loadDataByUser(int userId){
        database.getRecipeListLiveData().observeForever(new Observer<ArrayList<RecipeInstrument>>() {
            @Override
            public void onChanged(ArrayList<RecipeInstrument> recipeInstruments) {
                if (recipeInstruments != null && !recipeInstruments.isEmpty()) {
                    recipeListByUser.setValue(recipeInstruments);
                }
            }
        });
        database.getFoodByUser(userId);
    }


    public LiveData<ArrayList<RecipeInstrument>> getRecipeListLiveData(int i1, int i2) {

        recipeListLiveData = new MutableLiveData<>();
        database = new Repository();
        loadData(i1, i2);
        return recipeListLiveData;
    }


    private void loadData(int i1, int i2) {
        database.getRecipeListLiveData().observeForever(new Observer<ArrayList<RecipeInstrument>>() {
            @Override
            public void onChanged(ArrayList<RecipeInstrument> recipeInstruments) {
                if (recipeInstruments != null && !recipeInstruments.isEmpty()) {
                    recipeListLiveData.setValue(recipeInstruments);
                }
            }
        });
        database.getFoodFromFireStore(i1, i2);
    }


}
