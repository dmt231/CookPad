package com.example.recipefood.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefood.MainActivity;
import com.example.recipefood.R;
import com.example.recipefood.adapter.RandomRecipeRycAdapter;
import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.model.firebaseDatabase;
import com.example.recipefood.views.Detail_Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    static int count = 0 ;
    RecyclerView recyclerView;
    private RandomRecipeRycAdapter randomRecipeRycAdapter;
    private List<RecipeInstrument> recipeList; //List các món ăn
    private MainActivity mActivity ;

    //private Spinner spinner;
    ProgressDialog progressDialog;

    //khai bao homefragmentviewmodel
    private HomeFragmentViewModel homeFragmentViewModel;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mActivity = (MainActivity) getActivity();
        View views = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = views.findViewById(R.id.recyclerView);
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.show();
        homeFragmentViewModel=new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        homeFragmentViewModel.getRecipeListLiveData(count, count + 10).observe(getViewLifecycleOwner(), new Observer<ArrayList<RecipeInstrument>>() {
            @Override
            public void onChanged(ArrayList<RecipeInstrument> listrecipe) {
                if(listrecipe != null){
                    recipeList = listrecipe;
                    onSetUpRecyclerView();
                    progressDialog.dismiss();
                }
                else{
                    Toast.makeText(mActivity, "Error connect", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.addOnScrollListener(addRecipeToRyc);

        return views;

    }
    public void onSetUpRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 1));
        randomRecipeRycAdapter = new RandomRecipeRycAdapter(mActivity, recipeList, new RandomRecipeRycAdapter.Detail_ClickListener() {
                        @Override
                        public void OnClickRecipe(RecipeInstrument recipe) {
                            Fragment detail_recipe = new Detail_Recipe();
                            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();


                            Bundle bundle = new Bundle();
                            bundle.putSerializable("recipe", recipe);
                            detail_recipe.setArguments(bundle);
                            fragmentTransaction.replace(R.id.fragment_home, detail_recipe);
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
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                int itemCount = recyclerView.getAdapter().getItemCount();
                if (lastVisibleItemPosition == itemCount - 1) {
                    if(count<90){
                        count+=10;
                    }
                    Log.d("Count : ", count + "");
                    progressDialog.show();
                    homeFragmentViewModel.getRecipeListLiveData(count, count+10).observe(getViewLifecycleOwner(), new Observer<ArrayList<RecipeInstrument>>() {
                        @Override
                        public void onChanged(ArrayList<RecipeInstrument> recipeInstruments) {
                            if(recipeInstruments != null){
                                recipeList.addAll(recipeInstruments);
                                randomRecipeRycAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            }
        }
    };
}