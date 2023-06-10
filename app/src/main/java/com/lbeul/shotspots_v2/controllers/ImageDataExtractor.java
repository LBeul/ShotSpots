package com.lbeul.shotspots_v2.controllers;

import com.lbeul.shotspots_v2.models.ImageDataImpl;

import java.nio.file.Path;

public interface ImageDataExtractor {

    /**
     * Extract the metadata from a given file
     * @param path the path to the file to be analyzed
     * @return ImageData of the analyzed file
     */
    public ImageDataImpl extractDataFromImage(Path path);
}
