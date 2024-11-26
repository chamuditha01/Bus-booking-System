package com.example.busbookingsystem_madcw;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        Log.d(TAG, "Landing page loaded.");

        // Delay for 4 seconds before moving to LoginActivity
        new Handler().postDelayed(() -> {
            Log.d(TAG, "Navigating to LoginActivity...");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 3000); // 4000ms = 4 seconds
    }
}