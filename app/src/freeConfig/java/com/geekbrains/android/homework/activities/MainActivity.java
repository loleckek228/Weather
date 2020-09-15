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
import android.widget.Toast;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private BroadcastReceiver networkStatusReceiver = new NetworkStatusReceiver();
    private BroadcastReceiver batteryStatusReceiver = new BatteryStatusReceiver();
    private SharedPreferences settingPrefs;

    private final String settingsPrefsKey = "named_prefs";
    private final String themeKey = "theme_key";

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.floatingButton)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_search_city, R.id.navigation_weather,
                R.id.navigation_maps, R.id.navigation_developer,
                R.id.navigation_settings)
                .setDrawerLayout(drawer)
                .build();

        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI
                .setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        registerReceivers();
        initNotificationChannel();
        setOnFloatingButtonClick();
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
            Toast.makeText(this,
                    R.string.function_show_client_weather,
                    Toast.LENGTH_LONG).show();
        });
    }

    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_MIN;
            NotificationChannel channel = new NotificationChannel("2", "name", importance);
            notificationManager.createNotificationChannel(channel);
        }
    }
}