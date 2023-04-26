package com.example.recipefood.login;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.recipefood.R;

public class LoginPageFragment  extends Fragment {

    onChangedScreen changedScreen;
    EditText username,pass;
    Button btnLogin;

    float v = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_page, container, false);
        username = (EditText) view.findViewById(R.id.username);
        pass =(EditText) view.findViewById(R.id.pass);
        btnLogin =(Button) view.findViewById(R.id.btnLogin);

        username.setTranslationY(800);
        pass.setTranslationY(800);
        btnLogin.setTranslationY(800);

        username.setAlpha(v);
        pass.setAlpha(v);
        btnLogin.setAlpha(v);

        username.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btnLogin.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changedScreen.onChanged(username.getText().toString());
            }
        });
        return view;
    }
    public interface onChangedScreen{
        void onChanged(String username);
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
}
