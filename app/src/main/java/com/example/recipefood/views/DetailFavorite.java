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

import com.example.recipefood.model.RecipeFavorite;
import com.example.recipefood.R;
import com.squareup.picasso.Picasso;

public class DetailFavorite extends Fragment {
    RecipeFavorite recipe_favorite;
    //Khai báo các view
    ImageButton button;

    ImageView img_recipe;
    TextView title;
    TextView time_cooking;
    TextView like;
    TextView serving;
    TextView ingredient;
    TextView instructions;

    //Khai báo layout
    private ScrollView scrollView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View views = inflater.inflate(R.layout.fragment_favorite_recipe, null, false);
        button = views.findViewById(R.id.recipe_back_favorite);
        img_recipe = views.findViewById(R.id.image_recipe_favorite);
        title = views.findViewById(R.id.title_name_favorite);
        time_cooking = views.findViewById(R.id.time_cooking_favorite);
        like = views.findViewById(R.id.favorite_like);
        serving = views.findViewById(R.id.favorite_serving);
        ingredient = views.findViewById(R.id.recipe_ingredients_favorite);
        instructions = views.findViewById(R.id.recipe_instruction_favorite);
        scrollView = views.findViewById(R.id.layout_scrollview);
        Bundle bundle  = getArguments();
        if(bundle != null){
            recipe_favorite = (RecipeFavorite) bundle.get("recipe_favorite");
            if(recipe_favorite != null){
                Picasso.get().load(recipe_favorite.getImage()).into(img_recipe);
                title.setText(recipe_favorite.getName());
                time_cooking.setText(String.valueOf(recipe_favorite.getTime()));
                like.setText(String.valueOf(recipe_favorite.getLikes()));
                serving.setText(String.valueOf(recipe_favorite.getServing()));
                ingredient.setText(recipe_favorite.getIngredients());
                instructions.setText(recipe_favorite.getInstruction());
            }
        }
        scrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
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
