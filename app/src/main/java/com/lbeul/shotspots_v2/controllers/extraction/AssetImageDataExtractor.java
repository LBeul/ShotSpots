package com.lbeul.shotspots_v2.controllers.extraction;

import android.content.Context;
import android.os.Build;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.models.imageData.ImageDataImpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;

public class AssetImageDataExtractor implements ImageDataExtractor {

    private final Path imagePath;
    private final Context context;

    public AssetImageDataExtractor(Path imagePath, Context context) {
        this.imagePath = imagePath;
        this.context = context;
    }

    @Override
    public ImageData extractDataFromImage() throws RuntimeException {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            throw new RuntimeException("SDK version is too old to run this.");
        }

        System.out.println("beginning extraction of: " + this.imagePath.toAbsolutePath().toString());

        Objects.requireNonNull(this.imagePath, "Image path must not be null.");

        // get file
        InputStream is = readInputStreamFromAssets(this.imagePath);

        // get file metadata
        Metadata metadata = readMetadataFromStream(is);

        // get exif directory
        ExifSubIFDDirectory exifDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

        // GPS Directory
        GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);

        if (gpsDirectory == null || !(gpsDirectory.containsTag(GpsDirectory.TAG_LATITUDE)) ||
                !(gpsDirectory.containsTag(GpsDirectory.TAG_LONGITUDE))) {
            throw new RuntimeException("No Location data was found.");
        }

        //debugPrintAllTags(metadata);

        // set values of ImageData
        ImageData iData = new ImageDataImpl(this.imagePath.toString());

        // set longitude and latitude
        iData.setLocation(gpsDirectory.getGeoLocation().getLongitude(),
                gpsDirectory.getGeoLocation().getLatitude());

        // set timestamp
        iData.setCreationTimeStamp(exifDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL));

        // set camera details
        iData.setCameraManufacturer(exifDirectory.getString(ExifSubIFDDirectory.TAG_LENS_MAKE));
        iData.setCameraModel(exifDirectory.getString(ExifSubIFDDirectory.TAG_LENS_MODEL));

        System.out.println(iData);

        return iData;
    }

    private InputStream readInputStreamFromAssets(Path path) throws RuntimeException {
        try {
            return context.getAssets().open(path.toString());
        } catch (IOException e) {
            throw new RuntimeException("File could not be loaded.");
        }
    }

    private Metadata readMetadataFromStream(InputStream is) throws RuntimeException {
        try {
            return ImageMetadataReader.readMetadata(is);
        } catch (ImageProcessingException | IOException e) {
            throw new RuntimeException("No metadata was found.");
        }
    }

    private void debugPrintAllTags(Metadata metadata) {
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }
    }
}
