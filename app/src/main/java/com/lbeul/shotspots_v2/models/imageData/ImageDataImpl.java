package com.lbeul.shotspots_v2.models.imageData;

import androidx.annotation.NonNull;

import org.osmdroid.util.GeoPoint;

import java.util.Date;
import java.util.UUID;

public class ImageDataImpl implements ImageData {
    private final UUID id = UUID.randomUUID();
    private final String stringifiedImageUri;
    private double longitude;
    private double latitude;
    private Date creationTimeStamp;
    private String cameraManufacturer;
    private String cameraModel;


    public ImageDataImpl(String stringifiedImageUri) {

        // perform Data Extraction
        // store data in class somewhere

        this.stringifiedImageUri = stringifiedImageUri;

        //this.location = location;
    }

    // setters
    @Override
    public void setLocation(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public void setCreationTimeStamp(Date creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    @Override
    public void setCameraManufacturer(String cameraManufacturer) {
        this.cameraManufacturer = cameraManufacturer;
    }

    @Override
    public void setCameraModel(String cameraModel) {
        this.cameraModel = cameraModel;
    }

    // getters

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
        return this.stringifiedImageUri;
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
