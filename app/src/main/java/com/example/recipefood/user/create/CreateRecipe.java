package com.example.recipefood.user.create;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipefood.R;
import com.example.recipefood.databinding.LayoutAddNewRecipeBinding;
import com.example.recipefood.fcm.SendNotificationTask;
import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.model.Repository;
import com.squareup.picasso.Picasso;

public class CreateRecipe extends Fragment {

    private LayoutAddNewRecipeBinding binding;
    private int id;

    private Repository mRepository;
    private RecipeInstrument foodRecipe;
    private int state = 0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutAddNewRecipeBinding.inflate(inflater, container, false);
        View views = binding.getRoot();
        mRepository = new Repository();
        getUserId();
        binding.imageUrl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String imageUrl = binding.imageUrl.getText().toString();
                    if (!imageUrl.isEmpty()) {
                        Picasso.get().load(imageUrl).into(binding.imageNewFoods);
                    }
                }
            }
        });

        binding.scrollviewCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.recipeBackFromCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getFragmentManager() != null){
                    getFragmentManager().popBackStack();
                }
            }
        });
        binding.btnPost.setOnClickListener(new View.OnClickListener() {
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
                Log.d("Food Exists :",foodRecipe.getId() + foodRecipe.getName() + foodRecipe.getIngredients() + foodRecipe.getInstructions());
                binding.postRecipeName.setText(foodRecipe.getName());
                binding.imageUrl.setText(foodRecipe.getImages());
                binding.postIngredient.setText(foodRecipe.getIngredients());
                binding.postInstructions.setText(foodRecipe.getInstructions());
                binding.postTime.setText(String.valueOf(foodRecipe.getTime()));
                binding.postServing.setText(String.valueOf(foodRecipe.getServing()));
                binding.txtRecipe.setText("Update Your Recipe");
                binding.btnPost.setText("Update");
            }
        }
    }
    public void addNewRecipe(){
        String image = binding.imageUrl.getText().toString();
        String name = binding.postRecipeName.getText().toString();
        String ingredientsValue = binding.postIngredient.getText().toString();
        String instructionsValue = binding.postInstructions.getText().toString();
        int timeValue = Integer.parseInt(binding.postTime.getText().toString());
        int servingValue = Integer.parseInt(binding.postServing.getText().toString());
        RecipeInstrument recipeInstrument = new RecipeInstrument(-1, name, ingredientsValue, instructionsValue, image, 0, servingValue,
                                                                    timeValue, "" , "", "",id );
        mRepository.addRecipe(recipeInstrument, new Repository.onSuccess() {
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
        String image = binding.imageUrl.getText().toString();
        String name = binding.postRecipeName.getText().toString();
        String ingredientsValue = binding.postIngredient.getText().toString();
        String instructionsValue = binding.postInstructions.getText().toString();
        int timeValue = Integer.parseInt(binding.postTime.getText().toString());
        int servingValue = Integer.parseInt(binding.postServing.getText().toString());
        mRepository.updateRecipe(foodRecipe.getId(), image, name, ingredientsValue, instructionsValue, timeValue, servingValue, new Repository.onSuccess() {
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
        if(binding.imageUrl.getText().toString().equals("")){
            binding.imageUrl.setError("Please enter your link image");
            check = false;
        }
        if(binding.postRecipeName.getText().toString().equals("")){
            binding.postRecipeName.setError("Please enter your recipe name");
            check = false;
        }
        if(binding.postIngredient.getText().toString().equals("")){
            binding.postIngredient.setError("Please enter your ingredients");
            check = false;
        }
        if(binding.postInstructions.getText().toString().equals("")){
            binding.postInstructions.setError("Please enter your instructions");
            check = false;
        }
        if(binding.postTime.getText().toString().equals("")){
            binding.postTime.setError("Please enter the time for your recipe");
            check = false;
        }
        if(binding.postServing.getText().toString().equals("")){
            binding.postServing.setError("Please enter your serving");
            check = false;
        }
        return check;
    }
}
