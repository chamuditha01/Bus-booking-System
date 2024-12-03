package com.example.busbookingsystem_madcw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

public class OwnerDashboard extends AppCompatActivity {
    private int userId; // Variable to store the user ID
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_dashboard);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Retrieve the user ID from the Intent
        userId = getIntent().getIntExtra("user_id", -1); // Default to -1 if no user_id is passed
        Log.d("OwnerDashboard", "Received User ID: " + userId);

        // Initialize buttons and layout
        Button btnAddRoute = findViewById(R.id.btnbusandrouteadd);
        ImageButton profilebtn = findViewById(R.id.pimageButton3);
        ScrollView buttonContainer = findViewById(R.id.btncontainer); // LinearLayout for holding buttons

        // Get the list of buses from the database
        // Assuming this method returns the bus numbers from the database
        ArrayList<String> buses = databaseHelper.getAllBuses(userId); // Fetch bus numbers based on userId

// Find the LinearLayout that will hold the buttons
        LinearLayout buttonContainerLayout = findViewById(R.id.buttonContainerLayout);

// Loop through the bus list and create a Button for each bus number
        for (String bus : buses) {
            // Create a new Button
            Button busButton = new Button(this);

            // Set the text of the button to the bus number
            busButton.setText(bus);

            // Set LayoutParams to make the button take full width and wrap content height
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            busButton.setLayoutParams(params);

            // Optionally, set some properties like text size or click listeners
            busButton.setTextSize(20f);
            busButton.setOnClickListener(v -> {
                // Handle button click here
                Toast.makeText(this, "You clicked on bus " + bus, Toast.LENGTH_SHORT).show();
            });

            // Add the button to the LinearLayout
            buttonContainerLayout.addView(busButton);
        }


        // Set up Add Route button
        btnAddRoute.setOnClickListener(v -> {
            Intent intent = new Intent(OwnerDashboard.this, RouteandBusAdd.class);
            // Pass the user ID to the next activity
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        // Set up Profile button
        profilebtn.setOnClickListener(v -> {
            Intent intent = new Intent(OwnerDashboard.this, UserProfile.class);
            // Pass the user ID to the next activity
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });
    }
}
