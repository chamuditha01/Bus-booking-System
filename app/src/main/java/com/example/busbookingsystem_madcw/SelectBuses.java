package com.example.busbookingsystem_madcw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.busbookingsystem_madcw.BusSheats;
import com.example.busbookingsystem_madcw.DatabaseHelper;

import java.util.HashMap;
import java.util.List;

public class SelectBuses extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_buses);
        Log.d("SelectBuses", "SelectBuses loaded.");

        // Get inputs from the Intent
        String selectedDate = getIntent().getStringExtra("selectedDate");
        String txtfrom = getIntent().getStringExtra("from");
        String txtto = getIntent().getStringExtra("to");
        int user_id= getIntent().getIntExtra("user_id", -1);

        // Fetch available bus routes
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<HashMap<String, String>> busRoutes = dbHelper.getBusNumbersAndRoutes(txtfrom, txtto);

        LinearLayout busListContainer = findViewById(R.id.busListContainer);

        if (busRoutes.isEmpty()) {
            Toast.makeText(this, "No buses available for the selected route.", Toast.LENGTH_SHORT).show();
        } else {
            for (HashMap<String, String> bus : busRoutes) {
                // Create a button for each bus
                Button busButton = new Button(this);
                String busNumber = bus.get("bus_number");
                String route = bus.get("route");
                String busIdStr = bus.get("bid");


                busButton.setText(busNumber + " - " + route);
                busButton.setOnClickListener(v -> {
                    Log.d("SelectBuses", "Selected Bus: " + busNumber);
                    Intent intent = new Intent(SelectBuses.this, BusSheats.class);
                    intent.putExtra("busNumber", busNumber);
                    intent.putExtra("selectedDate", selectedDate);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("busId", busIdStr);
                    Log.d("Tbusiddd", busIdStr);
                    startActivity(intent);
                });

                // Add the button to the LinearLayout
                busListContainer.addView(busButton);
            }
        }
    }
}
