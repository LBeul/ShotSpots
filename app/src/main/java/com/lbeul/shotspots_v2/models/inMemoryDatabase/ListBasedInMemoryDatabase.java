package com.lbeul.shotspots_v2.models.inMemoryDatabase;

import com.lbeul.shotspots_v2.controllers.persistence.JSONPersistenceService;
import com.lbeul.shotspots_v2.controllers.persistence.PersistenceService;
import com.lbeul.shotspots_v2.models.imageData.ImageData;

import java.util.ArrayList;
import java.util.Collections;
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
    public void seed(List<ImageData> initialData) {
        if(this.imageDataCollection.isEmpty()) {
            // Deep copy existing db image into local db
            Collections.copy(initialData, imageDataCollection);
            System.out.println("Successfully seeded InMemory Database.");
        } else {
            // FIXME: Create InMemoryDbException for such cases
            System.err.println("Error: Non-empty database cannot be seeded.");
        }
    }

    @Override
    public void addImageData(ImageData imgData) {
        imageDataCollection.add(imgData);
    }

    @Override
    public ImageData removeImageById(UUID imageId) {
        ImageData imageToBeDeleted = imageDataCollection.stream()
                .filter(imageData -> imageData.getId().equals(imageId))
                .findFirst()
                .orElse(null);
        if (imageToBeDeleted != null) {
            imageDataCollection = imageDataCollection.stream()
                    .filter(img -> !(img.getId().equals(imageId)))
                    .collect(Collectors.toList());
        }
        return imageToBeDeleted;
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

        // FIXME: Persistence tests
        PersistenceService ps = new JSONPersistenceService();
        ps.persistToFileSystem(null, this);
        List<ImageData> deserialized = ps.readFromFileSystem(null);

        System.out.println("====== DESERIALIZED CONTENT ======");
        deserialized.forEach(imageData -> {
            System.out.println(imageData.toString());
            System.out.println("    =========================    ");
        });
    }
}
