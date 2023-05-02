package com.example.recipefood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeRycAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<RecipeInstrument> recipeList;

    private Detail_ClickListener DetailClickListener;


    public interface Detail_ClickListener {
        void OnClickRecipe(RecipeInstrument recipe);
    }

    public RandomRecipeRycAdapter(List<RecipeInstrument> recipeList, Detail_ClickListener detail_clickListener) {
        this.recipeList = recipeList;
        this.DetailClickListener = detail_clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_custom_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Nap du lieu

        RecipeInstrument dataModel = recipeList.get(position);
        holder.Ryc_TextView_title.setText(dataModel.getName());
        holder.Ryc_TextView_title.setSelected(true);
        holder.Ryc_textFavorite.setText(dataModel.getLikes() + " Likes");
        holder.time_cooking.setText(dataModel.getTime() + " Min");
        holder.Ryc_serving.setText(dataModel.getServing() + " Servings");
        Picasso.get().load(dataModel.getImages()).into(holder.Ryc_Image_food);
        holder.Ryc_Image_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailClickListener.OnClickRecipe(dataModel);
            }
        });
    }

    @Override
    public int getItemCount() {

        return recipeList.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    CardView Ryc_CardView;
    TextView Ryc_TextView_title, Ryc_textFavorite, time_cooking, Ryc_serving;
    ImageView Ryc_Image_food;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        Ryc_CardView = itemView.findViewById(R.id.Ryc_CardView);
        Ryc_TextView_title = itemView.findViewById(R.id.Ryc_TextView_title);
        Ryc_textFavorite = itemView.findViewById(R.id.Ryc_textfavorite);
        Ryc_Image_food = itemView.findViewById(R.id.Ryc_Image_food);
        time_cooking = itemView.findViewById(R.id.time_cooking);
        Ryc_serving = itemView.findViewById(R.id.Ryc_serving);
    }
}
