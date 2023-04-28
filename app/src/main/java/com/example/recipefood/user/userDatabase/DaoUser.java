package com.example.recipefood.user.userDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DaoUser {
    @Insert
    public void InsertUser(UserLogin user);

    @Update
    public void UpdateUser(UserLogin user);

    @Delete
    public void DeleteUser(UserLogin user);

    @Query("Select * from UserLogin where isLogin = 1")
    public UserLogin GetUserLogin();

    @Query("Select * from userlogin where userId = :values")
    public UserLogin GetUserFromId(int values);
}
