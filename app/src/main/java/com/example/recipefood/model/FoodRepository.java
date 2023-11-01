package com.example.recipefood.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodRepository {
    public MutableLiveData<ArrayList<User>> getUserLiveData() {
        return userLiveData;
    }

    private MutableLiveData<ArrayList<User>> userLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<RecipeInstrument>> recipeListLiveData = new MutableLiveData<>();

    public MutableLiveData<ArrayList<RecipeInstrument>> getRecipeListLiveData() {
        return recipeListLiveData;
    }

    FirebaseFirestore fireStore;

    public FoodRepository() {
        fireStore = FirebaseFirestore.getInstance();
    }


    public void getFoodFromFireStore(int i1, int i2) { //(0,10)
        fireStore.collection("foods")
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
                                int Userid = documentSnapshot.get("userId", Integer.class);

                                Log.d("Object : ", name + String.valueOf(id));
                                RecipeInstrument recipeInstrument = new RecipeInstrument(id, name, ingredients, instructions, image, likes, serving, times, sourceName, sourceUrl, spoon, Userid);
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

    public void getFoodByIngredients(int i1, int i2, String ingredient) {
        fireStore.collection("foods")
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
                                if (documentSnapshot.getString("ingredients").contains(ingredient)) {
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
                                    int Userid = documentSnapshot.get("userId", Integer.class);

                                    Log.d("Object : ", name + String.valueOf(id));
                                    RecipeInstrument recipeInstrument = new RecipeInstrument(id, name, ingredients, instructions, image, likes, serving, times, sourceName, sourceUrl, spoon, Userid);
                                    listRecipe.add(recipeInstrument);
                                }
                            }
                            recipeListLiveData.setValue(listRecipe);

                        } else {
                            recipeListLiveData.setValue(new ArrayList<>());
                            Log.d("Info : ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    //User's Recipe
    public void haveAnyRecipe(int userid, OnExistListener listener){
        fireStore.collection("foods")
                .whereEqualTo("userId", userid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && !task.getResult().isEmpty()){
                            listener.onExist(true);
                        }else{
                            listener.onExist(false);
                        }
                    }
                });
    }


    public void getFoodByUser(int userid){
        fireStore.collection("foods")
                .whereEqualTo("userId",userid)
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
                                int Userid = documentSnapshot.get("userId", Integer.class);

                                Log.d("Object : ", name + String.valueOf(id));
                                RecipeInstrument recipeInstrument = new RecipeInstrument(id, name, ingredients, instructions, image, likes, serving, times, sourceName, sourceUrl, spoon, Userid);
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

    public void addRecipe(RecipeInstrument recipe, onSuccess addSuccess){
        CollectionReference ref = fireStore.collection("foods");
        Map<String, Object> food = new HashMap<>();
        food.put("foodImage", recipe.getImages());
        food.put("foodLikes", recipe.getLikes());
        food.put("foodName", recipe.getName());
        food.put("ingredients", recipe.getIngredients());
        food.put("instructions", recipe.getInstructions());
        food.put("serving", recipe.getServing());
        food.put("time",recipe.getTime());
        food.put("sourcefoodName", recipe.getSourceName());
        food.put("sourcefoodUrl", recipe.getSourceUrl());
        food.put("spoonacularSourceUrl", recipe.getSpoonacularSourceUrl());
        food.put("userId", recipe.getUserid());
        ref.get().addOnSuccessListener(queryDocumentSnapshots -> {
            int newFoodId = 1;
            if (!queryDocumentSnapshots.isEmpty()) {
                // Nếu có tài liệu, tìm giá trị UserId lớn nhất
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    int foodId =  documentSnapshot.get("foodId", Integer.class);
                    if (foodId > newFoodId) {
                        newFoodId = foodId;
                    }
                }
                // Tăng giá trị UserId lên 1 để tạo giá trị mới cho trường UserId
                newFoodId++;
            }
            food.put("foodId", newFoodId);
            ref.document("food" + String.valueOf(newFoodId)).set(food).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("Info : ", "Add Success");
                    addSuccess.onAddSuccess();
                }
            });
        });

    }
    public interface onSuccess {
        void onAddSuccess();
        void onUpdateSuccess();
    }

    public void updateRecipe(int foodId, String image, String name, String ingredients, String instructions,
                             int time, int serving, onSuccess success){
        CollectionReference ref = fireStore.collection("foods");
        Query query = ref.whereEqualTo("foodId", foodId);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    documentSnapshot.getReference().update("foodImage", image);
                    documentSnapshot.getReference().update("foodName", name);
                    documentSnapshot.getReference().update("ingredients", ingredients);
                    documentSnapshot.getReference().update("instructions", instructions);
                    documentSnapshot.getReference().update("time", time);
                    documentSnapshot.getReference().update("serving", serving);
                }
                success.onUpdateSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Error: ", "Fail to update");
            }
        });
    }

    public void deleteRecipe(int foodId){
        CollectionReference ref = fireStore.collection("foods");
        Query query = ref.whereEqualTo("foodId", foodId);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                    documentSnapshot.getReference().delete();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Info Delete : ", "Fail to Delete");
            }
        });
    }



    //User



    //Favorite food for User
    public void addFavoriteForUser(int Userid, int foodId, OnExistListener listener){
        CollectionReference reference = fireStore.collection("User");
        Query query = reference.whereEqualTo("userId", Userid);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    documentSnapshot.getReference().update("favorite", FieldValue.arrayUnion(foodId)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            listener.onExist(true);
                            Log.d("Update : ", "Update Successfully");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onExist(false);
                            Log.d("Fail : ", "Update Failed");
                        }
                    });
                }
            }
        });
    }
    public void removeFavoriteForUser(int userId, int foodId, OnExistListener listener) {
        CollectionReference reference = fireStore.collection("User");
        Query query = reference.whereEqualTo("userId", userId);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    documentSnapshot.getReference().update("favorite", FieldValue.arrayRemove(Integer.valueOf(foodId)))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    listener.onExist(true);
                                    Log.d("Remove", "Removed foodId " + foodId + " from favorite");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    listener.onExist(false);
                                    Log.e("Remove", "Error removing foodId " + foodId + " from favorite", e);
                                }
                            });
                }
            }
        });
    }
    public void checkFoodFavoriteExist(int userId, int foodId , OnExistListener listener){
        CollectionReference usersRef = fireStore.collection("User");
        Query query = usersRef.whereEqualTo("userId", userId).whereArrayContains("favorite", foodId);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                   listener.onExist(true);
                } else {
                    listener.onExist(false);
                }
            }
        });
    }
    public void haveAnyFavorite(int userId, OnExistListener existListener){
        fireStore.collection("User")
                .whereEqualTo("userId", userId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                List<Integer> favorite = (List<Integer>) documentSnapshot.get("favorite");
                                if(favorite.size() != 0){
                                    existListener.onExist(true);
                                    Log.d("Exists : ", "true");
                                }
                                else {
                                    existListener.onExist(false);
                                    Log.d("Exists : ", "false");
                                }
                            }
                        }
                    }
                });
    }
    public void getAllFoodFavorite(int userId){
        CollectionReference foodRef = fireStore.collection("foods");
        fireStore.collection("User")
                .whereEqualTo("userId", userId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && !task.getResult().isEmpty()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                List<Integer> favorite = (List<Integer>) documentSnapshot.get("favorite"); // 2 79 7 4 10
                                foodRef.whereIn("foodId", favorite).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                                                int Userid = documentSnapshot.get("userId", Integer.class);

                                                Log.d("Object : ", name + String.valueOf(id));
                                                RecipeInstrument recipeInstrument = new RecipeInstrument(id, name, ingredients, instructions, image, likes, serving, times, sourceName, sourceUrl, spoon, Userid);
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
                    }
                });
    }



    public interface OnExistListener {
        void onExist(boolean exists);
    }
}
