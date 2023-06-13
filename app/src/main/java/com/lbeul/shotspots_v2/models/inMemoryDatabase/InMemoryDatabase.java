package com.lbeul.shotspots_v2.models.inMemoryDatabase;

import com.lbeul.shotspots_v2.models.imageData.ImageData;

import java.util.List;
import java.util.UUID;

public interface InMemoryDatabase {

    /**
     * Adds a new ImageData entity to the collection
     * @param imgData the enitity to be stored
     */
    public void addImageData(ImageData imgData);

    /**
     * Removes a stored ImageData entity from the collection by ID
     * @param imageId the UUID of the entity that will be deleted
     * @return Object reference to the deleted entity
     */
    public ImageData removeImageById(UUID imageId);

    /**
     * Reads all entities that are currently stored in the collection
     * @return
     */
    public List<ImageData> getAllImages();

    /**
     * Log current collection content to System.out
     */
    public void logContent();
}
