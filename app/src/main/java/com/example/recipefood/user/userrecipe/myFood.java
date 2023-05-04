package com.example.recipefood.user.userrecipe;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefood.R;
import com.example.recipefood.adapter.RandomRecipeRycAdapter;
import com.example.recipefood.home.HomeFragmentViewModel;
import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.model.Repository;
import com.example.recipefood.user.UserFragment;
import com.example.recipefood.user.create.CreateRecipe;
import com.example.recipefood.views.DetailRecipe;

import java.util.ArrayList;
import java.util.Random;


public class myFood extends Fragment {
    private ImageButton back;

    private int id;
    private RecyclerView recyclerView;
    private RandomRecipeRycAdapter adapter;
    private HomeFragmentViewModel viewModel;
    private ArrayList<RecipeInstrument> recipeList;
    private Repository repository;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfood, container, false);
        repository = new Repository();
        recyclerView = view.findViewById(R.id.recyclerView_myFood);
        progressDialog = new ProgressDialog(getActivity());
        back = view.findViewById(R.id.recipe_back_from_myFood);
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
        recyclerView.addOnScrollListener(addRecipeToRyc);
        return view;
    }
    public void getData(){
        repository.haveAnyRecipe(id, new Repository.OnExistListener() {
            @Override
            public void onExist(boolean exists) {
                if(exists){
                    progressDialog.show();
                    onObserveData();
                }else{
                    customToast("This user haven't any recipe. Please add !");
                }
            }
        });
    }
    public void onSetUpRecyclerView(){
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
                fragmentTransaction.replace(R.id.fragment_myFood, detail_recipe);
                fragmentTransaction.addToBackStack(detail_recipe.getTag());
                fragmentTransaction.commit();

            }
        });
        recyclerView.setAdapter(adapter);
    }
    public void onObserveData(){
        viewModel.getRecipeListByUser(id).observe(getViewLifecycleOwner(), new Observer<ArrayList<RecipeInstrument>>() {
            @Override
            public void onChanged(ArrayList<RecipeInstrument> recipeInstruments) {
                progressDialog.dismiss();
                recipeList = recipeInstruments;
                onSetUpRecyclerView();

            }
        });
    }
    public void getUserId(){
        Bundle bundle = getArguments();
        if(bundle != null){
            id = (int) bundle.get("Userid");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case 101 :
                onChangedToEdit(recipeList.get(item.getGroupId()));
                return true;
            case 102 :
                alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Do you want to remove this recipe ? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int id = recipeList.get(item.getGroupId()).getId();
                                repository.deleteRecipe(id);
                                adapter.removeItem(item.getGroupId());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
                return true;
        }
        return super.onContextItemSelected(item);
    }
    public void onChangedToEdit(RecipeInstrument recipeInstrument){
        Fragment create = new CreateRecipe();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("Userid", id);
        bundle.putSerializable("recipeEdit", recipeInstrument);
        create.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_myFood, create);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private RecyclerView.OnScrollListener addRecipeToRyc = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                if (firstCompletelyVisibleItemPosition == 0) {
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.show();
                    viewModel.getRecipeListByUser(id).observe(getViewLifecycleOwner(), new Observer<ArrayList<RecipeInstrument>>() {
                        @Override
                        public void onChanged(ArrayList<RecipeInstrument> recipeInstruments) {
                            recipeList = recipeInstruments;
                            onSetUpRecyclerView();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        }
    };
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
