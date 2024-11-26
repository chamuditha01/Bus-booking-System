package com.example.busbookingsystem_madcw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        Log.d("LoginActivity", "LoginActivity loaded.");

        // Initialize Spinner
        Spinner roleSpinner = findViewById(R.id.spinnerrole);
        Button signupButton = findViewById(R.id.btnsignup);


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        // Get string array from resources
        String[] roles = getResources().getStringArray(R.array.role);

        // Set up ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set adapter to Spinner
        roleSpinner.setAdapter(adapter);

        // Set OnItemSelectedListener
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    // Disable "Select" option
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    // Handle valid selection
                    String selectedRole = roles[position];
                    Toast.makeText(SignupActivity.this, "Selected: " + selectedRole, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle no selection
            }




        });
    }
}
