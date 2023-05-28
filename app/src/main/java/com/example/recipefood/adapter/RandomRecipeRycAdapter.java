package com.example.recipefood.adapter;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefood.databinding.LayoutRecyclerCustomRowItemBinding;
import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeRycAdapter extends RecyclerView.Adapter<ViewHolder>{
    private List<RecipeInstrument> recipeList;
    private Detail_ClickListener detailClickListener;


    public interface  Detail_ClickListener{
        void onClickRecipe(RecipeInstrument recipe);
    }
    public RandomRecipeRycAdapter(List<RecipeInstrument> recipeList, Detail_ClickListener detail_clickListener) {
        this.recipeList = recipeList;
        this.detailClickListener = detail_clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutRecyclerCustomRowItemBinding binding = LayoutRecyclerCustomRowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Nap du lieu

        RecipeInstrument dataModel = recipeList.get(position);
        holder.binding.RycTextViewTitle.setText(dataModel.getName());
        holder.binding.RycTextViewTitle.setSelected(true);
        holder.binding.RycTextfavorite.setText(dataModel.getLikes() + " Likes");
        holder.binding.timeCooking.setText(dataModel.getTime() + " Min");
        holder.binding.RycServing.setText(dataModel.getServing() + " Servings");
        Picasso.get().load(dataModel.getImages()).into(holder.binding.RycImageFood);
        holder.binding.RycImageFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailClickListener.onClickRecipe(dataModel);
            }
        });
    }

    @Override
    public int getItemCount() {

        return recipeList.size();
    }
    public void removeItem(int position){
        recipeList.remove(position);
        notifyDataSetChanged();
    }
}

class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    public LayoutRecyclerCustomRowItemBinding binding;
    public ViewHolder(@NonNull LayoutRecyclerCustomRowItemBinding itemView) {
        super(itemView.getRoot());
        this.binding = itemView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select Option");
        contextMenu.add(getAdapterPosition(), 101,0,"Edit");
        contextMenu.add(getAdapterPosition(), 102, 1, "Delete");
    }
}
