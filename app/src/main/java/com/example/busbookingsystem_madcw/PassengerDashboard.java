package com.example.busbookingsystem_madcw;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageButton;

import java.util.Calendar;

public class PassengerDashboard extends AppCompatActivity {

    private EditText dateInput;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_dashboard);

        userId= getIntent().getIntExtra("user_id", -1);
        // Initialize UI components
        Button btnSearch = findViewById(R.id.btnsearch);
        dateInput = findViewById(R.id.dateInput); // Add EditText for date selection
        EditText txtFrom = findViewById(R.id.txtfrom);
        EditText txtTo = findViewById(R.id.txtto);
        // Set click listener for the DatePicker
        dateInput.setOnClickListener(v -> showDatePicker());

        // Set click listener for the search button
        btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(PassengerDashboard.this, SelectBuses.class);
            intent.putExtra("user_id", userId);
            intent.putExtra("selectedDate", dateInput.getText().toString().trim());
            intent.putExtra("from", txtFrom.getText().toString().trim());
            intent.putExtra("to", txtTo.getText().toString().trim());

            startActivity(intent);
        });

        ImageButton btnp= findViewById(R.id.pimageButton);

        btnp.setOnClickListener(v -> {
            Intent intent = new Intent(PassengerDashboard.this, UserProfile.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);

        });
    }

    /**
     * Displays the DatePickerDialog for selecting a date.
     */
    private void showDatePicker() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the selected date
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    dateInput.setText(date);
                    Toast.makeText(this, "Date Selected: " + date, Toast.LENGTH_SHORT).show();
                },
                year, month, day);

        datePickerDialog.show();
    }

    /**
     * Navigates to the SelectBuses activity.
     */
    private void navigateToSelectBuses() {
        // Ensure a date is selected
        String selectedDate = dateInput.getText().toString().trim();
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select a date first.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proceed to SelectBuses activity
        Intent intent = new Intent(PassengerDashboard.this, SelectBuses.class);
        intent.putExtra("selectedDate", selectedDate); // Pass the selected date to the next activity
        startActivity(intent);
    }
}
