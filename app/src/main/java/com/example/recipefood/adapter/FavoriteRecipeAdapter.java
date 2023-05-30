package com.example.recipefood.adapter;

import android.app.Activity;
import android.app.AlertDialog;
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


import com.example.recipefood.databinding.CustomFavoriteBinding;
import com.example.recipefood.databinding.CustomLayoutBinding;
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
       CustomFavoriteBinding layoutBinding = CustomFavoriteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
       return new ViewHolderFavorite(layoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFavorite holder, int position) {
        RecipeFavorite dataModel = recipeList.get(position);
        holder.binding.setRecipeFavorite(dataModel);
        Picasso.get().load(dataModel.getImages()).into(holder.binding.ImageFavorite);
        holder.binding.ImageFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detail_clickListener_favorite.onClickRecipe(dataModel);
            }
        });

        holder.binding.cardViewFavorite.setOnLongClickListener(new View.OnLongClickListener() {
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
        void onClickRecipe(RecipeFavorite recipeFavorite);
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
    public CustomFavoriteBinding binding;

    public ViewHolderFavorite(@NonNull CustomFavoriteBinding itemView) {
        super(itemView.getRoot());
        this.binding = itemView;
    }
}