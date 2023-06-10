package com.lbeul.shotspots_v2.models;

import android.os.Build;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.lbeul.shotspots_v2.controllers.ImageData;
import com.lbeul.shotspots_v2.controllers.ImageDataExtractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class ImageDataExtractorImpl implements ImageDataExtractor {

    @Override
    public ImageData extractDataFromImage(Path path) throws IllegalArgumentException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            System.out.println("beginning extraction of: " + path.toAbsolutePath().toString());
        }
        // extract exif data
        try {
            Objects.requireNonNull(path, "Image path must not be null.");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (!Files.exists(path)) {
                    throw new IllegalArgumentException("Image file does not exist.");
                }
            }
            // there is an image.
            ImageData iData = new ImageDataImpl(path);

            Metadata metadata = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                metadata = ImageMetadataReader.readMetadata(path.toFile());
            }
            assert metadata != null;
            ExifSubIFDDirectory exifDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

            if (exifDirectory != null) {
                // set details
                iData.setCameraModel(exifDirectory.getDescription(ExifSubIFDDirectory.TAG_MODEL));
            }

            return iData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ImageProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
