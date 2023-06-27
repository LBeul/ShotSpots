package com.lbeul.shotspots_v2.controllers.persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.models.imageData.ImageDataImpl;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.InMemoryDatabase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
        } catch (IOException e ) {
            throw new PersistenceServiceException("Cannot read from file.");
        }
        // TODO: Use below string for testing, but remove from "src/"
        // String stubbedJSON = "[{\"cameraManufacturer\":\"Apple\",\"cameraModel\":\"iPhone 12 mini back dual wide camera 1.55mm f/2.4\",\"creationTimeStamp\":\"Nov 16, 2022 2:45:49 PM\",\"geoPoint\":{\"mAltitude\":0.0,\"mLatitude\":50.969094444444444,\"mLongitude\":7.0169194444444445},\"id\":\"26fe2179-4b1c-41f7-8a82-4b75bfb36752\",\"imageUri\":{},\"latitude\":50.969094444444444,\"longitude\":7.0169194444444445},{\"cameraManufacturer\":\"Apple\",\"cameraModel\":\"iPhone 12 mini back dual wide camera 4.2mm f/1.6\",\"creationTimeStamp\":\"Aug 6, 2022 4:31:36 PM\",\"geoPoint\":{\"mAltitude\":0.0,\"mLatitude\":52.52208611111111,\"mLongitude\":13.37105},\"id\":\"c8387e72-963c-445b-a538-5c1e72448ab2\",\"imageUri\":{},\"latitude\":52.52208611111111,\"longitude\":13.37105}]";
        Type implementedDataList = new TypeToken<List<ImageDataImpl>>(){}.getType();
        List<ImageDataImpl> imgDataList = gson.fromJson(sb.toString(), implementedDataList);
        return new ArrayList<>(imgDataList);
    }
}
