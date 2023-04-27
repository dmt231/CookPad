package com.example.recipefood.views;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipefood.model.DataBase.Models.RecipeFavoriteDownload;
import com.example.recipefood.model.DataBase.ViewModel.RecipeFavoriteDownloadViewModel;
import com.example.recipefood.model.DatabaseHelper;
import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

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

    private  RecipeFavoriteDownloadViewModel viewModel;

    //Khai báo Database để lưu trữ dữ liệu
    DatabaseHelper database_helper;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel=new RecipeFavoriteDownloadViewModel(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View views = inflater.inflate(R.layout.fragment_detail__recipe, container, false);
        //Database.
        database_helper = new DatabaseHelper(this.getContext());
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
                Picasso.get().load(recipe.getImages()).into(img_recipe);;
                title.setText(recipe.getName());
                time_cooking.setText(String.valueOf(recipe.getTime()));
                like.setText(String.valueOf(recipe.getLikes()));
                serving.setText(String.valueOf(recipe.getServing()));
                result+= replaceString(recipe.getIngredients());
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

                List<RecipeFavoriteDownload> list=viewModel.getRecipeByName(recipe.getName());
                if(list.size()!=0){
                    Toast toast = new Toast(mactivity);
                    LayoutInflater inflater = getLayoutInflater();
                    View view_inflate = inflater.inflate(R.layout.layout_custom_toast, mactivity.findViewById(R.id.custom_toast));
                    TextView text_message = view_inflate.findViewById(R.id.text_toast);
                    text_message.setText("This Recipe has already exits");
                    toast.setView(view_inflate);
                    toast.setGravity(Gravity.BOTTOM, 0,25);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                }else {
                    viewModel.insertRecipeFavorite(recipe.getId(),recipe.getName(), recipe.getImages(), recipe.getTime(), recipe.getLikes(), recipe.getServing(), result, result_2,recipe.getSourceUrl(),recipe.getSpoonacularSourceUrl());
                    Toast toast = new Toast(mactivity);
                    LayoutInflater inflater = getLayoutInflater();
                    View view_inflate = inflater.inflate(R.layout.layout_custom_toast, mactivity.findViewById(R.id.custom_toast));
                    TextView text_message = view_inflate.findViewById(R.id.text_toast);
                    text_message.setText("Add to Favorite Successfully");
                    toast.setView(view_inflate);
                    toast.setGravity(Gravity.BOTTOM, 0,25);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        return views;
    }
    public String replaceString(String input){
        String s = input.replace(";", "\n");
        return s;
    }

}