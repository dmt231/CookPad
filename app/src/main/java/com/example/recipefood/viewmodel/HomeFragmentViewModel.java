package com.example.recipefood.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.model.firebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HomeFragmentViewModel extends ViewModel {

    private ArrayList<RecipeInstrument> recipeList;
    private firebaseDatabase database;

    public ArrayList<RecipeInstrument> getRecipeList() {
        return recipeList;
    }

    public  void GetData(int y1,int y2, OnGetResult onGetResult){
        database= new firebaseDatabase();
        database.getDataFromFirestore(y1, y2, new firebaseDatabase.FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<RecipeInstrument> listRecipe) {
                if(listRecipe!=null){
                    recipeList=listRecipe;
                    onGetResult.OnResult(recipeList);
                }
            }
        });
    }
//
    public interface OnGetResult{
        void OnResult(ArrayList<RecipeInstrument> listRecipe);
    }
}
