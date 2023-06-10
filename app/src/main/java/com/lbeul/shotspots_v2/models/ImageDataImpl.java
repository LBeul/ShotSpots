package com.lbeul.shotspots_v2.models;

import androidx.annotation.NonNull;

import com.lbeul.shotspots_v2.controllers.ImageData;

import org.osmdroid.util.GeoPoint;

import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;

public class ImageDataImpl implements ImageData {
    private final UUID id;
    private final Path imageUri;
    private double longitude;
    private double latitude;
    private GeoPoint geoPoint;
    //private final GeoLocation location;
    private Date creationTimeStamp;
    private String cameraManufacturer;
    private String cameraModel;



    public ImageDataImpl(Path imageUri) {

        // perform Data Extraction
        // store data in class somewhere

        this.id = UUID.randomUUID();
        this.imageUri = imageUri;

        //this.location = location;
    }

    // setters
    @Override
    public void setLocation(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.geoPoint = new GeoPoint(latitude, longitude);
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
    public GeoPoint getGeoPoint() {
        return this.geoPoint;
    }

    @Override
    public String getImageURI() {
        return this.imageUri.toString();
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
        StringBuilder sb = new StringBuilder();

        // path
        sb.append(this.id.toString()).append(": ").append(this.getImageURI())
                .append(System.lineSeparator());

        // location
        sb.append("Location: ").append(this.longitude).append(" - ").append(this.latitude)
                .append(System.lineSeparator());

        // details
        sb.append(cameraManufacturer).append(System.lineSeparator());
        sb.append(cameraModel).append(System.lineSeparator());

        // creation date
        sb.append(creationTimeStamp);

        return sb.toString();
    }
}
