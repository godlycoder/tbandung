package com.example.alfarih.kateangapp.model.trending;

/**
 * Created by alfarih on 18/11/17.
 */

public class Location {
    String address;
    double lat, lng;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
