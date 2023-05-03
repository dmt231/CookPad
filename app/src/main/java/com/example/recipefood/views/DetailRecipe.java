package com.example.recipefood.views;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipefood.model.RecipeFavorite;
import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.R;
import com.example.recipefood.model.roomDatabase.FoodsDatabase;
import com.squareup.picasso.Picasso;

public class DetailRecipe extends Fragment {

    Activity mactivity;
    RecipeInstrument recipe;
    //Khai báo các view
    ImageButton button;

    ImageView button_Download;
    ImageView img_recipe;
    TextView title;
    TextView time_cooking;
    TextView like;
    TextView serving;
    TextView ingredient;
    TextView instructions;


    //Khai báo listString để lưu danh sách nguyên liệu
    String result = "";
    String result_2 = "";
    //Khai báo layout
    private ScrollView layout_contraint;

    //Khai báo Database để lưu trữ dữ liệu


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View views = inflater.inflate(R.layout.fragment_detail__recipe, container, false);
        //Ánh xạ
        mactivity = getActivity();
        layout_contraint = views.findViewById(R.id.layout_constraint);
        img_recipe = views.findViewById(R.id.image_recipe);
        title = views.findViewById(R.id.title_name);
        time_cooking = views.findViewById(R.id.time_cooking);
        like = views.findViewById(R.id.textfavorite);
        serving = views.findViewById(R.id.serving);
        title = views.findViewById(R.id.title_name);
        ingredient = views.findViewById(R.id.recipe_ingredients);
        instructions = views.findViewById(R.id.recipe_instruction);
        button = (ImageButton) views.findViewById(R.id.recipe_back);
        button_Download = (ImageButton) views.findViewById(R.id.download_recipe);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }

            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            recipe = (RecipeInstrument) bundle.get("recipe");
            if (recipe != null) {
                Picasso.get().load(recipe.getImages()).into(img_recipe);
                title.setText(recipe.getName());
                time_cooking.setText(String.valueOf(recipe.getTime()));
                like.setText(String.valueOf(recipe.getLikes()));
                serving.setText(String.valueOf(recipe.getServing()));
                result += replaceString(recipe.getIngredients());
                ingredient.setText(result);
                result_2 += recipe.getInstructions();
                instructions.setText(result_2);
            }
        }
        layout_contraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        button_Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeFavorite foods = new RecipeFavorite();
                foods = FoodsDatabase.getInstance(getContext()).recipeDao().getFoodsByID(recipe.getId());
                if (foods != null) {
                    Toast toast = new Toast(mactivity);
                    LayoutInflater inflater = getLayoutInflater();
                    View view_inflate = inflater.inflate(R.layout.layout_custom_toast, mactivity.findViewById(R.id.custom_toast));
                    TextView text_message = view_inflate.findViewById(R.id.text_toast);
                    text_message.setText("This Recipe has already exist"); // đã tồn tại
                    toast.setView(view_inflate);
                    toast.setGravity(Gravity.BOTTOM, 0, 25);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    RecipeFavorite food = new RecipeFavorite(recipe.getId(), recipe.getName(), recipe.getIngredients(), recipe.getInstructions(),
                            recipe.getImages(), recipe.getLikes(), recipe.getServing(), recipe.getTime(), recipe.getSourceName(), recipe.getSourceUrl(), recipe.getSpoonacularSourceUrl());
                    FoodsDatabase.getInstance(mactivity).recipeDao().insertFavorite(food);
                    Toast toast = new Toast(mactivity);
                    LayoutInflater inflater = getLayoutInflater();
                    View view_inflate = inflater.inflate(R.layout.layout_custom_toast, mactivity.findViewById(R.id.custom_toast));
                    TextView text_message = view_inflate.findViewById(R.id.text_toast);
                    text_message.setText("Add to Favorite Successfully");
                    toast.setView(view_inflate);
                    toast.setGravity(Gravity.BOTTOM, 0, 25);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        return views;
    }

    public String replaceString(String input) {
        String s = input.replace(";", "\n");
        return s;
    }

}