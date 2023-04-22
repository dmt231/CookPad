package com.example.recipefood.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.model.Repository;

import java.util.ArrayList;

public class HomeFragmentViewModel extends ViewModel {
    public MutableLiveData<ArrayList<RecipeInstrument>> recipeListLiveData;
    private Repository database;

    public LiveData<ArrayList<RecipeInstrument>> getRecipeListLiveData(int i1, int i2) {

        recipeListLiveData = new MutableLiveData<>(); // 0 : null
        loadData(i1, i2);
        return recipeListLiveData;
    }

    private void loadData(int i1, int i2) {
        database = new Repository();
        database.getRecipeListLiveData().observeForever(new Observer<ArrayList<RecipeInstrument>>() {
            @Override
            public void onChanged(ArrayList<RecipeInstrument> recipeInstruments) {
                if (recipeInstruments != null && !recipeInstruments.isEmpty()) {
                    Log.d("Thread: ", Thread.currentThread().getName());
                    recipeListLiveData.setValue(recipeInstruments); //0 -> 10
                }
            }
        });
        database.getDataFromFirestore(i1, i2);
    }
}
