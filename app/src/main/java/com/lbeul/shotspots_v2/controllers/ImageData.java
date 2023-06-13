package com.lbeul.shotspots_v2.controllers;

import androidx.annotation.NonNull;

import org.osmdroid.util.GeoPoint;

import java.sql.Struct;
import java.sql.Timestamp;
import java.util.Date;

public interface ImageData {
    /**
     * Return ImageData file path and location
     * @return String
     */
    @NonNull
    String toString();

    /**
     * Set the geo coordinates and GeoPoint of image
     * @param longitude double longitude
     * @param latitude double latitude
     */
    void setLocation(double longitude, double latitude);

    /**
     * Set timestamp of image creation
     * @param creationTimeStamp Date of creation
     */
    void setCreationTimeStamp(Date creationTimeStamp);

    /**
     * Set camera manufacturer of camera that took the image
     * @param cameraManufacturer String camera manufacturer
     */
    void setCameraManufacturer(String cameraManufacturer);

    /**
     * Set camera model of camera that took the image
     * @param cameraModel String camera model
     */
    void setCameraModel(String cameraModel);

    /**
     * Return geoPoint
     * @return GeoPoint
     */
    GeoPoint getGeoPoint();

    /**
     * Return system path to Image
     * @return String
     */
    String getImageURI();

    /**
     * Return timestamp of image creation
     * @return Date
     */
    Date getCreationTimeStamp();

    /**
     * Return camera manufacturer of camera that took the image
     * @return String
     */
    String getCameraManufacturer();

    /**
     * Return camera model of camera that took the image
     * @return String
     */
    String getCameraModel();
}
