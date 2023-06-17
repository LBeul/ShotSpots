package com.lbeul.shotspots_v2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lbeul.shotspots_v2.controllers.persistence.JSONPersistenceService;
import com.lbeul.shotspots_v2.controllers.persistence.PersistenceService;
import com.lbeul.shotspots_v2.controllers.persistence.PersistenceServiceException;
import com.lbeul.shotspots_v2.databinding.ActivityMainBinding;
import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.DatabaseException;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.InMemoryDatabase;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.ListBasedInMemoryDatabase;
import com.lbeul.shotspots_v2.views.locations.ImageUploadActivity;
import com.lbeul.shotspots_v2.views.map.MapActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final InMemoryDatabase database = ListBasedInMemoryDatabase.getInstance();
    private final String dbDumpFileName = "shotspots_dump.json";
    PersistenceService persistenceService = new JSONPersistenceService();

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

        binding.saveDB.setOnClickListener(view -> saveCurrentDatabaseState());
        binding.loadDB.setOnClickListener(view -> loadDatabase());
    }

    private void saveCurrentDatabaseState() {
        try (FileOutputStream fileOut = openFileOutput(dbDumpFileName, MODE_PRIVATE)) {
            persistenceService.persistToFileSystem(fileOut, database);
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + dbDumpFileName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Filesystem access failed - Please restart app!", Toast.LENGTH_LONG).show();
        } catch (PersistenceServiceException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDatabase() {
        try (FileInputStream fileIn = openFileInput(dbDumpFileName)) {
            database.seed(persistenceService.readFromFileSystem(fileIn));
            Toast.makeText(this, "Loaded " + getFilesDir() + "/" + dbDumpFileName, Toast.LENGTH_SHORT).show();
            database.logContent();
        } catch (PersistenceServiceException | DatabaseException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Filesystem access failed - Please restart app!", Toast.LENGTH_LONG).show();
        }
    }
}