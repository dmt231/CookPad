package com.example.recipefood.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipefood.adapter.FavoriteRecipeAdapter;
import com.example.recipefood.databinding.FragmentRecipeBinding;
import com.example.recipefood.model.RecipeFavorite;
import com.example.recipefood.R;
import com.example.recipefood.model.roomDatabase.FoodsDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment {
    private FragmentRecipeBinding binding;
    private FavoriteRecipeAdapter favoriteRecipeAdapter;//Recycle Adapter
    private Activity mActivity;
    //Database.
    private static int selectedID = 0;
    private List<RecipeFavorite> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecipeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setupRecyclerview();
        return view;
    }

    private void setupRecyclerview() {
        favoriteRecipeAdapter = new FavoriteRecipeAdapter(mActivity, list, new FavoriteRecipeAdapter.Detail_ClickListener_Favorite() {
            @Override
            public void onClickRecipe(RecipeFavorite recipeFavorite) {
                Fragment detail_favorite = new DetailDownload();
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipe_favorite", recipeFavorite);
                detail_favorite.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_recipe, detail_favorite);
                fragmentTransaction.addToBackStack(detail_favorite.getTag());
                fragmentTransaction.commit();
            }
        });
        binding.recyclerviewFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewFavorite.setAdapter(favoriteRecipeAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        getAllFoods();
    }

    public void getAllFoods() {
        list = FoodsDatabase.getInstance(mActivity).recipeDao().getFoods();
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        RecipeFavorite recipe_favorite = list.get(selectedID);
        switch (item.getItemId()) {
            case R.id.delete:

        }
        return super.onContextItemSelected(item);
    }
}