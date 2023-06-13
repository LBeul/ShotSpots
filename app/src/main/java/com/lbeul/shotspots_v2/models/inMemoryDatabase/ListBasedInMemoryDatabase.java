package com.lbeul.shotspots_v2.models.inMemoryDatabase;

import androidx.annotation.NonNull;

import com.lbeul.shotspots_v2.models.imageData.ImageData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ListBasedInMemoryDatabase implements InMemoryDatabase {

    private static final InMemoryDatabase INSTANCE = new ListBasedInMemoryDatabase();

    private List<ImageData> database = new ArrayList<>();

    /**
     * Private constructor to prevent instantiation
     */
    private ListBasedInMemoryDatabase() {
    }

    public static InMemoryDatabase getInstance() {
        return INSTANCE;
    }

    @Override
    public void addImageData(ImageData imgData) {
        database.add(imgData);
    }

    @Override
    public ImageData removeImageById(UUID imageId) {
        ImageData imageToBeDeleted = database.stream()
                .filter(imageData -> imageData.getId().equals(imageId))
                .findFirst()
                .orElse(null);
        if (imageToBeDeleted != null) {
            database = database.stream()
                    .filter(img -> !(img.getId().equals(imageId)))
                    .collect(Collectors.toList());
        }
        return imageToBeDeleted;
    }

    @Override
    public List<ImageData> getAllImages() {
        return database;
    }

    @Override
    public void logContent() {
        System.out.println("====== START OF DB CONTENT ======");
        database.forEach(System.out::println);
        System.out.println("======= END OF DB CONTENT =======");
    }
}
