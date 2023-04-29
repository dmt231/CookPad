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
import com.example.recipefood.model.DatabaseHelper;
import com.example.recipefood.model.RecipeFavorite;
import com.example.recipefood.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment {
    private FavoriteRecipeAdapter favoriteRecipeAdapter;//Recycle Adapter
    private RecyclerView recyclerView;
    private Activity mActivity ;
    //Database.
    private DatabaseHelper database_helper;
    private static int selectedID = 0;
    private List<RecipeFavorite> list = new ArrayList<>();

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
        favoriteRecipeAdapter = new FavoriteRecipeAdapter(mActivity, list, new FavoriteRecipeAdapter.Detail_ClickListener_Favorite() {
            @Override
            public void OnClickRecipe(RecipeFavorite recipeFavorite) {
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(favoriteRecipeAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        database_helper = new DatabaseHelper(this.getContext());
        querydata();
    }

    public void querydata() {
        Cursor cursor = database_helper.getData("Select * from Recipe ");
        while(cursor.moveToNext()){
            String name = cursor.getString(1);
            String image = cursor.getString(2);
            int time = cursor.getInt(3);
            int like = cursor.getInt(4);
            int serving = cursor.getInt(5);
            String ingredients = cursor.getString(6);
            String instructions = cursor.getString(7);
            list.add(new RecipeFavorite(name, image,time,like,serving,ingredients,instructions));
            Log.d("Favorite : " , list.get(0).getName() + list.get(0).getTime());
        }
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(mActivity);
        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        RecipeFavorite recipe_favorite = list.get(selectedID);
        switch(item.getItemId()){
            case R.id.delete:
                dialog_builder = new AlertDialog.Builder(mActivity);
                dialog_builder.setMessage("Do you want to remove this recipe")
                                .setTitle("Alert!")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                list.remove(selectedID);
                                                database_helper.delete(recipe_favorite.getName());
                                                favoriteRecipeAdapter.notifyDataSetChanged();
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