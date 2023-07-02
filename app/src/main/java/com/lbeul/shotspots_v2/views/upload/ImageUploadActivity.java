package com.lbeul.shotspots_v2.views.upload;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.lbeul.shotspots_v2.controllers.extraction.ExtractorException;
import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.controllers.extraction.ImageDataExtractor;
import com.lbeul.shotspots_v2.databinding.ActivityUploadImageBinding;
import com.lbeul.shotspots_v2.controllers.extraction.NativeImageDataExtractor;
import com.lbeul.shotspots_v2.models.database.InMemoryDatabase;
import com.lbeul.shotspots_v2.models.database.ListBasedInMemoryDatabase;

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
            ImageData newImageData = null;
            try {
                newImageData = extractMetaData(imageUri);
                db.addImageData(newImageData);
                Toast.makeText(this, "Successfully extracted metadata", Toast.LENGTH_LONG).show();
            } catch (ExtractorException e) {
                Toast.makeText(this, "Metadata extraction failed!", Toast.LENGTH_LONG).show();
            }
            binding.extractExifButton.setEnabled(false);
        });

    }

    private ImageData extractMetaData(Uri uri) throws ExtractorException {
        ImageDataExtractor extractor = new NativeImageDataExtractor(this, uri);
        return extractor.extractDataFromImage();
    }
}