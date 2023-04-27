package com.example.recipefood.model.DataBase.Models;
import android.app.Application;
import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.recipefood.model.DataBase.Models.Room.DbHelper;
import java.util.List;

public class RepositoryRoom {
    private  LiveData<List<RecipeFavoriteDownload>> listMutableLiveData;
    private  Context mContext;

    public RepositoryRoom(Application context) {
         this.mContext=context.getApplicationContext();
         listMutableLiveData= DbHelper.getInstance(mContext.getApplicationContext()).recipeFavoriteDao().getAllRecipe();
    }

    public  LiveData<List<RecipeFavoriteDownload>> getAllRecipeFavoriteDownload(){
        return listMutableLiveData;
    }


    public  void  insertRecipeFavoriteDownload(RecipeFavoriteDownload recipeFavorite){
        DbHelper.getInstance(mContext).recipeFavoriteDao().insert(recipeFavorite);
    }
    public  List<RecipeFavoriteDownload> getRecipeByName(String Name){
      return   DbHelper.getInstance(mContext).recipeFavoriteDao().getRecipeByName(Name);
    }
    public  List<RecipeFavoriteDownload> getAllNormal(){
        return DbHelper.getInstance(mContext).recipeFavoriteDao().getAllNormal();
    }
    public  void DeleteByName(String name){
        DbHelper.getInstance(mContext).recipeFavoriteDao().deleteRecipeByName(name);
    }
}
