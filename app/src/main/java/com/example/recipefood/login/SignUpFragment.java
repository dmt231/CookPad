package com.example.recipefood.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefood.R;
import com.example.recipefood.databinding.SignUpBinding;
import com.example.recipefood.model.FoodRepository;
import com.example.recipefood.model.User;
import com.example.recipefood.model.UserRepository;

import java.util.ArrayList;

public class SignUpFragment extends Fragment {
    private SignUpBinding binding;

    private UserRepository userRepository;
    private ViewModelSignUpLogin viewModelSignUpLogin;
    private ProgressDialog progressDialog;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = SignUpBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        progressDialog = new ProgressDialog(getActivity());
        userRepository = new UserRepository();
        viewModelSignUpLogin = new ViewModelProvider(this).get(ViewModelSignUpLogin.class);
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEmail() && checkUsername() && checkPassword()) {
                    progressDialog.show();
                    signUp();
                }
            }
        });
        return view;
    }

    public void signUp() {

        progressDialog.show();
        String usernameValue = binding.signUpUsername.getText().toString();
        String emailValue = binding.SignUpEmail.getText().toString();
        String password = binding.signUpPassword.getText().toString();

        userRepository.checkUserExist(new FoodRepository.OnExistListener() {
            @Override
            public void onExist(boolean exists) {
                if (!exists) {
                    progressDialog.dismiss();
                    userRepository.Register(usernameValue, emailValue, password);
                    customToast("Thêm Thành Công");
                } else if (exists) {

                }
            }
        });


        viewModelSignUpLogin.getListUser().observe(getViewLifecycleOwner(), new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                if (users != null) {
                    progressDialog.dismiss();
                    boolean exists = false;
                    for (User user : users) {
                        if (user.getUsername().equals(usernameValue) || user.getEmail().equals(emailValue)) {
                            exists = true;
                            customToast("Người dùng đã tồn tại");
                            break;
                        }
                    }
                    if (!exists) {
                        userRepository.Register(usernameValue, emailValue, password);
                        customToast("Thêm Thành Công");
                    }
                }
            }
        });
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
        if (binding.signUpUsername.getText().toString().length() == 0) {
            binding.signUpUsername.setError("Vui lòng điền tên đăng nhập");
            check = false;
        } else if (binding.signUpUsername.getText().length() < 6) {
            binding.signUpUsername.setError("Tên đăng nhập phải có tối thiểu 6 ký tự");
            check = false;
        }
        return check;
    }

    public boolean checkPassword() {
        boolean check = true;
        if (binding.signUpPassword.getText().toString().length() == 0) {
            binding.signUpPassword.setError("Vui lòng nhập mật khẩu");
            check = false;
        } else if (binding.signUpPassword.getText().length() < 6 || !binding.signUpPassword.getText().toString().matches(".*[A-Z].*")) {
            binding.signUpPassword.setError("Mật khẩu phải có ít nhất 6 ký tự, bao gồm ít nhất 1 chữ cái viết hoa");
            check = false;
        }
        return check;
    }

    public boolean checkEmail() {
        boolean check = true;
        if (binding.SignUpEmail.getText().toString().length() == 0) {
            binding.SignUpEmail.setError("Vui lòng nhập địa chỉ email");
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.SignUpEmail.getText().toString()).matches()) {
            binding.SignUpEmail.setError("Địa chỉ email không hợp lệ");
            check = false;
        }
        return check;
    }
}
