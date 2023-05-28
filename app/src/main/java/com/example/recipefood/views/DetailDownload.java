package com.example.recipefood.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipefood.databinding.FragmentFavoriteRecipeBinding;
import com.example.recipefood.model.RecipeFavorite;
import com.example.recipefood.R;
import com.squareup.picasso.Picasso;

public class DetailDownload extends Fragment {
    private FragmentFavoriteRecipeBinding binding;
    private RecipeFavorite recipe_favorite;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteRecipeBinding.inflate(inflater, container, false);
        View views = binding.getRoot();
        Bundle bundle = getArguments();
        if (bundle != null) {
            recipe_favorite = (RecipeFavorite) bundle.get("recipe_favorite");
            if (recipe_favorite != null) {
                Picasso.get().load(recipe_favorite.getImages()).into(binding.imageRecipeFavorite);
                binding.titleNameFavorite.setText(recipe_favorite.getName());
                binding.timeCookingFavorite.setText(String.valueOf(recipe_favorite.getTime()));
                binding.favoriteLike.setText(String.valueOf(recipe_favorite.getLikes()));
                binding.favoriteServing.setText(String.valueOf(recipe_favorite.getServing()));
                binding.recipeIngredientsFavorite.setText(recipe_favorite.getIngredients());
                binding.recipeInstructionFavorite.setText(recipe_favorite.getInstructions());
            }
        }
        binding.layoutScrollview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.recipeBackFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        return views;
    }
}
