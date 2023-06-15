package com.lbeul.shotspots_v2.views.locations;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.controllers.extraction.ImageDataExtractor;
import com.lbeul.shotspots_v2.databinding.ActivityUploadImageBinding;
import com.lbeul.shotspots_v2.controllers.extraction.NativeImageDataExtractor;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.InMemoryDatabase;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.ListBasedInMemoryDatabase;

public class ImageUploadActivity extends AppCompatActivity {
    ActivityUploadImageBinding binding;
    Uri imageUri;

    InMemoryDatabase db = ListBasedInMemoryDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.extractExifButton.setEnabled(false);

        ActivityResultLauncher<String> launcher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    binding.thumbnail.setImageURI(uri);
                    binding.thumbnail.setBackgroundColor(0xfff);
                    binding.extractExifButton.setEnabled(true);
                    imageUri = uri;
                }
        );

        binding.backToMainButton.setOnClickListener(view -> finish());
        binding.loadImageButton.setOnClickListener(view -> launcher.launch("image/*"));
        binding.extractExifButton.setOnClickListener(view -> {
            ImageData newImageData = extractMetaData(imageUri);
            Toast.makeText(this, newImageData.toString(), Toast.LENGTH_LONG).show();
            db.addImageData(newImageData);
        });

    }

    private ImageData extractMetaData(Uri uri) {
        ImageDataExtractor extractor = new NativeImageDataExtractor(this, uri);
        return extractor.extractDataFromImage();
    }
}