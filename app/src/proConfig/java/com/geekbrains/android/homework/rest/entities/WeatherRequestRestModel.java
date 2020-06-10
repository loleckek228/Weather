package com.geekbrains.android.homework.rest.entities;

import com.google.gson.annotations.SerializedName;

public class WeatherRequestRestModel {
    @SerializedName("weather") public WeatherRestModel[] weather;
    @SerializedName("main") public MainRestModel main;
    @SerializedName("wind") public WindRestModel wind;
    @SerializedName("dt") public long dt;
    @SerializedName("sys") public SysRestModel sys;
    @SerializedName("id") public long id;
    @SerializedName("name") public String name;
}