package com.geekbrains.android.homework;

import android.location.Location;

public class LocationContainer {
    private static LocationContainer instance;

    private LocationContainer() {}

    public static LocationContainer getInstance() {
        if (instance == null) {
            instance = new LocationContainer();
        }

        return instance;
    }

    private Location location;

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
