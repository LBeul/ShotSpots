# ShotSpots

> Course Project for **Mobile Systems & Networks** at HTW Berlin.

## In order to setup...

You have to create a resources file that contains the api key for mapbox under `res/values/api_keys.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="MAPBOX_API_TOKEN">YOUR_API_KEY</string>
</resources>
```

ShotSpots is an Android application that allows users to read metadata from uploaded image files:

## Backend functionality :nut_and_bolt:
- Images can be loaded into the application via Android's local FileSystem
- EXIF data is retrieved from all valid image files
- During runtime, the dataset for each image is saved to an in-memory database
- The user can persist this database to the local filesystem by creating a JSON dump
- Vice versa, existing JSON dumps can be used to populate the in-memory database

## Frontend functionality :iphone:
- There's a screen sporting map, showing each images shooting location extracted from the EXIF data
- There's a upload screen that allows you to import photos to be analyzed
- Exceptions are propagated as Toast Messages

## APIs & Dependencies
- [OSMdroid](https://github.com/osmdroid/osmdroid) by **OpenStreetMaps** for showing the map and handling coordinates
- [metadata-extractor](https://github.com/drewnoakes/metadata-extractor) by **Drew Noakes** for extracting EXIF data from images
- [Gson](https://github.com/google/gson) by **Google** for (de)-serializing POJO to JSON and vice versa
- [JUnit](https://junit.org/junit4/) for testing everything that is not UI-specific