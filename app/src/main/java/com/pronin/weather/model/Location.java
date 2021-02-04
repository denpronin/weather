package com.pronin.weather.model;

public class Location {
    private final double latitude;
    private final double longitude;
    private final String address;

    public Location(double lat, double lon, String addr) {
        latitude = lat;
        longitude = lon;
        address = addr;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }
}
