package com.example.busbookingsystem_madcw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class BusSheats extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_sheats);

        String busno=getIntent().getStringExtra("busNumber");
        int user_id= getIntent().getIntExtra("user_id", -1);
        String selectedDate = getIntent().getStringExtra("selectedDate");
        String bus_id= getIntent().getStringExtra("busId");

        Log.d("busid", "BusSheats loaded." + bus_id + " " + user_id + " " + selectedDate + " " + busno);

        TextView bsno=findViewById(R.id.txtbusnumber);
        bsno.setText(busno);

        Log.d("BusSheats", "BusSheats loaded.");
    }
}
