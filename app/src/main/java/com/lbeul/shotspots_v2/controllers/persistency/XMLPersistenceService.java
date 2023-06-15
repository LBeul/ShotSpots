package com.lbeul.shotspots_v2.controllers.persistency;

import com.lbeul.shotspots_v2.models.imageData.ImageDataImpl;

import java.nio.file.Path;
import java.util.List;

public class XMLPersistenceService implements PersistenceService {
    @Override
    public void persistToFileSystem(Path path, List<ImageDataImpl> imageDataList) {

    }

    @Override
    public List<ImageDataImpl> readFromFileSystem(Path path) {
        return null;
    }

}
