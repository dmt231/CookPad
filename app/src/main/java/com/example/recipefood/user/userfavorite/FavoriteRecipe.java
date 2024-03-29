package com.example.recipefood.user.userfavorite;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.recipefood.databinding.FavoriteRecipeBinding;
import com.example.recipefood.home.HomeFragmentViewModel;
import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.model.FoodRepository;
import com.example.recipefood.user.create.CreateRecipe;
import com.example.recipefood.views.DetailRecipe;

import java.util.ArrayList;

public class FavoriteRecipe extends Fragment {
    private FavoriteRecipeBinding viewBinding;

    private int id;
    private RandomRecipeRycAdapter adapter;
    private HomeFragmentViewModel viewModel;
    private ArrayList<RecipeInstrument> recipeList;
    private FoodRepository foodRepository;
    private ProgressDialog progressDialog;
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding = FavoriteRecipeBinding.inflate(inflater,container,false);
        View view = viewBinding.getRoot();
        foodRepository = new FoodRepository();
        progressDialog = new ProgressDialog(getActivity());
        viewBinding.recipeBackFromLikeRecipe.setOnClickListener(new View.OnClickListener() {
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
        viewBinding.recyclerViewLikeRecipe.addOnScrollListener(addRecipeToRyc);
        return view;
    }
    public void getData(){
        foodRepository.haveAnyFavorite(id, new FoodRepository.OnExistListener() {
            @Override
            public void onExist(boolean exists) {
                if(exists){
                    progressDialog.show();
                    onObserve();
                }else{
                    if(recipeList != null) {
                        recipeList.clear();
                        adapter.notifyDataSetChanged();
                    }
                    customToast("This user hasn't any favorite recipe");
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
        viewBinding.recyclerViewLikeRecipe.setHasFixedSize(true);
        viewBinding.recyclerViewLikeRecipe.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        adapter = new RandomRecipeRycAdapter(recipeList, new RandomRecipeRycAdapter.Detail_ClickListener() {
            @Override
            public void onClickRecipe(RecipeInstrument recipe) {
                Fragment detail_recipe = new DetailRecipe();
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();


                Bundle bundle = new Bundle();
                bundle.putSerializable("recipe", recipe);
                bundle.putInt("Userid", id);
                detail_recipe.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_like_recipe, detail_recipe);
                fragmentTransaction.addToBackStack(detail_recipe.getTag());
                fragmentTransaction.commit();

            }
        });
        viewBinding.recyclerViewLikeRecipe.setAdapter(adapter);
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
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case 101 :
                int userid = recipeList.get(item.getGroupId()).getUserid();
                if(userid == id){
                    onChangedToEdit(recipeList.get(item.getGroupId()));
                }else{
                    customToast("You don't have the permission");
                }
                return true;
            case 102 :
                int useridDelete = recipeList.get(item.getGroupId()).getUserid();
                if(useridDelete== id){
                    int id = recipeList.get(item.getGroupId()).getId();
                    foodRepository.deleteRecipe(id);
                    adapter.removeItem(item.getGroupId());
                }else{
                    customToast("You don't have the permission");
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }
    private RecyclerView.OnScrollListener addRecipeToRyc = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                if (firstCompletelyVisibleItemPosition == 0) {
                    getData();
                }
            }
        }
    };
    public void onChangedToEdit(RecipeInstrument recipeInstrument){
        Fragment create = new CreateRecipe();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("Userid", id);
        bundle.putSerializable("recipeEdit", recipeInstrument);
        create.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_like_recipe, create);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
