package com.lbeul.shotspots_v2.controllers.extraction;

import com.lbeul.shotspots_v2.models.imageData.ImageData;

public interface ImageDataExtractor {

    /**
     * Extract the metadata from a given file
     *
     * @return ImageData of the analyzed file
     * @throws RuntimeException if there is no image at path, no geo location, no meta data
     */
    ImageData extractDataFromImage() throws RuntimeException;
}
