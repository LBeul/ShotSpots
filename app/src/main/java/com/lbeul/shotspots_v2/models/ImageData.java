package com.lbeul.shotspots_v2.models;

import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;

public class ImageData {
    private UUID id;
    private Path imageUri;
    private GeoLocation location;
    private Date timeStamp;

    public ImageData() {
        // perform Data Extraction
        // store data in class somewhere
    }
}
