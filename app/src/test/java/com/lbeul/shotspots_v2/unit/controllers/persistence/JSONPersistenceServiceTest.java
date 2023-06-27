package com.lbeul.shotspots_v2.unit.controllers.persistence;

import com.lbeul.shotspots_v2.controllers.persistence.JSONPersistenceService;
import com.lbeul.shotspots_v2.controllers.persistence.PersistenceService;
import com.lbeul.shotspots_v2.controllers.persistence.PersistenceServiceException;
import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.models.imageData.ImageDataImpl;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.InMemoryDatabase;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.ListBasedInMemoryDatabase;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class JSONPersistenceServiceTest {

    private final PersistenceService persistenceService = new JSONPersistenceService();

    @Mock
    InMemoryDatabase mockedDatabase = mock(ListBasedInMemoryDatabase.class);

    @Mock
    ImageData mockedImageDataA = mock(ImageDataImpl.class);
    @Mock
    ImageData mockedImageDataB = mock(ImageDataImpl.class);

    @Before
    public void populateMocks() {
        UUID aId = UUID.randomUUID();
        UUID bId = UUID.randomUUID();
        when(mockedImageDataA.getId()).thenReturn(aId);

        when(mockedImageDataB.getId()).thenReturn(bId);

        List<ImageData> imgDataList = Arrays.asList(mockedImageDataA, mockedImageDataB);

        when(mockedDatabase.getAllImages()).thenReturn(imgDataList);
    }

    @Test
    public void shouldPersistDatabaseContentToFileSystem() throws PersistenceServiceException {
        persistenceService.persistToFileSystem(System.out, mockedDatabase);
        // Inspect console statement!
    }

    @Test
    public void shouldReadImageDataFromFileDumps() throws PersistenceServiceException {
        String mockedJSONDump = "[{\"cameraManufacturer\":\"Apple\",\"cameraModel\":\"iPhone 12 mini back dual wide camera 1.55mm f/2.4\",\"creationTimeStamp\":\"Nov 16, 2022 2:45:49 PM\",\"geoPoint\":{\"mAltitude\":0.0,\"mLatitude\":50.969094444444444,\"mLongitude\":7.0169194444444445},\"id\":\"26fe2179-4b1c-41f7-8a82-4b75bfb36752\",\"imageUri\":{},\"latitude\":50.969094444444444,\"longitude\":7.0169194444444445},{\"cameraManufacturer\":\"Apple\",\"cameraModel\":\"iPhone 12 mini back dual wide camera 4.2mm f/1.6\",\"creationTimeStamp\":\"Aug 6, 2022 4:31:36 PM\",\"geoPoint\":{\"mAltitude\":0.0,\"mLatitude\":52.52208611111111,\"mLongitude\":13.37105},\"id\":\"c8387e72-963c-445b-a538-5c1e72448ab2\",\"imageUri\":{},\"latitude\":52.52208611111111,\"longitude\":13.37105}]";
        InputStream mockedInputStream = new ByteArrayInputStream(mockedJSONDump.getBytes());
        List<ImageData> imgData = persistenceService.readFromFileSystem(mockedInputStream);

        assertEquals(2, imgData.size());
        assertEquals("iPhone 12 mini back dual wide camera 1.55mm f/2.4", imgData.get(0).getCameraModel());
    }
}