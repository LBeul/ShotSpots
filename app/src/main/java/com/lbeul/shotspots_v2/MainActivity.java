package com.lbeul.shotspots_v2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.lbeul.shotspots_v2.databinding.ActivityMainBinding;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.InMemoryDatabase;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.ListBasedInMemoryDatabase;
import com.lbeul.shotspots_v2.view.locations.ImageUploadActivity;
import com.lbeul.shotspots_v2.view.map.MapActivity;

public class MainActivity extends AppCompatActivity {
    private final InMemoryDatabase database = ListBasedInMemoryDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goToUpload.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, ImageUploadActivity.class);
            startActivity(i);
        });
        binding.goToMapView.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, MapActivity.class);
            startActivity(i);
        });
        
        // TODO: Only for debugging, remove when handed in!
        binding.logDB.setOnClickListener(view -> {
            database.logContent();
        });
    }
}