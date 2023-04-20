package com.example.recipefood.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefood.model.Recipe_Favorite;
import com.example.recipefood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteRecipeAdapter extends RecyclerView.Adapter<ViewHolderFavorite> {

    private Activity mContext;
    private List<Recipe_Favorite> recipeList;
    private Detail_ClickListener_Favorite detail_clickListener_favorite;

    public FavoriteRecipeAdapter(Activity mContext, List<Recipe_Favorite> recipeList, Detail_ClickListener_Favorite detail_clickListener_favorite){
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
        Recipe_Favorite dataModel = recipeList.get(position);
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
        void OnClickRecipe(Recipe_Favorite recipeFavorite);
    }
}
class ViewHolderFavorite extends RecyclerView.ViewHolder{
    CardView Ryc_CardView;
    TextView Ryc_TextView_title, Ryc_textfavorite, time_cooking, Ryc_serving;
    ImageView Ryc_Image_food;
    public ViewHolderFavorite(@NonNull View itemView) {
        super(itemView);
        Ryc_CardView = itemView.findViewById(R.id.Ryc_CardView);
        Ryc_TextView_title = itemView.findViewById(R.id.Ryc_TextView_title);
        Ryc_textfavorite = itemView.findViewById(R.id.Ryc_textfavorite);
        Ryc_Image_food = itemView.findViewById(R.id.Ryc_Image_food);
        time_cooking = itemView.findViewById(R.id.time_cooking);
        Ryc_serving = itemView.findViewById(R.id.Ryc_serving);
    }
}