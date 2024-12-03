package com.example.busbookingsystem_madcw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class BusSheats extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_sheats);
        Log.d("BusSheats", "BusSheats loaded.");
    }
}
