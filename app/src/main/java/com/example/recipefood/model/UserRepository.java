package com.example.recipefood.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    public MutableLiveData<ArrayList<User>> getUserLiveData() {
        return userLiveData;
    }

    private MutableLiveData<ArrayList<User>> userLiveData = new MutableLiveData<>();

    FirebaseFirestore fireStore;

    public UserRepository() {
        fireStore = FirebaseFirestore.getInstance();
    }

    public void Register(String username, String email, String pasword) {
        CollectionReference ref = fireStore.collection("User");
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", username);
        userMap.put("email", email);
        userMap.put("password", pasword);

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
            List<Integer> favorite = new ArrayList<>();
            userMap.put("favorite", favorite);
            ref.document(username).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("Info : ", "Add Success");
                }
            });
        });
    }
    public void checkUser() {
        fireStore.collection("User")
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
                                long id = documentSnapshot.get("userId", Long.class);
                                User user = new User(username, password, email, id);
                                Log.d("User", user.getUsername() + user.getEmail() + user.getUserId());
                                listUser.add(user);
                            }
                            userLiveData.setValue(listUser);
                        } else {
                            userLiveData.setValue(new ArrayList<>());
                            Log.d("Error : ", "Not found any user");
                        }
                    }
                });
    }
    public void getUserLogin(long id){ //For UserAccount
        fireStore.collection("User")
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
                                long idUser = documentSnapshot.get("userId", Long.class);
                                if(idUser == id) {
                                    User user = new User(username, password, email, idUser);
                                    Log.d("User", user.getUsername() + user.getEmail() + user.getUserId());
                                    listUser.add(user);
                                }
                            }
                            userLiveData.setValue(listUser);
                        } else {
                            userLiveData.setValue(new ArrayList<>());
                            Log.d("Error : ", "Not found any user");
                        }
                    }
                });
    }
    public interface OnExistListener {
        void onExist(boolean exists);
    }

    public void checkUserExist(FoodRepository.OnExistListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("User");
        usersRef.get().addOnCompleteListener((Task<QuerySnapshot> task) -> {
            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                listener.onExist(true);
            } else {
                listener.onExist(false);
            }
        });
    }
}
