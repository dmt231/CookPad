package com.example.recipefood.search;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefood.R;
import com.example.recipefood.adapter.RandomRecipeRycAdapter;
import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.views.DetailRecipe;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private static int count = 0;
    private String ingredient = "";
    private RecyclerView recyclerView;
    private SearchView searchView;
    private RandomRecipeRycAdapter randomRecipeRycAdapter;
    SearchFragmentViewModel searchFragmentViewModel;
    ProgressDialog progressDialog;
    private ArrayList<RecipeInstrument> recipeList;

    LinearLayoutManager layoutManager;
    private int userId;
    public SearchFragment(long id) {
        // Required empty public constructor
        this.userId = (int) id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = view.findViewById(R.id.search_recipe_layout);
        recyclerView = view.findViewById(R.id.recycler_search_layout);
        progressDialog = new ProgressDialog(getActivity());
        searchFragmentViewModel = new ViewModelProvider(this).get(SearchFragmentViewModel.class);
        getSearch();
        recyclerView.addOnScrollListener(addRecipeToRyc);
        return view;
    }

    public void getSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.show();
                ingredient = query;
                searchFragmentViewModel.getRecipeListLiveData(count, count + 10, ingredient).observe(getViewLifecycleOwner(), new Observer<ArrayList<RecipeInstrument>>() {
                    @Override
                    public void onChanged(ArrayList<RecipeInstrument> recipeInstruments) {
                        if (recipeInstruments != null) {
                            progressDialog.dismiss();
                            recipeList = recipeInstruments;
                            onSetUpRecyclerView();

                        } else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void onSetUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 1));
        randomRecipeRycAdapter = new RandomRecipeRycAdapter(recipeList, new RandomRecipeRycAdapter.Detail_ClickListener() {
            @Override
            public void OnClickRecipe(RecipeInstrument recipe) {
                Fragment detail_recipe = new DetailRecipe();
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();


                Bundle bundle = new Bundle();
                bundle.putSerializable("recipe", recipe);
                bundle.putInt("Userid", (int)userId);
                detail_recipe.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_search, detail_recipe);
                fragmentTransaction.addToBackStack(detail_recipe.getTag());
                fragmentTransaction.commit();
            }
        });
        recyclerView.setAdapter(randomRecipeRycAdapter);
    }

    private RecyclerView.OnScrollListener addRecipeToRyc = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                int itemCount = recyclerView.getAdapter().getItemCount();
                if (lastVisibleItemPosition == itemCount - 1) {
                    if (count <= 90) {
                        count += 10;
                        searchFragmentViewModel.getRecipeListLiveData(count, count + 10, ingredient).observe(getViewLifecycleOwner(), new Observer<ArrayList<RecipeInstrument>>() {
                            @Override
                            public void onChanged(ArrayList<RecipeInstrument> recipeInstruments) {
                                if (recipeInstruments != null) {
                                    recipeList.addAll(recipeInstruments);
                                    randomRecipeRycAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            }
        }
    };
}