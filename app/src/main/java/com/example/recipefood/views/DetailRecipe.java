package com.example.recipefood.views;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipefood.databinding.FragmentDetailRecipeBinding;
import com.example.recipefood.model.RecipeFavorite;
import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.R;
import com.example.recipefood.model.FoodRepository;
import com.example.recipefood.model.roomDatabase.FoodsDatabase;
import com.squareup.picasso.Picasso;

public class DetailRecipe extends Fragment {

    private FragmentDetailRecipeBinding binding;
    private Activity mActivity;
    private RecipeInstrument recipe;


    String result = "";
    String result_2 = "";

    private int Userid;
    private int state;
    //Khai báo layout
    private FoodRepository mFoodRepository;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailRecipeBinding.inflate(inflater, container, false);
        View views = binding.getRoot();
        //Ánh xạ
        mActivity = getActivity();
        mFoodRepository = new FoodRepository();
        binding.recipeBack.setOnClickListener(new View.OnClickListener() {
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
                Picasso.get().load(recipe.getImages()).into(binding.imageRecipe);
                binding.titleName.setText(recipe.getName());
                binding.timeCooking.setText(String.valueOf(recipe.getTime()));
                binding.textfavorite.setText(String.valueOf(recipe.getLikes()));
                binding.serving.setText(String.valueOf(recipe.getServing()));
                result += replaceString(recipe.getIngredients());
                binding.recipeIngredients.setText(result);
                result_2 += recipe.getInstructions();
                binding.recipeInstruction.setText(result_2);
                Log.d("Food Id : " , recipe.getId() + "");
                Log.d("Food Id : " , Userid + "");
                mFoodRepository.checkFoodFavoriteExist(Userid, recipe.getId(), new FoodRepository.OnExistListener() {
                    @Override
                    public void onExist(boolean exists) {
                        if(exists){
                            binding.favoriteRecipe.setImageResource(R.drawable.baseline_favorite_24);
                            state = 1;

                        }else {
                            binding.favoriteRecipe.setImageResource(R.drawable.baseline_unfavorite);
                            state = 0;
                        }
                    }
                });
            }
        }
        binding.layoutConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.downloadRecipe.setOnClickListener(new View.OnClickListener() {
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
        binding.favoriteRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state == 0){
                    addToFavorite();
                    binding.favoriteRecipe.setImageResource(R.drawable.baseline_favorite_24);
                }else if(state == 1){
                    removeFromFavorite();
                    binding.favoriteRecipe.setImageResource(R.drawable.baseline_unfavorite);
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
        mFoodRepository.addFavoriteForUser(Userid, recipe.getId(), new FoodRepository.OnExistListener() {
            @Override
            public void onExist(boolean exists) {
                if(exists){
                    customToast("Add To Favorite Successfully");
                    state = 1;
                }else{
                    customToast("Failed to Add !");
                }
            }
        });
    }
    public void removeFromFavorite(){
        mFoodRepository.removeFavoriteForUser(Userid, recipe.getId(), new FoodRepository.OnExistListener() {
            @Override
            public void onExist(boolean exists) {
                if(exists){
                    customToast("Remove From Favorite Successfully");
                    state = 0;
                }else{
                    customToast("Failed To Remove !");
                }
            }
        });
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