package com.example.recipefood.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class firebaseDatabase {
    private MutableLiveData<ArrayList<RecipeInstrument>> recipeListLiveData = new MutableLiveData<>();

    public MutableLiveData<ArrayList<RecipeInstrument>> getRecipeListLiveData() {
        return recipeListLiveData;
    }
    FirebaseFirestore firestore;
    public firebaseDatabase(){
        firestore = FirebaseFirestore.getInstance();
    }


    public void getDataFromFirestore(int i1, int i2) { //(0,10)
        String food = "food";
        firestore.collection("foods")
                .orderBy("foodId")
                .whereGreaterThanOrEqualTo("foodId", i1)
                .whereLessThan("foodId", i2)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<RecipeInstrument> listRecipe = new ArrayList<>();
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                int id = documentSnapshot.get("foodId", Integer.class);
                                String name = documentSnapshot.getString("foodName");
                                String ingredients = documentSnapshot.getString("ingredients");
                                String instructions = documentSnapshot.getString("instructions");
                                String image = documentSnapshot.getString("foodImage");
                                int times = documentSnapshot.get("time", Integer.class);
                                int likes = documentSnapshot.get("foodLikes", Integer.class);
                                int serving = documentSnapshot.get("serving", Integer.class);
                                String sourceName = documentSnapshot.getString("sourcefoodName");
                                String sourceUrl = documentSnapshot.getString("sourcefoodUrl");
                                String spoon = documentSnapshot.getString("spoonacularSourceUrl");
                                Log.d("Object : " ,name + String.valueOf(id));
                                RecipeInstrument recipeInstrument = new RecipeInstrument(id, name, ingredients, instructions, image, likes, serving, times, sourceName, sourceUrl, spoon);
                                listRecipe.add(recipeInstrument);
                            }
                            recipeListLiveData.setValue(listRecipe);

                        } else {
                            recipeListLiveData.setValue(new ArrayList<>());
                            Log.d("Info : ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
