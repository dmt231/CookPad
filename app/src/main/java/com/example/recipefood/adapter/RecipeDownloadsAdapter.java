package com.example.recipefood.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefood.R;
import com.example.recipefood.model.DataBase.Models.RecipeFavoriteDownload;
import com.example.recipefood.model.RecipeFavorite;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDownloadsAdapter extends RecyclerView.Adapter<ViewHolderFavorite> {

    private Activity mContext;
    private List<RecipeFavoriteDownload> recipeList;
    private Detail_ClickListener_Favorite detail_clickListener_favorite;

    public RecipeDownloadsAdapter(Activity mContext, List<RecipeFavoriteDownload> recipeList, Detail_ClickListener_Favorite detail_clickListener_favorite){
        this.mContext = mContext;
        this.recipeList = recipeList;
        this.detail_clickListener_favorite = detail_clickListener_favorite;
    }

    @NonNull
    @Override
    public ViewHolderFavorite onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderFavorite(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_custom_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFavorite holder, int position) {
        RecipeFavorite dataModel = recipeList.get(position);
        holder.Ryc_TextView_title.setText(dataModel.getName());
        holder.Ryc_TextView_title.setSelected(true);
        holder.Ryc_textfavorite.setText(dataModel.getLikes() + " Likes");
        holder.time_cooking.setText(dataModel.getTime() + " Min");
        holder.Ryc_serving.setText(dataModel.getServing() + " Servings");
        Picasso.get().load(dataModel.getImage()).into(holder.Ryc_Image_food);
        holder.Ryc_Image_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detail_clickListener_favorite.OnClickRecipe(dataModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
    public interface  Detail_ClickListener_Favorite{
        void OnClickRecipe(RecipeFavorite recipeFavorite);
    }
}
