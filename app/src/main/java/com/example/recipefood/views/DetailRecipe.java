package com.example.recipefood.views;

import android.app.Activity;
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
import com.example.recipefood.model.Repository;
import com.example.recipefood.model.roomDatabase.FoodsDatabase;
import com.squareup.picasso.Picasso;

public class DetailRecipe extends Fragment {

    private Activity mActivity;
    private RecipeInstrument recipe;
    private ImageButton buttonBack;
    private ImageView buttonDownload;
    private ImageView button_favorite;
    private ImageView img_recipe;
    private TextView title;
    private TextView time_cooking;
    private TextView like;
    private TextView serving;
    private TextView ingredient;
    private TextView instructions;


    //Khai báo listString để lưu danh sách nguyên liệu
    String result = "";
    String result_2 = "";

    private int Userid;
    private int state;
    //Khai báo layout
    private ScrollView layoutConstraint;
    private Repository mRepository;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View views = inflater.inflate(R.layout.fragment_detail__recipe, container, false);
        //Ánh xạ
        mActivity = getActivity();
        mRepository = new Repository();
        layoutConstraint = views.findViewById(R.id.layout_constraint);
        img_recipe = views.findViewById(R.id.image_recipe);
        title = views.findViewById(R.id.title_name);
        time_cooking = views.findViewById(R.id.time_cooking);
        like = views.findViewById(R.id.textfavorite);
        serving = views.findViewById(R.id.serving);
        title = views.findViewById(R.id.title_name);
        ingredient = views.findViewById(R.id.recipe_ingredients);
        instructions = views.findViewById(R.id.recipe_instruction);
        buttonBack = (ImageButton) views.findViewById(R.id.recipe_back);
        button_favorite = (ImageButton) views.findViewById(R.id.favorite_recipe);
        buttonDownload = (ImageButton) views.findViewById(R.id.download_recipe);
        buttonBack.setOnClickListener(new View.OnClickListener() {
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
            Userid = (int)bundle.get("Userid");
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
                Log.d("Food Id : " , recipe.getId() + "");
                Log.d("Food Id : " , Userid + "");
                mRepository.checkFavoriteExist(Userid, recipe.getId(), new Repository.OnExistListener() {
                    @Override
                    public void onExist(boolean exists) {
                        if(exists){
                            button_favorite.setImageResource(R.drawable.baseline_favorite_24);
                            state = 1;

                        }else {
                            button_favorite.setImageResource(R.drawable.baseline_unfavorite);
                            state = 0;
                        }
                    }
                });
            }
        }
        layoutConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeFavorite foods = new RecipeFavorite();
                foods = FoodsDatabase.getInstance(getContext()).recipeDao().getFoodsByID(recipe.getId());
                if (foods != null) {
                    Toast toast = new Toast(mActivity);
                    LayoutInflater inflater = getLayoutInflater();
                    View view_inflate = inflater.inflate(R.layout.layout_custom_toast, mActivity.findViewById(R.id.custom_toast));
                    TextView text_message = view_inflate.findViewById(R.id.text_toast);
                    text_message.setText("This Recipe has already exist"); // đã tồn tại
                    toast.setView(view_inflate);
                    toast.setGravity(Gravity.BOTTOM, 0, 25);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    RecipeFavorite food = new RecipeFavorite(recipe.getId(), recipe.getName(), recipe.getIngredients(), recipe.getInstructions(),
                            recipe.getImages(), recipe.getLikes(), recipe.getServing(), recipe.getTime(), recipe.getSourceName(), recipe.getSourceUrl(), recipe.getSpoonacularSourceUrl());
                    FoodsDatabase.getInstance(mActivity).recipeDao().insertFavorite(food);
                    Toast toast = new Toast(mActivity);
                    LayoutInflater inflater = getLayoutInflater();
                    View view_inflate = inflater.inflate(R.layout.layout_custom_toast, mActivity.findViewById(R.id.custom_toast));
                    TextView text_message = view_inflate.findViewById(R.id.text_toast);
                    text_message.setText("Add to Download Successfully");
                    toast.setView(view_inflate);
                    toast.setGravity(Gravity.BOTTOM, 0, 25);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        button_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state == 0){
                    addToFavorite();
                    state = 1;
                    button_favorite.setImageResource(R.drawable.baseline_favorite_24);
                    customToast("Add To Favorite Successfully");
                }else if(state == 1){
                    removeFromFavorite();
                    state = 0;
                    button_favorite.setImageResource(R.drawable.baseline_unfavorite);
                    customToast("Remove From Favorite Successfully");
                }
            }
        });
        return views;
    }

    public String replaceString(String input) {
        String s = input.replace(";", "\n");
        return s;
    }
    public void addToFavorite(){
        mRepository.addFavoriteForUser(Userid, recipe.getId());
    }
    public void removeFromFavorite(){
        mRepository.removeFavoriteForUser(Userid, recipe.getId());
    }
    public void customToast(String message) {
        Toast toast = new Toast(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view_inflate = inflater.inflate(R.layout.layout_custom_toast, getActivity().findViewById(R.id.custom_toast));
        TextView text_message = view_inflate.findViewById(R.id.text_toast);
        text_message.setText(message);
        toast.setView(view_inflate);
        toast.setGravity(Gravity.BOTTOM, 0, 25);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}