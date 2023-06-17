package com.lbeul.shotspots_v2.controllers.persistence;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.models.imageData.ImageDataImpl;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.InMemoryDatabase;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class JSONPersistenceService implements PersistenceService{

    private final Gson gson;

    public JSONPersistenceService() {
        this.gson = new Gson();
    }

    @Override
    public void persistToFileSystem(Uri fileUri, InMemoryDatabase db) {
        // Extract db entries to List
        List<ImageData> images = db.getAllImages();

        // Convert List to JSON string
        String imagesJson = gson.toJson(images);

        // Create new file && write JSON to file
        System.out.println(imagesJson);
    }

    @Override
    public List<ImageData> readFromFileSystem(Uri fileUri) {
        // TODO: Read string form file instead of stub
        String stubbedJSON = "[{\"cameraManufacturer\":\"Apple\",\"cameraModel\":\"iPhone 12 mini back dual wide camera 4.2mm f/1.6\",\"creationTimeStamp\":\"Nov 30, 2022 10:05:58 PM\",\"geoPoint\":{\"mAltitude\":0.0,\"mLatitude\":48.127725,\"mLongitude\":11.561694444444445},\"id\":\"d3296975-734c-48db-b175-e2494308fc2b\",\"imageUri\":{},\"latitude\":48.127725,\"longitude\":11.561694444444445},{\"cameraManufacturer\":\"Apple\",\"cameraModel\":\"iPhone 12 mini back dual wide camera 4.2mm f/1.6\",\"creationTimeStamp\":\"Dec 24, 2021 9:48:06 PM\",\"geoPoint\":{\"mAltitude\":0.0,\"mLatitude\":50.63503333333333,\"mLongitude\":8.114527777777777},\"id\":\"5f57a4aa-9f09-4bea-b8cc-ab7c0379d54c\",\"imageUri\":{},\"latitude\":50.63503333333333,\"longitude\":8.114527777777777},{\"cameraManufacturer\":\"Apple\",\"cameraModel\":\"iPhone 12 mini back dual wide camera 4.2mm f/1.6\",\"creationTimeStamp\":\"Jul 15, 2022 6:05:45 PM\",\"geoPoint\":{\"mAltitude\":0.0,\"mLatitude\":49.89106666666667,\"mLongitude\":10.88656111111111},\"id\":\"0635579b-0cff-40e4-b2ea-47486aeab1ae\",\"imageUri\":{},\"latitude\":49.89106666666667,\"longitude\":10.88656111111111},{\"cameraManufacturer\":\"Apple\",\"cameraModel\":\"iPhone 12 mini back dual wide camera 1.55mm f/2.4\",\"creationTimeStamp\":\"Nov 16, 2022 2:45:49 PM\",\"geoPoint\":{\"mAltitude\":0.0,\"mLatitude\":50.969094444444444,\"mLongitude\":7.0169194444444445},\"id\":\"26fe2179-4b1c-41f7-8a82-4b75bfb36752\",\"imageUri\":{},\"latitude\":50.969094444444444,\"longitude\":7.0169194444444445},{\"cameraManufacturer\":\"Apple\",\"cameraModel\":\"iPhone 12 mini back dual wide camera 4.2mm f/1.6\",\"creationTimeStamp\":\"Aug 6, 2022 4:31:36 PM\",\"geoPoint\":{\"mAltitude\":0.0,\"mLatitude\":52.52208611111111,\"mLongitude\":13.37105},\"id\":\"c8387e72-963c-445b-a538-5c1e72448ab2\",\"imageUri\":{},\"latitude\":52.52208611111111,\"longitude\":13.37105},{\"cameraManufacturer\":\"Apple\",\"cameraModel\":\"iPhone 11 Pro back triple camera 4.25mm f/1.8\",\"creationTimeStamp\":\"Mar 31, 2023 4:42:28 PM\",\"geoPoint\":{\"mAltitude\":0.0,\"mLatitude\":52.521633333333334,\"mLongitude\":13.529102777777778},\"id\":\"68a87c26-9faf-4d6b-9c01-167f10ccb765\",\"imageUri\":{},\"latitude\":52.521633333333334,\"longitude\":13.529102777777778}]";

        // Create type of concrete classes because gson can't instantiate interfaces
        Type implementedDataList = new TypeToken<List<ImageDataImpl>>() {}.getType();

        // Instantiate List of implemented interface class
        List<ImageDataImpl> imgDataList = gson.fromJson(stubbedJSON, implementedDataList);

        // Return implicitly casted list
        return new ArrayList<>(imgDataList);
    }
}
