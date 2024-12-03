package com.example.busbookingsystem_madcw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class UserProfile extends AppCompatActivity {

    private int userId; // Variable to store the user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        // Retrieve user ID passed through Intent
        userId = getIntent().getIntExtra("user_id", -1);

        // Initialize EditText fields
        EditText txtName = findViewById(R.id.txtnameprofile);
        EditText txtEmail = findViewById(R.id.txtemailprofile);
        EditText txtRole = findViewById(R.id.txtroleprofile);
        EditText txtp = findViewById(R.id.txtpasswordprofile);

        if (userId != -1) {
            // Fetch user details from the database
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            HashMap<String, String> userDetails = dbHelper.getUserDetails(userId);

            if (!userDetails.isEmpty()) {
                // Populate EditText fields with user details
                txtName.setText(userDetails.get("name"));
                txtEmail.setText(userDetails.get("email"));
                txtRole.setText(userDetails.get("role"));
                txtRole.setText(userDetails.get("role"));
                txtp.setText(userDetails.get("password"));

                // Additional handling for profile picture if required
                String profilePicture = userDetails.get("profile_picture");
                if (profilePicture != null && !profilePicture.isEmpty()) {
                    // Example: Use a library like Glide or Picasso to load the profile picture
                    // Glide.with(this).load(profilePicture).into(profileImageView);
                }
            } else {
                Toast.makeText(this, "User details not found!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Invalid User ID!", Toast.LENGTH_SHORT).show();
        }
    }
}
