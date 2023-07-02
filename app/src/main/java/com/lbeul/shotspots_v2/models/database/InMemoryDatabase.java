package com.lbeul.shotspots_v2.models.database;

import com.lbeul.shotspots_v2.models.imageData.ImageData;

import java.util.List;
import java.util.UUID;

public interface InMemoryDatabase {

    /**
     * Populates the database with an existing list of seed data
     * @param initialData the existing db image to replicate
     */
    void seed(List<ImageData> initialData) throws DatabaseException;

    /**
     * Adds a new ImageData entity to the collection
     * @param imgData the entity to be stored
     */
    void addImageData(ImageData imgData);

    /**
     * Removes a stored ImageData entity from the collection by ID
     * @param imageId the UUID of the entity that will be deleted
     * @return Object reference to the deleted entity
     */
    ImageData removeImageById(UUID imageId) throws DatabaseException;

    /**
     * Reads all entities that are currently stored in the collection
     * @return the current database content
     */
    List<ImageData> getAllImages();

    /**
     * Log current collection content to System.out
     */
    void logContent();
}
