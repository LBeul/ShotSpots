package com.lbeul.shotspots_v2.models.imageData;

import androidx.annotation.NonNull;

import org.osmdroid.util.GeoPoint;

import java.util.Date;
import java.util.UUID;

public interface ImageData {
    /**
     * Return ImageData file path and location
     * @return String
     */
    @NonNull
    String toString();

    /**
     * Get UUID of imageData object
     * @return id in UUID format
     */
    UUID getId();

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
