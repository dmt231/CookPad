package com.example.recipefood.Views;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipefood.Adapter.RandomRecipeRycAdapter;
import com.example.recipefood.MainActivity;
import com.example.recipefood.Model.RecipeInstrument;
import com.example.recipefood.Model.firebaseDatabase;
import com.example.recipefood.R;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private RecyclerView recycler_search_layout;

    private MainActivity mActivity;
    private firebaseDatabase database;
    private List<String> tags = new ArrayList<>();
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        SearchView search_recipe_layout = view.findViewById(R.id.search_recipe_layout);
        recycler_search_layout = view.findViewById(R.id.recycler_search_layout);
        mActivity = (MainActivity) getActivity();

        return view;
    }
}