package com.example.recipefood.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefood.R;
import com.example.recipefood.databinding.LoginPageBinding;
import com.example.recipefood.model.FoodRepository;
import com.example.recipefood.model.User;
import com.example.recipefood.preference.SharedPreference;

import java.util.ArrayList;

public class LoginPageFragment extends Fragment {
    private LoginPageBinding viewBinding;
    private onChangedScreen changedScreen;
    private ProgressDialog progressDialog;
    private ViewModelSignUpLogin viewModelSignUpLogin;
    float v = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = LoginPageBinding.inflate(inflater, container, false);
        View view = viewBinding.getRoot();
        progressDialog = new ProgressDialog(getActivity());
        viewModelSignUpLogin = new ViewModelProvider(this).get(ViewModelSignUpLogin.class);
        setAnimation();
        viewBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkUsername() && checkPassword()) {
                    progressDialog.show();
                    Login();
                }
            }
        });
        return view;
    }

    public void Login() {
        String usernameValue = viewBinding.username.getText().toString();
        String passwordValue = viewBinding.pass.getText().toString();
        viewModelSignUpLogin.getListUser().observe(getViewLifecycleOwner(), new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                progressDialog.dismiss();
                boolean check = false;
                long id = 0;
                if (users != null) {
                    for (User user : users) {
                        if (checkLogin(user, usernameValue, passwordValue)) {
                            id = user.getUserId();
                            new SharedPreference().keepLoggedInUser((int) user.getUserId(), requireContext());
                            check = true;
                            break;
                        }
                    }
                    if (check) {
                        changedScreen.onChanged(id);
                    } else {
                        customToast("Tên đăng nhập hoặc mật khẩu không đúng");
                    }
                }
            }
        });
    }

    public interface onChangedScreen {
        void onChanged(Long id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            changedScreen = (onChangedScreen) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnButtonClickListener");
        }
    }

    public void setAnimation() {
        viewBinding.username.setTranslationY(800);
        viewBinding.pass.setTranslationY(800);
        viewBinding.btnLogin.setTranslationY(800);

        viewBinding.username.setAlpha(v);
        viewBinding.pass.setAlpha(v);
        viewBinding.btnLogin.setAlpha(v);

        viewBinding.username.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        viewBinding.pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        viewBinding.btnLogin.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
    }

    public boolean checkLogin(User user, String username, String password) {
        boolean check = false;
        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            check = true;
        }
        return check;
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

    public boolean checkUsername() {
        boolean check = true;
        if (viewBinding.username.getText().toString().length() == 0) {
            viewBinding.username.setError("Vui lòng điền tên đăng nhập");
            check = false;
        }
        return check;
    }

    public boolean checkPassword() {
        boolean check = true;
        if (viewBinding.pass.getText().toString().length() == 0) {
            viewBinding.pass.setError("Vui lòng nhập mật khẩu");
            check = false;
        }
        return check;
    }
}
