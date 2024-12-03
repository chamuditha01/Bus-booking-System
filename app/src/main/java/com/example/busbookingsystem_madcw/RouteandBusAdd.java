package com.example.busbookingsystem_madcw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class RouteandBusAdd extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private int userId; // Variable to store the user ID

    private int routeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_and_bus_add);

        // Initialize the UI elements
        Spinner routeSpinner = findViewById(R.id.spinnerroute);
        Spinner driverSpinner = findViewById(R.id.spinnerdriver);
        EditText busNumberInput = findViewById(R.id.txtbusnum);
        Button saveButton = findViewById(R.id.btnaddbus);

        // Retrieve user ID
        userId = getIntent().getIntExtra("user_id", -1); // Default to -1 if no user_id is passed
        Log.d("RouteandBusAdd", "Received User ID: " + userId);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Fetch routes from the database
        HashMap<String, Integer> routeMap = databaseHelper.getAllRoutes();
        ArrayList<String> routeDisplayList = new ArrayList<>(routeMap.keySet()); // Get display strings

// Populate the spinner
        ArrayAdapter<String> routeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, routeDisplayList);
        routeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        routeSpinner.setAdapter(routeAdapter);

// Retrieve route ID when saving
        String selectedRoute = (String) routeSpinner.getSelectedItem();
        routeId = routeMap.get(selectedRoute);

        // Fetch drivers from the database
        ArrayList<String> drivers = databaseHelper.getAllDrivers();


        // Populate the driver spinner with drivers
        ArrayAdapter<String> driverAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, drivers);
        driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driverSpinner.setAdapter(driverAdapter);

        // Save button logic
        saveButton.setOnClickListener(v -> {
            String busNumber = busNumberInput.getText().toString().trim();

            String selectedDriver = (String) driverSpinner.getSelectedItem();

            if (busNumber.isEmpty() || selectedRoute == null || selectedDriver == null) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert into the database
            boolean isInserted = databaseHelper.insertBus(busNumber, routeId, userId, selectedDriver);

            if (isInserted) {
                Toast.makeText(this, "Bus added successfully!", Toast.LENGTH_SHORT).show();
                finish(); // Close activity after saving
            } else {
                Toast.makeText(this, "Failed to add bus. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
