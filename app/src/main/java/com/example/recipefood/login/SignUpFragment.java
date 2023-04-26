package com.example.recipefood.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.recipefood.R;

public class SignUpFragment extends Fragment {
    private EditText email,pass, username;
    private Button btnSignup;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up, container, false);
        email = view.findViewById(R.id.SignUpEmail);
        pass = view.findViewById(R.id.signUpPassword);
        username = view.findViewById(R.id.signUpUsername);
        btnSignup = view.findViewById(R.id.btnSignUp);
        return view;
    }
}
