package com.example.recipefood.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefood.model.RecipeFavorite;
import com.example.recipefood.R;
import com.example.recipefood.model.roomDatabase.FoodsDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteRecipeAdapter extends RecyclerView.Adapter<ViewHolderFavorite> {

    private Activity mContext;
    private List<RecipeFavorite> recipeList;
    private Detail_ClickListener_Favorite detail_clickListener_favorite;
    AlertDialog.Builder dialog_builder;

    public FavoriteRecipeAdapter(Activity mContext, List<RecipeFavorite> foods, Detail_ClickListener_Favorite detail_clickListener_favorite) {
        this.mContext = mContext;
        this.recipeList = foods;
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
        Picasso.get().load(dataModel.getImages()).into(holder.Ryc_Image_food);
        holder.Ryc_Image_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detail_clickListener_favorite.OnClickRecipe(dataModel);
            }
        });

        holder.Ryc_CardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dialog_builder = new AlertDialog.Builder(v.getContext());
                dialog_builder.setMessage("Do you want to remove this recipe")
                        .setTitle("Alert!")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            FoodsDatabase.getInstance(v.getContext()).recipeDao().deleteFavorite(dataModel);
                            recipeList.remove(dataModel);
                            notifyDataSetChanged();
                            customToast("Delete Successfully");
                        })
                        .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel())
                        .show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public interface Detail_ClickListener_Favorite {
        void OnClickRecipe(RecipeFavorite recipeFavorite);
    }
    public void customToast(String message) {
        Toast toast = new Toast(mContext);
        LayoutInflater inflater = mContext.getLayoutInflater();
        View view_inflate = inflater.inflate(R.layout.layout_custom_toast, mContext.findViewById(R.id.custom_toast));
        TextView text_message = view_inflate.findViewById(R.id.text_toast);
        text_message.setText(message);
        toast.setView(view_inflate);
        toast.setGravity(Gravity.BOTTOM, 0, 25);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}

class ViewHolderFavorite extends RecyclerView.ViewHolder {
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