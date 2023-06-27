package com.lbeul.shotspots_v2.unit.models.inMemoryDatabase;

import static org.junit.Assert.*;

import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.DatabaseException;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.InMemoryDatabase;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.ListBasedInMemoryDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ListBasedInMemoryDatabaseTest {

    @Mock
    ImageData mockedImageData = Mockito.mock(ImageData.class);
    @Mock
    ImageData secondMockedImageData = Mockito.mock(ImageData.class);

    @Before
    public void populateMocks() {
        UUID firstId = UUID.randomUUID();
        UUID secondId = UUID.randomUUID();
        Mockito.when(mockedImageData.getId()).thenReturn(firstId);
        Mockito.when(secondMockedImageData.getId()).thenReturn(secondId);
    }

    @After
    public void dropSingletonContent() throws DatabaseException {
        InMemoryDatabase dbRef = ListBasedInMemoryDatabase.getInstance();
        for (ImageData img : dbRef.getAllImages()) {
            dbRef.removeImageById(img.getId());
        }
    }

    @Test
    public void shouldOnlyEverUseSingletonInstance() {
        InMemoryDatabase databaseReference = ListBasedInMemoryDatabase.getInstance();

        InMemoryDatabase secondDatabaseReference = ListBasedInMemoryDatabase.getInstance();
        assertSame(databaseReference, secondDatabaseReference);
    }

    @Test
    public void shouldSuccessfullySeedDatabase() throws DatabaseException {
        List<ImageData> listOfMocks = new ArrayList<>();
        listOfMocks.add(mockedImageData);
        InMemoryDatabase dbRef = ListBasedInMemoryDatabase.getInstance();

        dbRef.seed(listOfMocks);

        assertEquals(dbRef.getAllImages().size(), 1);
        assertEquals(dbRef.getAllImages().get(0), mockedImageData);
    }

    @Test
    public void shouldThrowExceptionAtSeedAttemptWhenNotEmpty() {
        InMemoryDatabase dbRef = ListBasedInMemoryDatabase.getInstance();
        dbRef.addImageData(secondMockedImageData);


        List<ImageData> listOfMocks = new ArrayList<>();
        listOfMocks.add(secondMockedImageData);

        Exception exception = assertThrows(DatabaseException.class, () -> dbRef.seed(listOfMocks));
        String expectedErrorMsg = "Non-empty database cannot be seeded.";
        assertTrue(exception.getMessage().contains(expectedErrorMsg));
    }

    @Test
    public void shouldAddImageDataToDatabase() {
        InMemoryDatabase dbRef = ListBasedInMemoryDatabase.getInstance();
        int initialSize = dbRef.getAllImages().size();

        dbRef.addImageData(mockedImageData);
        assertEquals(initialSize + 1, dbRef.getAllImages().size());
        dbRef.addImageData(secondMockedImageData);
        assertEquals(initialSize + 2, dbRef.getAllImages().size());
    }

    @Test
    public void shouldRemoveImagesById() throws DatabaseException {
        InMemoryDatabase dbRef = ListBasedInMemoryDatabase.getInstance();
        UUID id = mockedImageData.getId();
        dbRef.addImageData(mockedImageData);

        int sizeBefore = dbRef.getAllImages().size();
        assertTrue(dbRef.getAllImages().contains(mockedImageData));

        dbRef.removeImageById(id);

        assertFalse(dbRef.getAllImages().contains(mockedImageData));
        assertEquals(sizeBefore - 1, dbRef.getAllImages().size());
    }

    @Test
    public void shouldThrowExceptionWhenTryingToRemoveNonExistingImageData(){
        InMemoryDatabase dbRef = ListBasedInMemoryDatabase.getInstance();
        UUID id = mockedImageData.getId();

        assertFalse(dbRef.getAllImages().contains(mockedImageData));

        Exception exception = assertThrows(DatabaseException.class, () -> dbRef.removeImageById(id));
        String expectedErrorMsg = "Image does not exist";
        assertTrue(Objects.requireNonNull(exception.getMessage()).contains(expectedErrorMsg));
    }

    @Test
    public void testGettingAllImages() {
        InMemoryDatabase dbRef = ListBasedInMemoryDatabase.getInstance();
        dbRef.addImageData(mockedImageData);
        dbRef.addImageData(secondMockedImageData);
        assertTrue(dbRef.getAllImages().contains(mockedImageData));
        assertTrue(dbRef.getAllImages().contains(secondMockedImageData));
    }
}