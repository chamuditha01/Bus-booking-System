package com.example.busbookingsystem_madcw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Log.d("LoginActivity", "LoginActivity loaded.");

        TextView signupLink = findViewById(R.id.dnthvacc);

        signupLink.setOnClickListener(v -> {
            Log.d("LoginActivity", "Navigating to SignupActivity...");
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        Button loginButton = findViewById(R.id.btnlogin);

        loginButton.setOnClickListener(v -> {
            Log.d("LoginActivity", "Navigating to PassengerDashboard...");
            Intent intent = new Intent(LoginActivity.this, PassengerDashboard.class);
            startActivity(intent);
        }
        );
    }


}
