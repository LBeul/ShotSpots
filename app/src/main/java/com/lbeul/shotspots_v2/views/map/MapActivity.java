package com.lbeul.shotspots_v2.views.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.preference.PreferenceManager;

import com.lbeul.shotspots_v2.databinding.ActivityMapBinding;
import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.InMemoryDatabase;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.ListBasedInMemoryDatabase;
import com.lbeul.shotspots_v2.views.upload.ImageUploadActivity;
import com.lbeul.shotspots_v2.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {
    private MapView map;

    List<Marker> markers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        com.lbeul.shotspots_v2.databinding.ActivityMapBinding binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeMap();
        dropMarkerForEachImage(ListBasedInMemoryDatabase.getInstance());

        binding.locationButton.setOnClickListener(v -> {
            Intent i = new Intent(MapActivity.this, ImageUploadActivity.class);
            startActivity(i);
            finish();
        });

        binding.backToMainButton.setOnClickListener(view -> finish());

    }

    private void initializeMap() {
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMinZoomLevel(7.0);
        map.setMultiTouchControls(true);
        // Create and configure mapController
        IMapController mapController = map.getController();
        mapController.setZoom(7.5);
        GeoPoint startPoint = new GeoPoint(51.31, 10.48);
        mapController.setCenter(startPoint);
    }

    public void dropMarkerForEachImage(InMemoryDatabase database){
        for (ImageData imgData: database.getAllImages()) {
            Marker marker = new Marker(map);
            marker.setTitle("Shot on " + imgData.getCameraModel());
            marker.setPosition(imgData.getGeoPoint());
            markers.add(marker);
            map.getOverlays().add(marker);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }
}