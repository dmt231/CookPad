package com.example.recipefood.user.userDatabase;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class UserLogin {
    @PrimaryKey
    private int userId;
    private int isLogin;

    public UserLogin(int userId, int isLogin) {
        this.userId = userId;
        this.isLogin = isLogin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }
}
