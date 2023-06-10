package com.lbeul.shotspots_v2.controllers;

import java.nio.file.Path;

public interface ImageDataExtractor {

    /**
     * Extract the metadata from a given file
     *
     * @param path the path to the file to be analyzed
     * @return ImageData of the analyzed file
     * @throws IllegalArgumentException if there is no image at path
     */
    public ImageData extractDataFromImage(Path path) throws IllegalArgumentException;
}
