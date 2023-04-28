package com.example.recipefood.model;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Repository {
    public MutableLiveData<ArrayList<User>> getUserLiveData() {
        return userLiveData;
    }

    private MutableLiveData<ArrayList<User>> userLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<RecipeInstrument>> recipeListLiveData = new MutableLiveData<>();

    public MutableLiveData<ArrayList<RecipeInstrument>> getRecipeListLiveData() {
        return recipeListLiveData;
    }
    FirebaseFirestore firestore;
    public Repository(){
        firestore = FirebaseFirestore.getInstance();
    }


    public void getDataFromFirestore(int i1, int i2) { //(0,10)
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
    public void getDataByIngredients(int i1, int i2, String ingredient) {
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
                                if(documentSnapshot.getString("ingredients").contains(ingredient)) {
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
                                    Log.d("Object : ", name + String.valueOf(id));
                                    RecipeInstrument recipeInstrument = new RecipeInstrument(id, name, ingredients, instructions, image, likes, serving, times, sourceName, sourceUrl, spoon);
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
    public void Register(User user){
        CollectionReference ref = firestore.collection("User");
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", user.getUsername());
        userMap.put("email",user.getEmail());
        userMap.put("password", user.getPassword());

        //Lấy tất cả document trong collections "users"
        ref.get().addOnSuccessListener(queryDocumentSnapshots -> {
            long newUserId = 1;
            if (!queryDocumentSnapshots.isEmpty()) {
                // Nếu có tài liệu, tìm giá trị UserId lớn nhất
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    long userId = documentSnapshot.getLong("userId");
                    if (userId > newUserId) {
                        newUserId = userId;
                    }
                }
                // Tăng giá trị UserId lên 1 để tạo giá trị mới cho trường UserId
                newUserId++;
            }
            userMap.put("userId", newUserId);
            ref.document(user.getUsername()).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("Info : ", "Add Success");
                }
            });
        });
    }
    public void checkUser(){
        firestore.collection("User")
                  .orderBy("userId")
                  .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            ArrayList<User> listUser = new ArrayList<>();
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                String username = documentSnapshot.getString("username");
                                String email = documentSnapshot.getString("email");
                                String password = documentSnapshot.getString("password");
                                User user = new User(username, password, email);
                                Log.d("User", user.getUsername() + user.getEmail());
                                listUser.add(user);
                            }
                            userLiveData.setValue(listUser);
                        }
                        else {
                            userLiveData.setValue(new ArrayList<>());
                            Log.d("Error : " , "Not found any user");
                        }
                    }
                });
    }
    public interface OnUserExistListener {
        void onUserExist(boolean exists);
    }

    public void checkUserExist(OnUserExistListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("User");
        usersRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                listener.onUserExist(true);
            } else {
                listener.onUserExist(false);
            }
        });
    }


}
