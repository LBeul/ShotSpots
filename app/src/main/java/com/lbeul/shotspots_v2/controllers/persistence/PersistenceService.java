package com.lbeul.shotspots_v2.controllers.persistence;

import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.InMemoryDatabase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

public interface PersistenceService {
    /**
     * Persists existing InMemoryDatabase to local filesystem
     * @param fileOut the output stream to write a file to
     * @param db the database state to persist in the file system
     * @throws PersistenceServiceException if writing to filesystem failed
     */
    void persistToFileSystem(FileOutputStream fileOut, InMemoryDatabase db) throws PersistenceServiceException;

    /**
     * Read local file and extract list of image data from it
     * @param fileIn the input stream to read a file from
     * @return List of valid ImageData objects
     * @throws PersistenceServiceException if reading from filesystem failed
     */
    List<ImageData> readFromFileSystem(FileInputStream fileIn) throws PersistenceServiceException;
}
