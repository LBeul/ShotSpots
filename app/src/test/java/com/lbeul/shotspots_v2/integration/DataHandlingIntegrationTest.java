package com.lbeul.shotspots_v2.integration;

import static org.junit.Assert.*;

import com.lbeul.shotspots_v2.controllers.persistence.JSONPersistenceService;
import com.lbeul.shotspots_v2.controllers.persistence.PersistenceService;
import com.lbeul.shotspots_v2.controllers.persistence.PersistenceServiceException;
import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.models.imageData.ImageDataImpl;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.DatabaseException;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.InMemoryDatabase;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.ListBasedInMemoryDatabase;

import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DataHandlingIntegrationTest {

    PersistenceService persistenceService = new JSONPersistenceService();
    InMemoryDatabase db = ListBasedInMemoryDatabase.getInstance();


    UUID first = UUID.randomUUID();
    UUID second = UUID.randomUUID();
    ImageData imgA = new ImageDataImpl("image:a", 1.0, 2.0, new Date(1687874124L), "Apple", "iPhone 42", first);
    ImageData imgB = new ImageDataImpl("image:b", 3.0, 4.0, new Date(1687874124L), "Google", "Pixel 35a", second);


    @Test
    public void shouldReadPreviouslyPersistedImageDataBack() throws PersistenceServiceException, DatabaseException {
        db.addImageData(imgA);
        db.addImageData(imgB);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        persistenceService.persistToFileSystem(outContent, db);
        String out = outContent.toString();
        assertTrue(out.contains("iPhone 42") && out.contains("Pixel 35a"));


        clearDatabaseSingleton();

        InputStream inContent = new ByteArrayInputStream(out.getBytes());
        List<ImageData> retrievedData = persistenceService.readFromFileSystem(inContent);
        db.seed(retrievedData);

        assertEquals(2, db.getAllImages().size());
        assertTrue(db.getAllImages().stream().anyMatch(img -> Objects.equals(img.getCameraModel(), "Pixel 35a")));
    }

    private void clearDatabaseSingleton() throws DatabaseException {
        for (ImageData img : db.getAllImages()){
            db.removeImageById(img.getId());
        }
    }
}
