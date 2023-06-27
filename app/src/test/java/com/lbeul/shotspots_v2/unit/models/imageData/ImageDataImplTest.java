package com.lbeul.shotspots_v2.unit.models.imageData;

import static org.junit.Assert.*;

import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.models.imageData.ImageDataImpl;

import org.junit.Test;

import java.util.Date;

public class ImageDataImplTest {

    @Test
    public void testGetters() {
        String uri = "x";
        double lon = 5.0;
        double lat = 13.5;
        Date date = new Date();
        String camManufacturer = "y";
        String camModel = "z";

        ImageData imgData = new ImageDataImpl(uri, lon, lat, date, camManufacturer, camModel);

        assertEquals(uri, imgData.getImageURI());
        assertEquals(lon, imgData.getGeoPoint().getLongitude(), 0);
        assertEquals(lat, imgData.getGeoPoint().getLatitude(), 0);
        assertEquals(date, imgData.getCreationTimeStamp());
        assertEquals(camManufacturer, imgData.getCameraManufacturer());
        assertEquals(camModel, imgData.getCameraModel());
    }
}