package com.example.recipefood.user.create;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipefood.R;
import com.example.recipefood.databinding.LayoutAddNewRecipeBinding;
import com.example.recipefood.fcm.SendNotificationTask;
import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.model.FoodRepository;
import com.squareup.picasso.Picasso;

public class CreateRecipe extends Fragment {

    private LayoutAddNewRecipeBinding viewBinding;
    private int id;

    private FoodRepository mFoodRepository;
    private RecipeInstrument foodRecipe;
    private int state = 0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding = LayoutAddNewRecipeBinding.inflate(inflater, container, false);
        View views = viewBinding.getRoot();
        mFoodRepository = new FoodRepository();
        getUserId();
        viewBinding.imageUrl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String imageUrl = viewBinding.imageUrl.getText().toString();
                    if (!imageUrl.isEmpty()) {
                        Picasso.get().load(imageUrl).into(viewBinding.imageNewFoods);
                    }
                }
            }
        });

        viewBinding.scrollviewCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewBinding.recipeBackFromCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getFragmentManager() != null){
                    getFragmentManager().popBackStack();
                }
            }
        });
        viewBinding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(state == 0 ){
                    if(validateInput())
                        {addNewRecipe();}
                }else{
                    if(validateInput())
                        {updateRecipe();}
                }
            }
        });
        return views;
    }
    public void getUserId(){
        Bundle bundle = getArguments();
        if(bundle != null){
            id = (int) bundle.get("Userid");
            Log.d("Id Create : ", id + "");
            foodRecipe = (RecipeInstrument) bundle.get("recipeEdit");
            if(foodRecipe != null){
                state = 1;
                viewBinding.postRecipeName.setText(foodRecipe.getName());
                viewBinding.imageUrl.setText(foodRecipe.getImages());
                viewBinding.postIngredient.setText(foodRecipe.getIngredients());
                viewBinding.postInstructions.setText(foodRecipe.getInstructions());
                viewBinding.postTime.setText(String.valueOf(foodRecipe.getTime()));
                viewBinding.postServing.setText(String.valueOf(foodRecipe.getServing()));
                viewBinding.txtRecipe.setText("Update Your Recipe");
                viewBinding.btnPost.setText("Update");
            }
        }
    }
    public void addNewRecipe(){
        String image = viewBinding.imageUrl.getText().toString();
        String name = viewBinding.postRecipeName.getText().toString();
        String ingredientsValue = viewBinding.postIngredient.getText().toString();
        String instructionsValue = viewBinding.postInstructions.getText().toString();
        int timeValue = Integer.parseInt(viewBinding.postTime.getText().toString());
        int servingValue = Integer.parseInt(viewBinding.postServing.getText().toString());
        RecipeInstrument recipeInstrument = new RecipeInstrument(-1, name, ingredientsValue, instructionsValue, image, 0, servingValue,
                                                                    timeValue, "" , "", "",id );
        mFoodRepository.addRecipe(recipeInstrument, new FoodRepository.onSuccess() {
            @Override
            public void onAddSuccess() {
                customToast("Add Successfully ! ");
                SendNotificationTask sender = new SendNotificationTask("new_recipe","New recipe", "Let's cook " + name);
                sender.execute();
            }

            @Override
            public void onUpdateSuccess() {

            }
        });
    }
    public void updateRecipe(){
        String image = viewBinding.imageUrl.getText().toString();
        String name = viewBinding.postRecipeName.getText().toString();
        String ingredientsValue = viewBinding.postIngredient.getText().toString();
        String instructionsValue = viewBinding.postInstructions.getText().toString();
        int timeValue = Integer.parseInt(viewBinding.postTime.getText().toString());
        int servingValue = Integer.parseInt(viewBinding.postServing.getText().toString());
        mFoodRepository.updateRecipe(foodRecipe.getId(), image, name, ingredientsValue, instructionsValue, timeValue, servingValue, new FoodRepository.onSuccess() {
            @Override
            public void onAddSuccess() {

            }

            @Override
            public void onUpdateSuccess() {
                customToast("Update Successfully !");
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
    public boolean validateInput(){
        boolean check = true;
        if(viewBinding.imageUrl.getText().toString().equals("")){
            viewBinding.imageUrl.setError("Please enter your link image");
            check = false;
        }
        if(viewBinding.postRecipeName.getText().toString().equals("")){
            viewBinding.postRecipeName.setError("Please enter your recipe name");
            check = false;
        }
        if(viewBinding.postIngredient.getText().toString().equals("")){
            viewBinding.postIngredient.setError("Please enter your ingredients");
            check = false;
        }
        if(viewBinding.postInstructions.getText().toString().equals("")){
            viewBinding.postInstructions.setError("Please enter your instructions");
            check = false;
        }
        if(viewBinding.postTime.getText().toString().equals("")){
            viewBinding.postTime.setError("Please enter the time for your recipe");
            check = false;
        }
        if(viewBinding.postServing.getText().toString().equals("")){
            viewBinding.postServing.setError("Please enter your serving");
            check = false;
        }
        return check;
    }
}
