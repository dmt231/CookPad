package com.example.recipefood.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.model.FoodRepository;

import java.util.ArrayList;

public class SearchFragmentViewModel extends ViewModel {
    public MutableLiveData<ArrayList<RecipeInstrument>> recipeListLiveData;
    private FoodRepository database;


    public LiveData<ArrayList<RecipeInstrument>> getRecipeListLiveData(int i1, int i2, String ingredient) {

        recipeListLiveData = new MutableLiveData<>();
        database = new FoodRepository();
        loadData(i1, i2, ingredient);
        return recipeListLiveData;
    }


    private void loadData(int i1, int i2, String ingredient) {
        database.getRecipeListLiveData().observeForever(new Observer<ArrayList<RecipeInstrument>>() {
            @Override
            public void onChanged(ArrayList<RecipeInstrument> recipeInstruments) {
                if (recipeInstruments != null && !recipeInstruments.isEmpty()) {
                    recipeListLiveData.setValue(recipeInstruments);
                }
            }
        });
        database.getFoodByIngredients(i1, i2, ingredient);
    }
}
