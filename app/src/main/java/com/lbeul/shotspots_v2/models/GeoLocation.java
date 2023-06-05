package com.lbeul.shotspots_v2.models;

import org.osmdroid.util.GeoPoint;

public class GeoLocation {
    private final double longitude;
    private final double latitude;

    public GeoLocation(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public GeoPoint toGeoPoint() {
        return new GeoPoint(latitude, longitude);
    }
}
