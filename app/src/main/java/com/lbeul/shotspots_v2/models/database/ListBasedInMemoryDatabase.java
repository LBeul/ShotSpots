package com.lbeul.shotspots_v2.models.database;
import com.lbeul.shotspots_v2.models.imageData.ImageData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ListBasedInMemoryDatabase implements InMemoryDatabase {

    private static final InMemoryDatabase INSTANCE = new ListBasedInMemoryDatabase();

    private List<ImageData> imageDataCollection = new ArrayList<>();

    /**
     * Private constructor to prevent instantiation
     */
    private ListBasedInMemoryDatabase() {
    }

    public static InMemoryDatabase getInstance() {
        return INSTANCE;
    }

    @Override
    public void seed(List<ImageData> initialData) throws DatabaseException {
        if(this.imageDataCollection.isEmpty()) {
            imageDataCollection = initialData;
        } else {
            throw new DatabaseException("Non-empty database cannot be seeded.");
        }
    }

    @Override
    public void addImageData(ImageData imgData) {
        imageDataCollection.add(imgData);
    }

    @Override
    public ImageData removeImageById(UUID imageId) throws DatabaseException {
        ImageData imageToBeDeleted = imageDataCollection.stream()
                .filter(imageData -> imageData.getId().equals(imageId))
                .findFirst()
                .orElse(null);
        if (imageToBeDeleted != null) {
            imageDataCollection = imageDataCollection.stream()
                    .filter(img -> !(img.getId().equals(imageId)))
                    .collect(Collectors.toList());
            return imageToBeDeleted;
        }
        throw new DatabaseException("Image does not exist!");
    }

    @Override
    public List<ImageData> getAllImages() {
        return imageDataCollection;
    }

    @Override
    public void logContent() {
        System.out.println("====== START OF DB CONTENT ======");
        imageDataCollection.forEach(imageData -> {
            System.out.println(imageData.toString());
            System.out.println("    =========================    ");
        });
    }
}
