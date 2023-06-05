package com.lbeul.shotspots_v2.controllers;

import com.lbeul.shotspots_v2.models.ImageData;

import java.nio.file.Path;
import java.util.List;

public interface PersistenceService {
    /**
     * Persist existing list of imageData to local filesystem
     * @param path the path to the destination file
     * @param imageDataList the list of data to store
     */
    public void persistToFileSystem(Path path, List<ImageData> imageDataList);

    /**
     * Read local file and extract list of image data from it
     * @param path the path of the source file
     * @return List of valid ImageData objects
     */
    public List<ImageData> readFromFileSystem(Path path);
}
