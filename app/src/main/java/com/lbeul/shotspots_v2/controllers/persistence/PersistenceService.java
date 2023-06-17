package com.lbeul.shotspots_v2.controllers.persistence;

import android.net.Uri;

import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.InMemoryDatabase;

import java.util.List;

public interface PersistenceService {
    /**
     * Persists existing InMemoryDatabase to local filesystem
     * @param fileUri the URI of the destination file
     * @param db the database state to persist in the file system
     */
    public void persistToFileSystem(Uri fileUri, InMemoryDatabase db);

    /**
     * Read local file and extract list of image data from it
     * @param fileUri the URI of the source file
     * @return List of valid ImageData objects
     */
    public List<ImageData> readFromFileSystem(Uri fileUri);
}
