package com.geekbrains.android.homework.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.geekbrains.android.homework.EventBus;
import com.geekbrains.android.homework.LocationContainer;
import com.geekbrains.android.homework.LocationFinder;
import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.notifications.BatteryStatusReceiver;
import com.geekbrains.android.homework.notifications.NetworkStatusReceiver;
import com.geekbrains.android.homework.weatherData.RetrievesWeatherData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private BroadcastReceiver networkStatusReceiver = new NetworkStatusReceiver();
    private BroadcastReceiver batteryStatusReceiver = new BatteryStatusReceiver();
    private DrawerLayout drawer;
    private FloatingActionButton floatingActionButton;
    private LocListener locListener = null;
    private SharedPreferences settingPrefs;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private final String settingsPrefsKey = "named_prefs";
    private final String themeKey = "theme_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settingPrefs = getSharedPreferences(settingsPrefsKey, MODE_PRIVATE);

        boolean isDarkTheme = settingPrefs.getBoolean(themeKey, false);

        if (isDarkTheme) {
            setTheme(R.style.AppDarkTheme);
        } else {
            setTheme(R.style.AppTheme_NoActionBar);
        }
        
        setContentView(R.layout.activity_main);

        initViews();

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_search_city, R.id.navigation_weather,
                R.id.navigation_maps, R.id.navigation_developer,
                R.id.navigation_settings, R.id.navigation_auth_google)
                .setDrawerLayout(drawer)
                .build();

        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI
                .setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        LocationFinder.getInstance().isRequestPermission(this);

        initNotificationChannel();
        registerReceivers();
        setOnFloatingButtonClick();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (locListener == null) locListener = new LocListener();

        LocationFinder.getInstance().locationUpdates(locListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (locListener != null) LocationFinder.getInstance().removeUpdates(locListener);
    }

    private void initViews() {
        drawer = findViewById(R.id.drawer_layout);
        floatingActionButton = findViewById(R.id.floatingButton);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void registerReceivers() {
        registerReceiver(networkStatusReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        registerReceiver(batteryStatusReceiver, new IntentFilter(Intent.ACTION_BATTERY_LOW));
    }

    private void setOnFloatingButtonClick() {
        floatingActionButton.setOnClickListener(view -> {
            Location location = LocationContainer.getInstance().getLocation();

            String city = LocationFinder.getInstance()
                    .getCityByLocation(location.getLatitude(), location.getLongitude());

            findWeatherByCity(city);
        });
    }

    private void findWeatherByCity(String city) {
        RetrievesWeatherData.getInstance().updateWeatherData(city, true);
    }

    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("2", "name", importance);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 100) {
            boolean permissionsGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    permissionsGranted = false;
                    break;
                }
            }
            if (permissionsGranted) recreate();
        }
    }

    private final class LocListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            LocationFinder.getInstance().updateLocation();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }
}