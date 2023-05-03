package com.example.recipefood.user.userfavorite;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefood.R;
import com.example.recipefood.adapter.RandomRecipeRycAdapter;
import com.example.recipefood.home.HomeFragmentViewModel;
import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.model.Repository;
import com.example.recipefood.views.DetailRecipe;

import java.util.ArrayList;

public class FavoriteRecipe extends Fragment {
    private ImageButton back;

    private int id;
    private RecyclerView recyclerView;
    private RandomRecipeRycAdapter adapter;
    private HomeFragmentViewModel viewModel;
    private ArrayList<RecipeInstrument> recipeList;
    private Repository repository;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_recipe, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_like_recipe);
        repository = new Repository();
        progressDialog = new ProgressDialog(getActivity());
        back = view.findViewById(R.id.recipe_back_from_like_recipe);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getFragmentManager() != null){
                    getFragmentManager().popBackStack();
                }
            }
        });
        viewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        getUserId();
        getData();
        return view;
    }
    public void getData(){
        repository.haveAnyFavorite(id, new Repository.OnExistListener() {
            @Override
            public void onExist(boolean exists) {
                if(exists){
                    progressDialog.show();
                    onObserve();
                }else{
                    customToast("This user haven't favorite recipe");
                }
            }
        });
    }

    public void onObserve(){
        viewModel.getFavoriteList(id).observe(getViewLifecycleOwner(), new Observer<ArrayList<RecipeInstrument>>() {
            @Override
            public void onChanged(ArrayList<RecipeInstrument> recipeInstruments) {
                progressDialog.dismiss();
                recipeList = recipeInstruments;
                onSetUpRecyclerView();
            }
        });
    }
    public void onSetUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        adapter = new RandomRecipeRycAdapter(recipeList, new RandomRecipeRycAdapter.Detail_ClickListener() {
            @Override
            public void OnClickRecipe(RecipeInstrument recipe) {
                Fragment detail_recipe = new DetailRecipe();
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();


                Bundle bundle = new Bundle();
                bundle.putSerializable("recipe", recipe);
                bundle.putInt("Userid", (int)id);
                detail_recipe.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_like_recipe, detail_recipe);
                fragmentTransaction.addToBackStack(detail_recipe.getTag());
                fragmentTransaction.commit();

            }
        });
        recyclerView.setAdapter(adapter);
    }
    public void getUserId(){
        Bundle bundle = getArguments();
        if(bundle != null){
            id = (int) bundle.get("Userid");
        }
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