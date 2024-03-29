package com.lbeul.shotspots_v2.controllers.extraction;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.models.imageData.ImageDataImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class NativeImageDataExtractor implements ImageDataExtractor {
    private final ContentResolver resolver;
    private final Uri imageUri;

    public NativeImageDataExtractor(AppCompatActivity activity, Uri imageUri) {
        this.resolver = activity.getContentResolver();
        this.imageUri = imageUri;
        System.out.println(imageUri);
    }

    @Override
    public ImageData extractDataFromImage() throws ExtractorException {
        Metadata metadata;

        Objects.requireNonNull(imageUri, "Image uri must not be null.");

        try (InputStream is = resolver.openInputStream(imageUri)) {
            metadata = ImageMetadataReader.readMetadata(is);
        } catch (IOException | ImageProcessingException e) {
            throw new ExtractorException(e.getMessage());
        }

        ExifSubIFDDirectory exifDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);

        if (gpsDirectory == null || !coordinatesAreComplete(gpsDirectory)) {
            throw new RuntimeException("GPS coordinates are incomplete!");
        }

        return new ImageDataImpl(
                imageUri.getPath(),
                gpsDirectory.getGeoLocation().getLongitude(),
                gpsDirectory.getGeoLocation().getLatitude(),
                exifDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL),
                exifDirectory.getString(ExifSubIFDDirectory.TAG_LENS_MAKE),
                exifDirectory.getString(ExifSubIFDDirectory.TAG_LENS_MODEL));
    }

    private boolean coordinatesAreComplete(GpsDirectory gpsData) {
        return gpsData.containsTag(GpsDirectory.TAG_LATITUDE) && gpsData.containsTag(GpsDirectory.TAG_LONGITUDE);
    }
}
