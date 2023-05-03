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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipefood.R;
import com.example.recipefood.fcm.SendNotificationTask;
import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.model.Repository;

public class CreateRecipe extends Fragment {
    private ScrollView scrollView;
    private ImageButton back;
    private EditText linkImage;
    private EditText recipeName;
    private EditText ingredients;
    private EditText instructions;
    private EditText time;
    private EditText serving;

    private Button Post;
    private int id;

    private Repository repository;
    private RecipeInstrument foodRecipe;
    private int state = 0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View views = inflater.inflate(R.layout.layout_add_new_recipe, container, false);
        scrollView = views.findViewById(R.id.scrollview_createNew);
        back = views.findViewById(R.id.recipe_back_from_create);
        linkImage = views.findViewById(R.id.image_url);
        recipeName = views.findViewById(R.id.post_recipe_name);
        ingredients = views.findViewById(R.id.post_ingredient);
        instructions = views.findViewById(R.id.post_instructions);
        repository = new Repository();
        time = views.findViewById(R.id.post_time);
        serving = views.findViewById(R.id.post_serving);
        Post = views.findViewById(R.id.btnPost);
        getUserId();


        scrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getFragmentManager() != null){
                    getFragmentManager().popBackStack();
                }
            }
        });
        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(state == 0){
                    addNewRecipe();
                }else{
                    updateRecipe();
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
                recipeName.setText(foodRecipe.getName());
                linkImage.setText(foodRecipe.getImages());
                ingredients.setText(foodRecipe.getIngredients());
                instructions.setText(foodRecipe.getInstructions());
                time.setText(String.valueOf(foodRecipe.getTime()));
                serving.setText(String.valueOf(foodRecipe.getServing()));
            }
        }
    }
    public void addNewRecipe(){
        String image = linkImage.getText().toString();
        String name = recipeName.getText().toString();
        String ingredientsValue = ingredients.getText().toString();
        String instructionsValue = instructions.getText().toString();
        int timeValue = Integer.parseInt(time.getText().toString());
        int servingValue = Integer.parseInt(serving.getText().toString());
        RecipeInstrument recipeInstrument = new RecipeInstrument(-1, name, ingredientsValue, instructionsValue, image, 0, servingValue,
                                                                    timeValue, "" , "", "",id );
        repository.addRecipe(recipeInstrument, new Repository.onSuccess() {
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
        String image = linkImage.getText().toString();
        String name = recipeName.getText().toString();
        String ingredientsValue = ingredients.getText().toString();
        String instructionsValue = instructions.getText().toString();
        int timeValue = Integer.parseInt(time.getText().toString());
        int servingValue = Integer.parseInt(serving.getText().toString());
        repository.updateRecipe(foodRecipe.getId(), image, name, ingredientsValue, instructionsValue, timeValue, servingValue, new Repository.onSuccess() {
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
}
