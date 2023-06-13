package com.lbeul.shotspots_v2.view.locations;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.lbeul.shotspots_v2.controllers.ImageData;
import com.lbeul.shotspots_v2.controllers.ImageDataExtractor;
import com.lbeul.shotspots_v2.databinding.ActivityLocationsBinding;
import com.lbeul.shotspots_v2.models.NativeImageDataExtractor;

public class LocationsActivity extends AppCompatActivity {
    ActivityLocationsBinding binding;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.extractExifButton.setEnabled(false);

        ActivityResultLauncher<String> launcher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        binding.thumbnail.setImageURI(uri);
                        binding.thumbnail.setBackgroundColor(0xfff);
                        binding.extractExifButton.setEnabled(true);
                        imageUri = uri;
                    }
                }
        );

        binding.mapButton.setOnClickListener(view -> finish());
        binding.loadImageButton.setOnClickListener(view -> launcher.launch("image/*"));
        binding.extractExifButton.setOnClickListener(view -> extractAndToastMetaData(imageUri));

    }

    private void extractAndToastMetaData(Uri uri) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ImageDataExtractor extractor = new NativeImageDataExtractor(this, uri);
            ImageData nativeImageData = extractor.extractDataFromImage();

            System.out.println(nativeImageData.toString());
            Toast.makeText(this, nativeImageData.toString(), Toast.LENGTH_LONG).show();
        }
    }
}