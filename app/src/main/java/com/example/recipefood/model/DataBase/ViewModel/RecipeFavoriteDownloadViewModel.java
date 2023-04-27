package com.example.recipefood.model.DataBase.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.recipefood.model.DataBase.Models.RecipeFavoriteDownload;
import com.example.recipefood.model.DataBase.Models.RepositoryRoom;

import java.util.List;

public class RecipeFavoriteDownloadViewModel extends AndroidViewModel {
    private LiveData<List<RecipeFavoriteDownload>> mListLiveData;
    RepositoryRoom repositoryRoom;
    public RecipeFavoriteDownloadViewModel(@NonNull Application application) {
        super(application);
        repositoryRoom=new RepositoryRoom(application);
        this.mListLiveData=repositoryRoom.getAllRecipeFavoriteDownload();
    }
    public  LiveData<List<RecipeFavoriteDownload>> getAllRecipeFavorite(){
        return this.mListLiveData;
    }
    public List<RecipeFavoriteDownload> getRecipeByName(String name){
      return repositoryRoom.getRecipeByName(name);
    };

    public void insertRecipeFavorite(String name, String images, int time, int likes, int serving, String result, String result_2) {
        RecipeFavoriteDownload download=new RecipeFavoriteDownload(name,images,time,likes,serving,result,result_2);
        repositoryRoom.insertRecipeFavoriteDownload(download);
    }
    public List<RecipeFavoriteDownload> getRecipeFavorite(){
        return repositoryRoom.getAllNormal();
    }
    public  void DeleteByName(String Name){
        repositoryRoom.DeleteByName(Name);
    }
}
