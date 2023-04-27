package com.example.recipefood.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
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
import com.example.recipefood.adapter.RecipeDownloadsAdapter;
import com.example.recipefood.model.DataBase.Models.RecipeFavoriteDownload;
import com.example.recipefood.model.DataBase.ViewModel.RecipeFavoriteDownloadViewModel;
import com.example.recipefood.model.DatabaseHelper;
import com.example.recipefood.model.RecipeFavorite;
import com.example.recipefood.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeFragment extends Fragment {
    private RecipeDownloadsAdapter favoriteRecipeAdapter;//Recycle Adapter
    private RecyclerView recyclerView;
    private Activity mActivity ;
    //Database.
    private DatabaseHelper database_helper;

    private  RecipeFavoriteDownloadViewModel viewModel;
    private static int selectedID = 0;
    private List<RecipeFavoriteDownload> list = new ArrayList<>();

    AlertDialog.Builder dialog_builder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_favorite);
        setupRecyclerview();
        return view;
    }
    private void setupRecyclerview() {
        favoriteRecipeAdapter = new RecipeDownloadsAdapter(mActivity, list, new RecipeDownloadsAdapter.Detail_ClickListener_Favorite() {
            @Override
            public void OnClickRecipe(RecipeFavorite recipeFavorite) {
                Fragment detail_favorite = new DetailFavorite();
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipe_favorite", recipeFavorite);
                detail_favorite.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_recipe, detail_favorite);
                fragmentTransaction.addToBackStack(detail_favorite.getTag());
                fragmentTransaction.commit();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(favoriteRecipeAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        viewModel=new RecipeFavoriteDownloadViewModel(requireActivity().getApplication());
        querydata();
    }

    public void querydata() {
        list=viewModel.getRecipeFavorite();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(mActivity);
        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        RecipeFavoriteDownload recipe_favorite = list.get(selectedID);
        switch(item.getItemId()){
            case R.id.delete:
                dialog_builder = new AlertDialog.Builder(mActivity);
                dialog_builder.setMessage("Do you want to remove this recipe")
                                .setTitle("Alert!")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                list.remove(selectedID);
//                                                database_helper.delete(recipe_favorite.getName());
//                                                favoriteRecipeAdapter.notifyDataSetChanged();
                                                //
                                                viewModel.DeleteByName(recipe_favorite.getName());
                                                viewModel.getAllRecipeFavorite().observeForever(new Observer<List<RecipeFavoriteDownload>>() {
                                                    @Override
                                                    public void onChanged(List<RecipeFavoriteDownload> recipeFavoriteDownloads) {
                                                        list=recipeFavoriteDownloads;
                                                        favoriteRecipeAdapter.notifyDataSetChanged();
                                                    }
                                                });
                                                //
                                                Toast toast = new Toast(mActivity);
                                                LayoutInflater inflater = getLayoutInflater();
                                                View view = inflater.inflate(R.layout.layout_custom_toast, (ViewGroup) mActivity.findViewById(R.id.custom_toast));
                                                TextView text_message = view.findViewById(R.id.text_toast);
                                                text_message.setText("Delete Successfully");
                                                toast.setView(view);
                                                toast.setGravity(Gravity.BOTTOM, 0,25);
                                                toast.setDuration(Toast.LENGTH_SHORT);
                                                toast.show();
                                            }
                                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
        }
        return super.onContextItemSelected(item);
    }
}