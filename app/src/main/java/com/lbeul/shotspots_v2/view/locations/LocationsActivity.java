package com.lbeul.shotspots_v2.view.locations;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import com.lbeul.shotspots_v2.databinding.ActivityLocationsBinding;

public class LocationsActivity extends AppCompatActivity {
    ActivityLocationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityResultLauncher<String> launcher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri link) {
                        binding.imageUrl.setText(link.toString());
                    }
                }
        );

        binding.mapButton.setOnClickListener(view -> finish());
        binding.loadImageButton.setOnClickListener(view -> launcher.launch("image/*"));

    }
}