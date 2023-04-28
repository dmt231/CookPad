package com.example.recipefood.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.recipefood.model.Repository;
import com.example.recipefood.model.User;

import java.util.ArrayList;

public class SignUpFragment extends Fragment {
    private EditText email,pass, username;
    private Button btnSignup;

    private Repository repository;
    private ViewModelSignUpLogin viewModelSignUpLogin;
    private ProgressDialog progressDialog;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up, container, false);
        email = view.findViewById(R.id.SignUpEmail);
        pass = view.findViewById(R.id.signUpPassword);
        username = view.findViewById(R.id.signUpUsername);
        btnSignup = view.findViewById(R.id.btnSignUp);
        progressDialog = new ProgressDialog(getActivity());
        repository = new Repository();
        viewModelSignUpLogin = new ViewModelProvider(this).get(ViewModelSignUpLogin.class);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmail() && checkUsername() && checkPassword()){
                    progressDialog.show();
                    signUp();
                }
            }
        });
        return view;
    }
    public void signUp(){

                progressDialog.show();
                String usernameValue = username.getText().toString();
                String emailValue = email.getText().toString();
                String password = pass.getText().toString();
                User user = new User(usernameValue, password, emailValue);

                repository.checkUserExist(new Repository.OnUserExistListener() {
                    @Override
                    public void onUserExist(boolean exists) {
                        if(!exists){
                            progressDialog.dismiss();
                            repository.Register(user);
                            customToast("Thêm Thành Công");
                        }
                        else if(exists){

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
                                    repository.Register(user);
                                    customToast("Thêm Thành Công");
                                }
                            }
                    }
                });
    }
    public void customToast(String message){
        Toast toast = new Toast(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view_inflate = inflater.inflate(R.layout.layout_custom_toast, getActivity().findViewById(R.id.custom_toast));
        TextView text_message = view_inflate.findViewById(R.id.text_toast);
        text_message.setText(message);
        toast.setView(view_inflate);
        toast.setGravity(Gravity.BOTTOM, 0,25);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
    public boolean checkUsername(){
        boolean check = true;
        if(username.getText().toString().length() == 0){
            username.setError("Vui lòng điền tên đăng nhập");
            check = false;
        }
        else if(username.getText().length()<6){
            username.setError("Tên đăng nhập phải có tối thiểu 6 ký tự");
            check =false;
        }
        return check;
    }
    public boolean checkPassword(){
        boolean check = true;
        if(pass.getText().toString().length() == 0){
            pass.setError("Vui lòng nhập mật khẩu");
            check = false;
        }
        else if(pass.getText().length() < 6 || !pass.getText().toString().matches(".*[A-Z].*")){
            pass.setError("Mật khẩu phải có ít nhất 6 ký tự, bao gồm ít nhất 1 chữ cái viết hoa");
            check = false;
        }
        return check;
    }
    public boolean checkEmail(){
        boolean check = true;
        if(email.getText().toString().length() == 0) {
            email.setError("Vui lòng nhập địa chỉ email");
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            email.setError("Địa chỉ email không hợp lệ");
            check = false;
        }
        return check;
    }
}
