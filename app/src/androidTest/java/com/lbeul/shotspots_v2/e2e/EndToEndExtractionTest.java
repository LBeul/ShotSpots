package com.lbeul.shotspots_v2.e2e;

import static org.junit.Assert.*;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SdkSuppress;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class EndToEndExtractionTest {
    private UiDevice device;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        device.pressHome();

        // Wait for launcher
        final String launcherPackage = device.getLauncherPackageName();
        assertNotNull(launcherPackage);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), 2000);
    }

    @Test
    public void shouldExtractAndPersistImageData() throws UiObjectNotFoundException {
        int timeout = 3 * 1000;

        // Open main activity
        UiObject2 mainActivity = device.findObject(By.text("MainActivity"));
        Boolean opened = mainActivity.clickAndWait(Until.newWindow(), timeout);
        assertTrue(opened);

        // Got to upload activity
        UiObject2 goToUploadButton = device.findObject(By.text("Upload Image"));
        goToUploadButton.clickAndWait(Until.newWindow(), timeout);

        // Go to image picker
        UiObject2 loadImage = device.findObject(By.text("Load Image"));
        loadImage.clickAndWait(Until.newWindow(), timeout);

        // Select Image in picker
        device.click(200, 1100);
        UiObject2 select = device.findObject(By.text("SELECT"));
        select.clickAndWait(Until.newWindow(), timeout);

        // Click extract & wait for toast
        UiObject2 extract = device.findObject(By.text("Extract"));
        extract.click();
        device.wait(Until.findObject(By.text("Successfully extracted metadata")), 1000);

        // Go back to main
        UiObject2 back = device.findObject(By.text("Back"));
        back.click();

        // Validate that metadata is displayed
        UiObject2 cardText = device.wait(Until.findObject(By.text("Shot on an Apple Device")), 1000);

        // Persist to FS
        UiObject2 save = device.findObject(By.text("Save DB"));
        save.click();
        device.wait(Until.findObject(By.text("Database saved")), 1000);
    }

}