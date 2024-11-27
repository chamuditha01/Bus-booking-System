package com.example.busbookingsystem_madcw;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.busbookingsystem_madcw.DatabaseHelper;
import com.example.busbookingsystem_madcw.DriverDashboard;
import com.example.busbookingsystem_madcw.PassengerDashboard;
import com.example.busbookingsystem_madcw.SignupActivity;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Log.d("LoginActivity", "LoginActivity loaded.");

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // UI elements
        TextView signupLink = findViewById(R.id.dnthvacc);
        EditText emailInput = findViewById(R.id.txtloginemail);
        EditText passwordInput = findViewById(R.id.txtloginpassword);
        Button loginButton = findViewById(R.id.btnlogin);

        // Navigate to SignupActivity
        signupLink.setOnClickListener(v -> {
            Log.d("LoginActivity", "Navigating to SignupActivity...");
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        // Login functionality
        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate user using DatabaseHelper
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            String query = "SELECT role FROM users WHERE email = ? AND password = ?";
            try (Cursor cursor = db.rawQuery(query, new String[]{email, password})) {
                if (cursor.moveToFirst()) {
                    String role = cursor.getString(0);
                    Log.d("LoginActivity", "User role: " + role);

                    Intent intent;
                    switch (role.toLowerCase()) {
                        case "passenger":
                            intent = new Intent(LoginActivity.this, PassengerDashboard.class);
                            break;
                        case "driver":
                            intent = new Intent(LoginActivity.this, DriverDashboard.class);
                            break;
                        case "owner":
                            intent = new Intent(LoginActivity.this, DriverDashboard.class);
                            break;
                        default:
                            Toast.makeText(this, "Invalid role", Toast.LENGTH_SHORT).show();
                            return;
                    }
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
