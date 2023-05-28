package com.example.recipefood.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefood.R;
import com.example.recipefood.adapter.RandomRecipeRycAdapter;
import com.example.recipefood.databinding.FragmentHomeBinding;
import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.model.Repository;
import com.example.recipefood.user.create.CreateRecipe;
import com.example.recipefood.views.DetailRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    static int count = 0;
    private RandomRecipeRycAdapter randomRecipeRycAdapter;
    private List<RecipeInstrument> recipeList; //List các món ăn

    ProgressDialog progressDialog;

    private HomeFragmentViewModel homeFragmentViewModel;
    private long userId;
    private Repository mRepository;
    private AlertDialog.Builder alertDialog;
    private LinearLayoutManager layoutManager;

    public HomeFragment(long userId) {
        // Required empty public constructor
        this.userId = userId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View views = binding.getRoot();
        mRepository = new Repository();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        homeFragmentViewModel.getRecipeListLiveData(count, count + 10).observe(getViewLifecycleOwner(), new Observer<ArrayList<RecipeInstrument>>() {
            @Override
            public void onChanged(ArrayList<RecipeInstrument> listrecipe) {
                if (listrecipe != null) {
                    recipeList = listrecipe;
                    onSetUpRecyclerView();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Error connect", Toast.LENGTH_SHORT).show();
                }
            }
        });


        binding.recyclerView.addOnScrollListener(addRecipeToRyc);
        return views;
    }

    public void onSetUpRecyclerView() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        randomRecipeRycAdapter = new RandomRecipeRycAdapter(recipeList, new RandomRecipeRycAdapter.Detail_ClickListener() {
            @Override
            public void onClickRecipe(RecipeInstrument recipe) {
                Fragment detail_recipe = new DetailRecipe();
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipe", recipe);
                bundle.putInt("Userid", (int) userId);
                detail_recipe.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_home, detail_recipe);
                fragmentTransaction.addToBackStack(detail_recipe.getTag());
                fragmentTransaction.commit();
            }
        });
        binding.recyclerView.setAdapter(randomRecipeRycAdapter);
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
                        homeFragmentViewModel.getRecipeListLiveData(count, count + 10).observe(getViewLifecycleOwner(), new Observer<ArrayList<RecipeInstrument>>() {
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
        switch (item.getItemId()) {
            case 101:
                int userid = recipeList.get(item.getGroupId()).getUserid();
                if (userid == userId) {
                    onChangedToEdit(recipeList.get(item.getGroupId()));
                } else {
                    customToast("You don't have the permission");
                }
                return true;
            case 102:
                int useridDelete = recipeList.get(item.getGroupId()).getUserid();
                if (useridDelete == userId) {
                    alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setMessage("Do you want to remove this recipe ? ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    int id = recipeList.get(item.getGroupId()).getId();
                                    mRepository.deleteRecipe(id);
                                    randomRecipeRycAdapter.removeItem(item.getGroupId());
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            }).show();
                } else {
                    customToast("You don't have the permission");
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void onChangedToEdit(RecipeInstrument recipeInstrument) {
        Fragment create = new CreateRecipe();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("Userid", (int) userId);
        bundle.putSerializable("recipeEdit", recipeInstrument);
        create.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_like_recipe, create);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}