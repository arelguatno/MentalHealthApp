package com.example.mentalhealthapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mentalhealthapp.R;

public class LoginHomeScreen extends AppCompatActivity {

    private Button createAccountBtn, loginBtn;
    private static final String TAG = "GoogleActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        createAccountBtn = (Button) findViewById(R.id.create_account_button);
        loginBtn = (Button) findViewById(R.id.login_button);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.create_account_button) {
            Intent intent = new Intent(LoginHomeScreen.this, CreateAccount.class);
            startActivity(intent);
        } else if (id == R.id.login_button) {
            Intent intent = new Intent(LoginHomeScreen.this, LoginActivity.class);
            startActivity(intent);
        }
    }

}