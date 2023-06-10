package com.lbeul.shotspots_v2.models;

import com.lbeul.shotspots_v2.controllers.ImageData;

import org.osmdroid.util.GeoPoint;

import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;

public class ImageDataImpl implements ImageData {
    private final UUID id;
    private final Path imageUri;

    private final double longitude;
    private final double latitude;
    private final GeoPoint geoPoint;

    private final GeoLocation location;
    private final Date creationTimeStamp;
    private final String cameraManufacturer;
    private final String cameraModel;



    public ImageDataImpl() {
        // perform Data Extraction
        // store data in class somewhere



        this.id = UUID.randomUUID();
        this.imageUri = imageUri;
        this.longitude = longitude;
        this.latitude = latitude;
        this.geoPoint = geoPoint;
        this.location = location;
        this.creationTimeStamp = creationTimeStamp;
        this.cameraManufacturer = cameraManufacturer;
        this.cameraModel = cameraModel;
    }
}
