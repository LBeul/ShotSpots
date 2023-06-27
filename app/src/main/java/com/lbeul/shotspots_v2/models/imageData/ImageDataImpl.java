package com.lbeul.shotspots_v2.models.imageData;

import androidx.annotation.NonNull;

import org.osmdroid.util.GeoPoint;

import java.util.Date;
import java.util.UUID;

public class ImageDataImpl implements ImageData {
    private final UUID id;
    private final String uriString;
    private final double longitude;
    private final double latitude;
    private final Date creationTimeStamp;
    private final String cameraManufacturer;
    private final String cameraModel;

    public ImageDataImpl(String uriString, double longitude, double latitude, Date creationTimeStamp, String cameraManufacturer, String cameraModel) {
        this.id = UUID.randomUUID();
        this.uriString = uriString;
        this.longitude = longitude;
        this.latitude = latitude;
        this.creationTimeStamp = creationTimeStamp;
        this.cameraManufacturer = cameraManufacturer;
        this.cameraModel = cameraModel;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public GeoPoint getGeoPoint() {
        return new GeoPoint(this.latitude, this.longitude);
    }

    @Override
    public String getImageURI() {
        return this.uriString;
    }

    @Override
    public Date getCreationTimeStamp() {
        return creationTimeStamp;
    }

    @Override
    public String getCameraManufacturer() {
        return cameraManufacturer;
    }

    @Override
    public String getCameraModel() {
        return cameraModel;
    }

    @NonNull
    @Override
    public String toString() {
        return this.id.toString() + ": " + this.getImageURI() +
                System.lineSeparator() +
                "Location: " + this.longitude + " - " + this.latitude +
                System.lineSeparator() +
                cameraManufacturer + System.lineSeparator() +
                cameraModel + System.lineSeparator() +
                creationTimeStamp;
    }
}
