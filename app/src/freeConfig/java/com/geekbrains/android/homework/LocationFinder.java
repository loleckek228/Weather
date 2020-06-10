package com.geekbrains.android.homework;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class LocationFinder {
    private static LocationFinder instance;

    private LocationFinder() {
    }

    public static LocationFinder getInstance() {
        if (instance == null) {
            instance = new LocationFinder();
        }

        return instance;
    }

    private List<String> providers;
    private Activity activity;

    private final static String MSG_NO_DATA = "No data";

    private static final int PERMISSION_REQUEST_CODE = 10;

    private LocationManager locationManager;

    public void isRequestPermission(Activity activity) {
        this.activity = activity;

        if (ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermissions();
        } else {
            updateLocation();
        }
    }

    @SuppressLint("MissingPermission")
    public void updateLocation() {
        locationManager
                = (LocationManager) activity.getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        providers = locationManager.getAllProviders();

        Location location = null;

        for (String provider : providers) {
            while (location == null) {
                location = locationManager.getLastKnownLocation(provider);
            }
        }

        LocationContainer.getInstance().setLocation(location);
    }

    @SuppressLint("MissingPermission")
    public void locationUpdates(LocationListener listener) {
        for (String provider : providers) {
            locationManager.requestLocationUpdates(provider,
                    3000L, 1.0f, listener);
        }
    }

    public void removeUpdates(LocationListener listener) {
        locationManager.removeUpdates(listener);
    }

    private void requestLocationPermissions() {
        ActivityCompat.requestPermissions(activity,
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                },
                PERMISSION_REQUEST_CODE);
    }

    public String getCityByLocation(double latitude, double longitude) {
        final Geocoder geocoder = new Geocoder(activity);

        List<Address> list;

        try {
            list = geocoder
                    .getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }

        if (list.isEmpty()) return MSG_NO_DATA;

        Address address = list.get(0);

        String city = address.getLocality();

        if (city != null) {
            return city;
        } else {
            city = address.getSubAdminArea();
        }

        return city;
    }
}