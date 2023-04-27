package com.example.recipefood.user.userModel;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DaoUser {
    @Insert
    public void InsertUser(User user);

    @Update
    public void UpdateUser(User user);

    @Delete
    public void DeleteUser(User user);

    @Query("Select * from User where isLogin = 1")
    public User GetUserLogin();
}
