package com.lbeul.shotspots_v2.controllers.persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.models.imageData.ImageDataImpl;
import com.lbeul.shotspots_v2.models.database.InMemoryDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class JSONPersistenceService implements PersistenceService {

    private final Gson gson;

    public JSONPersistenceService() {
        this.gson = new Gson();
    }

    @Override
    public void persistToFileSystem(OutputStream fileOut, InMemoryDatabase db) throws PersistenceServiceException {
        List<ImageData> images = db.getAllImages();
        String imagesJson = gson.toJson(images);
        try {
            fileOut.write(imagesJson.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new PersistenceServiceException("Writing to the filesystem failed");
        }
    }

    @Override
    public List<ImageData> readFromFileSystem(InputStream fileIn) throws PersistenceServiceException {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(fileIn));
            while ((line = inputReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new PersistenceServiceException("Cannot read from file.");
        }
        Type implementedDataList = new TypeToken<List<ImageDataImpl>>() {}.getType();
        List<ImageDataImpl> imgDataList = gson.fromJson(sb.toString(), implementedDataList);
        return new ArrayList<>(imgDataList);
    }
}
