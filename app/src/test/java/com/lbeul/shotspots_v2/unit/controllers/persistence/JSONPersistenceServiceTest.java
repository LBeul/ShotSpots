package com.lbeul.shotspots_v2.unit.controllers.persistence;

import com.lbeul.shotspots_v2.controllers.persistence.JSONPersistenceService;
import com.lbeul.shotspots_v2.controllers.persistence.PersistenceService;
import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.InMemoryDatabase;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.ListBasedInMemoryDatabase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class JSONPersistenceServiceTest {

    private final PersistenceService persistenceService = new JSONPersistenceService();

    @Mock
    InMemoryDatabase mockedDatabase = Mockito.mock(ListBasedInMemoryDatabase.class);

    @Mock
    ImageData mockedImageDataA = Mockito.mock(ImageData.class);
    @Mock
    ImageData mockedImageDataB = Mockito.mock(ImageData.class);

    @Before
    public void populateMocks() {
        UUID aId = UUID.randomUUID();
        UUID bId = UUID.randomUUID();
        Mockito.when(mockedImageDataA.getId()).thenReturn(aId);
        Mockito.when(mockedImageDataB.getId()).thenReturn(bId);

        List<ImageData> imgDataList = Arrays.asList(mockedImageDataA, mockedImageDataB);

        Mockito.when(mockedDatabase.getAllImages()).thenReturn(imgDataList);
    }

    @Test
    public void shouldPersistDatabaseContentToFileSystem() {
    }

    @Test
    public void shouldReadImageDataFromFileDumps() {
    }
}