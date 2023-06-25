package com.lbeul.shotspots_v2.views.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lbeul.shotspots_v2.controllers.persistence.JSONPersistenceService;
import com.lbeul.shotspots_v2.controllers.persistence.PersistenceService;
import com.lbeul.shotspots_v2.controllers.persistence.PersistenceServiceException;
import com.lbeul.shotspots_v2.databinding.ActivityMainBinding;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.DatabaseException;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.InMemoryDatabase;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.ListBasedInMemoryDatabase;
import com.lbeul.shotspots_v2.views.upload.ImageUploadActivity;
import com.lbeul.shotspots_v2.views.map.MapActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final InMemoryDatabase database = ListBasedInMemoryDatabase.getInstance();
    private final String dbDumpFileName = "shotspots_dump.json";
    PersistenceService persistenceService = new JSONPersistenceService();

    LocationRecyclerViewAdapter adapter = new LocationRecyclerViewAdapter(this, database);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadDatabase();

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recycler = binding.locationsRecycler;
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        binding.goToUpload.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, ImageUploadActivity.class);
            startActivity(i);
        });
        binding.goToMapView.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, MapActivity.class);
            startActivity(i);
        });

        binding.saveDB.setOnClickListener(view -> saveCurrentDatabaseState());
    }

    private void saveCurrentDatabaseState() {
        try (FileOutputStream fileOut = openFileOutput(dbDumpFileName, MODE_PRIVATE)) {
            persistenceService.persistToFileSystem(fileOut, database);
            Toast.makeText(this, "Database saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Filesystem access failed - Please restart app!", Toast.LENGTH_LONG).show();
        } catch (PersistenceServiceException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDatabase() {
        try (FileInputStream fileIn = openFileInput(dbDumpFileName)) {
            database.seed(persistenceService.readFromFileSystem(fileIn));
            Toast.makeText(this, "Database loaded", Toast.LENGTH_SHORT).show();
            database.logContent();
        } catch (PersistenceServiceException | DatabaseException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Filesystem access failed - Please restart app!", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}