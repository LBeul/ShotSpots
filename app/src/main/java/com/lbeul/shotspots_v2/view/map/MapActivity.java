package com.lbeul.shotspots_v2.view.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.preference.PreferenceManager;

import com.lbeul.shotspots_v2.controllers.ImageData;
import com.lbeul.shotspots_v2.controllers.ImageDataExtractor;
import com.lbeul.shotspots_v2.models.ImageDataExtractorImpl;
import com.lbeul.shotspots_v2.models.ImageDataImpl;
import com.lbeul.shotspots_v2.view.locations.LocationsActivity;
import com.lbeul.shotspots_v2.R;
import com.lbeul.shotspots_v2.databinding.ActivityMapBinding;

import android.widget.Button;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {
    private ActivityMapBinding binding;
    private MapView map = null;

    List<Marker> markers = new ArrayList<>();

    Button locationsButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeMap();
        placeOnMap(new GeoPoint(52.3, 13.4));

        locationsButton = findViewById(R.id.location_button);
        locationsButton.setOnClickListener(v -> {
            Intent i = new Intent(MapActivity.this, LocationsActivity.class);
            System.out.println("click");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                ImageData iData = new ImageDataExtractorImpl().extractDataFromImage(Paths.get("test-res/test.jpg"));
            }
            startActivity(i);
        });

    }

    private void initializeMap() {
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMinZoomLevel(7.0);
        map.setMultiTouchControls(true);
        // Create and configure mapController
        IMapController mapController = map.getController();
        mapController.setZoom(9.5);
        GeoPoint startPoint = new GeoPoint(52.4, 13.3);
        mapController.setCenter(startPoint);
    }

    public void placeOnMap(GeoPoint startPoint){
        Marker startMarker = new Marker(map);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);

        markers.add(startMarker);
        map.getOverlays().add(startMarker);
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