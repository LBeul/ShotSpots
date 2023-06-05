package com.lbeul.shotspots_v2.view.locations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.lbeul.shotspots_v2.R;
import com.lbeul.shotspots_v2.view.map.MapActivity;

public class LocationsActivity extends AppCompatActivity {
    Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        mapButton = findViewById(R.id.map_button);
        mapButton.setOnClickListener(v -> {
            Intent i = new Intent(LocationsActivity.this, MapActivity.class);
            startActivity(i);
        });
    }
}